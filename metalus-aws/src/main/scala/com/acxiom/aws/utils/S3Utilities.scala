package com.acxiom.aws.utils

import com.acxiom.pipeline.PipelineContext
import org.apache.log4j.Logger
import java.net.URI

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder
import com.amazonaws.services.securitytoken.model.{AssumeRoleRequest, AssumeRoleResult}

object S3Utilities {
  private val logger = Logger.getLogger(getClass)
  val MULTIPART_UPLOAD_SIZE = 52428800
  val MULTIPART_COPY_SIZE = 5368709120L

  /**
    * Given a path, this function will attempt to derive the protocol. If the protocol cannot be determined, then it
    * will default to s3a.
    * @param path A valid path
    * @return The protocol to use for authentication.
    */
  def deriveProtocol(path: String): String = {
    if (path.startsWith("s3")) {
      path.substring(0, path.indexOf(":"))
    } else {
      "s3a"
    }
  }

  /**
   * Given a path, this function will attempt to derive the s3 bucket.
   * @param path A valid Path
   * @return The bucket to use.
   */
  def deriveBucket(path: String): String = {
    new URI(path).normalize().getHost
  }

  /**
    * This function will attempt to set the authorization used when reading or writing a DataFrame.
    *
    * @param path A valid path
    * @param accessKeyId The access key
    * @param secretAccessKey The secret
    * @param pipelineContext The PipelineContext
    */
  def setS3Authorization(path: String,
                         accessKeyId: Option[String],
                         secretAccessKey: Option[String],
                         accountId: Option[String] = None,
                         role: Option[String] = None,
                         partition: Option[String] = None,
                         duration: Option[String] = None,
                         pipelineContext: PipelineContext): Unit = {
    val keyAndSecret = accessKeyId.isDefined && secretAccessKey.isDefined
    val roleBased = role.isDefined && accountId.isDefined
    val useBucketPermissions = pipelineContext.getGlobal("s3bucketPermissionsEnabled")
      .exists(_.toString == "true")
    val bucket = if (useBucketPermissions) s".${S3Utilities.deriveBucket(path)}" else ""
    if (keyAndSecret || roleBased) {
      logger.debug(s"Setting up S3 authorization for $path")
      val protocol = S3Utilities.deriveProtocol(path)
      val conf = pipelineContext.sparkSession.get.conf
      if (accessKeyId.isDefined && secretAccessKey.isDefined) {
        conf.set(s"fs.$protocol$bucket.awsAccessKeyId", accessKeyId.get)
        conf.set(s"fs.$protocol$bucket.awsSecretAccessKey", secretAccessKey.get)
        conf.set(s"fs.$protocol$bucket.access.key", accessKeyId.get)
        conf.set(s"fs.$protocol$bucket.secret.key", secretAccessKey.get)
      }
      if(roleBased && protocol == "s3a") {
        conf.set(s"fs.s3a$bucket.assumed.role.arn", buildARN(accountId.get, role.get, partition))
        duration.foreach(conf.set(s"fs.s3a$bucket.assumed.role.session.duration", _))
      }
      conf.set(s"fs.$protocol.acl.default", "BucketOwnerFullControl")
      conf.set(s"fs.$protocol.canned.acl", "BucketOwnerFullControl")
    }
  }

  /**
    * This function will attempt to add or replace the protocol in the given path.
    * @param path A calid path
    * @param protocol The protocol to use
    * @return The path with the proper protocol.
    */
  def replaceProtocol(path: String, protocol: String): String = {
    val newPath = if (path.startsWith("s3")) {
      path.substring(path.indexOf(":") + 3)
    } else {
      path
    }
    s"$protocol://${prepareS3FilePath(newPath)}"
  }

  /**
    * This function will take the given path and strip any protocol information.
    * @param path A valid path
    * @return A raw path with no protocol information
    */
  def prepareS3FilePath(path: String, bucket: Option[String] = None): String = {
    val newPath = if (path.startsWith("/")) {
      path.substring(1)
    } else if (path.startsWith(s"s3") && bucket.isDefined) {
      path.substring(path.indexOf(s"${bucket.get}/") + bucket.get.length + 1)
    } else if (path.startsWith(s"s3")) {
      new URI(path).normalize().toString
    } else {
      path
    }
    newPath
  }

  /**
    * Prepares Spark for reading/writing of DataFrames
    * @param pipelineContext The PipelineContext containing the SparkSession
    */
  def registerS3FileSystems(pipelineContext: PipelineContext): Unit = {
    pipelineContext.sparkSession.get.sparkContext.hadoopConfiguration.set("fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem")
    pipelineContext.sparkSession.get.sparkContext.hadoopConfiguration.set("fs.s3.impl", "org.apache.hadoop.fs.s3native.NativeS3FileSystem")
  }

  def buildARN(accountId: String, role: String, partition: Option[String]): String = {
    s"arn:${partition.getOrElse("aws")}:iam::$accountId:role/$role"
  }

  def assumeRole(accountId: String,
                 role: String,
                 partition: Option[String] = None,
                 session: Option[String] = None,
                 externalId: Option[String] = None,
                 duration: Option[Integer] = None): AssumeRoleResult = {
    val arn = buildARN(accountId, role, partition)
    val sessionName = session.getOrElse(s"${accountId}_$role")
    val s3Client = AWSSecurityTokenServiceClientBuilder.standard()
      .withCredentials(new DefaultAWSCredentialsProviderChain())
      .build()
    val roleRequest = new AssumeRoleRequest()
      .withRoleArn(arn)
      .withRoleSessionName(sessionName)
    val withExternalId = externalId.filter(_.trim.nonEmpty).map(roleRequest.withExternalId).getOrElse(roleRequest)
    val withDuration = duration.map(withExternalId.withDurationSeconds).getOrElse(withExternalId)
    s3Client.assumeRole(withDuration)
  }
}
