# Table of Contents
* [Introduction](introduction.md)
* [Getting Started](getting-started.md)
* [JSON Pipelines](json-pipelines.md)
* [Applications](applications.md)
    * [Executions](executions.md)
    * [Pipelines](pipelines.md)
        * [Steps](steps.md)
            * [Advance Step Features](advanced-step-features.md)
        * [Pipeline Steps](pipeline-steps.md)
        * [Step Groups](step-groups.md)
        * [Flow Control](flow-control.md)
            * [Fork/Join](fork-join.md)
            * [Split/Merge](split-merge.md)
            * [Streaming Monitor Step](../metalus-common/docs/flowutilssteps.md#streaming-monitor)
              * [Streaming Query Monitors](../metalus-common/docs/streamingquerymonitor.md)
        * [Parameter Mapping](parameter-mapping.md)
            * [Reserved Globals](parameter-mapping.md#reserved-globals)
        * [Execution and Data Flow](pipeline-flow.md)
        * [Connectors](connectors.md)
          * [Data Connectors](dataconnectors.md)
          * [File Connectors](fileconnectors.md)
    * [Error Handling](error-handling.md)
    * [Logging](logging.md)
* [Step Libraries](step-libraries.md)
    * [Metalus Core](../metalus-core/readme.md)
        * Steps
            * [JavascriptSteps](../metalus-core/docs/javascriptsteps.md)
            * [ScalaSteps](../metalus-core/docs/scalascriptsteps.md)
            * [ExceptionSteps](../metalus-core/docs/exceptionsteps.md)
    * [Metalus Common](../metalus-common/readme.md)
        * Steps
            * [ApiSteps](../metalus-common/docs/apisteps.md)
            * [CredentialSteps](../metalus-common/docs/credentialsteps.md)
            * [DataConnectorSteps](../metalus-common/docs/dataconnectorsteps.md)
            * [DataFrameSteps](../metalus-common/docs/dataframesteps.md)
            * [FileManagerSteps](../metalus-common/docs/filemanagersteps.md)
            * [FlowUtilsSteps](../metalus-common/docs/flowutilssteps.md)
            * [HDFSSteps](../metalus-common/docs/hdfssteps.md)
            * [HiveSteps](../metalus-common/docs/hivesteps.md)
            * [JDBCSteps](../metalus-common/docs/jdbcsteps.md)
            * [JSONSteps](../metalus-common/docs/jsonsteps.md)
            * [LoggingSteps](../metalus-common/docs/loggingsteps.md)
            * [QuerySteps](../metalus-common/docs/querysteps.md)
            * [SFTPSteps](../metalus-common/docs/sftpsteps.md)
            * [StringSteps](../metalus-common/docs/stringsteps.md)
            * [TransformationSteps](../metalus-common/docs/transformationsteps.md)
        * Pipelines/Step Groups
            * [Copy File](../metalus-common/docs/copyfile.md) * _Uses new connectors api_
            * [Load to Bronze](../metalus-common/docs/loadtobronze.md) * _Uses new connectors api_
            * [SFTP to HDFS](../metalus-common/docs/sftp2hdfs.md)
            * [Download To Bronze HDFS](../metalus-common/docs/downloadToBronzeHdfs.md)
            * [DownloadSFTPToHDFSWithDataFrame](../metalus-common/docs/downloadsftptohdfswithdataframe.md)
            * [WriteDataFrameToHDFS](../metalus-common/docs/writedataframetohdfs.md)
        * Execution Templates
            * [Load SFTP to Bronze HDFS](../metalus-common/docs/sftploadtobronze.md)
    * [Metalus AWS](../metalus-aws/readme.md)
        * Steps
            * [S3Steps](../metalus-aws/docs/s3steps.md)
            * [KinesisSteps](../metalus-aws/docs/kinesissteps.md)
        * Pipelines/Step Groups
            * [LoadS3Data](../metalus-aws/docs/loads3data.md)
            * [WriteDataFrameToS3](../metalus-aws/docs/writedataframetos3.md)
        * Execution Templates
            * [S3 Load to Bronze](../metalus-aws/docs/s3loadtobronze.md)
        * Extensions
            * [S3FileManager](../metalus-aws/docs/s3filemanager.md)
            * [Kinesis Pipeline Driver](../metalus-aws/docs/kinesispipelinedriver.md)
            * [AWS Secrets Manager Credential Provider](../metalus-aws/docs/awssecretsmanager-credentialprovider.md)
    * [Metalus GCP](../metalus-gcp/readme.md)
        * Steps
            * [BigQuerySteps](../metalus-gcp/docs/bigquerysteps.md)
            * [GCSSteps](../metalus-gcp/docs/gcssteps.md)
            * [PubSubSteps](../metalus-gcp/docs/pubsubsteps.md)
        * Extensions
            * [GCSFileManager](../metalus-gcp/docs/gcsfilemanager.md)
            * [PubSub Pipeline Driver](../metalus-gcp/docs/pubsubpipelinedriver.md)
            * [GCP Secrets Manager Credential Provider](../metalus-gcp/docs/gcpsecretsmanager-credentialprovider.md)
    * [Metalus Kafka](../metalus-kafka/readme.md)
        * Steps
            * [KafkaSteps](../metalus-kafka/docs/kafkasteps.md)
        * Extensions
            * [Kafka Pipeline Driver](../metalus-kafka/docs/kafkapipelinedriver.md)
    * [Metalus Mongo](../metalus-mongo/readme.md)
        * Steps
            * [Mongo Steps](../metalus-mongo/docs/mongosteps.md)
    * [Metalus Delta Lake](../metalus-delta/docs/readme.md)
        * Steps
            * [Delta Lake Steps](../metalus-delta/docs/deltalakesteps.md)
    * [Creating your own step library (Step Templates)](step-templates.md)
        * [Step Annotations](step-annotations.md)
* Advanced
    * [Metadata Extractor](metadata-extractor.md)
    * [Dependency Manager](dependency-manager.md)
    * [Credential Provider](credentialprovider.md)
    * [File Manager](filemanager.md)
    * [Drivers](pipeline-drivers.md)
    * [Audits](executionaudits.md)
    * [PipelineContext](pipeline-context.md)
    * [PipelineManager](pipeline-manager.md)
    * [Http Rest Client](httprestclient.md)
    * [Driver Utils](driver-utils.md)
    * [Streaming Utils](streaming-utils.md)
    * [Script Engine](script-engine.md)
    * [Custom Serializers](serialization.md)
* [Contributing](contributions.md)
