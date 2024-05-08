package jetbrains.buildServer.configs.kotlin.v2017_2.projectFeatures

import jetbrains.buildServer.configs.kotlin.v2017_2.*

/**
 * Amazon S3 Artifact Storage
 *
 * **Example.**
 * Adds new storage that allows TeamCity to store build artifacts in S3
 * ```
 * s3Storage {
 *   id = "S3_STORAGE_ID"
 *   storageName = "Storage Name"
 *
 *   bucketName = "Bucket_Name"
 *   bucketPrefix = "path/prefix"
 *
 *   forceVirtualHostAddressing = true
 *   enableTransferAcceleration = false
 *   multipartThreshold = "8MB"
 *   multipartChunksize = "8MB"
 *
 *   // AWS S3 storage requires configured Credentials
 *   connectionId = "AWS Connection ID"
 * }
 * ```
 *
 * **Example.**
 * Adds new storage that allows TeamCity to store build artifacts in S3 and upload/download them using CloudFront
 * ```
 * s3Storage {
 *   id = "S3_STORAGE_ID"
 *   storageName = "Storage Name"
 *   bucketName = "Bucket_Name"
 *   bucketPrefix = "path/prefix"
 *   forceVirtualHostAddressing = true
 *   multipartThreshold = "6MB"
 *   multipartChunksize = "8MB"
 *   connectionId = "AWS Connection ID"
 *
 *   //CloudFront configuration
 *   cloudFrontEnabled = true
 *   cloudFrontUploadDistribution = "ID of CloudFront Distribution used for uploads"
 *   cloudFrontDownloadDistribution = "ID of CloudFront Distribution used for downloads"
 *   cloudFrontPublicKeyId = "ID of CloudFront public key"
 *   cloudFrontPrivateKey = "credentialsJSON:CloudFront-private-key-link"
 * }
 * ```
 *
 * **Example.**
 * Adds new storage that allows TeamCity to store build artifacts in S3 and upload/download them with Transfer Acceleration
 * ```
 * s3Storage {
 *   id = "S3_STORAGE_ID"
 *   storageName = "Storage Name"
 *   bucketName = "Bucket_Name"
 *   connectionId = "AWS Connection ID"
 *
 *   // must be set to true to enable Transfer Acceleration
 *   forceVirtualHostAddressing = true
 *   enableTransferAcceleration = true
 * }
 * ```
 *
 * **Example.**
 * S3 Storage. Disable integrity verification
 * ```
 * s3Storage {
 *   id = "S3_STORAGE_ID"
 *   storageName = "Storage Name"
 *   bucketName = "Bucket_Name"
 *   connectionId = "AWS Connection ID"
 *
 *   verifyIntegrityAfterUpload = false
 * }
 * ```
 *
 * **Example.**
 * AWSEnvironment. Selects default environment with specific AWS region
 * ```
 * awsEnvironment = default {
 *   awsRegionName = ""
 * }
 * ```
 *
 * **Example.**
 * AWSEnvironment. Selects custom environment with specific region
 * ```
 * awsEnvironment = custom {
 *   endpoint = "URL of custom endpoint"
 *   awsRegionName = "region"
 * }
 * ```
 *
 *
 * @see s3Storage
 */
open class S3Storage() : ProjectFeature() {

    init {
        type = "storage_settings"
        param("storage.type", "S3_storage")
        param("storage.s3.bucket.name.wasProvidedAsString", "true")
    }

    constructor(init: S3Storage.() -> Unit): this() {
        init()
    }

    /**
     * Storage name
     */
    var storageName by stringParameter("storage.name")

    /**
     * Bucket name
     */
    var bucketName by stringParameter("storage.s3.bucket.name")

    /**
     * Bucket path prefix
     */
    var bucketPrefix by stringParameter("storage.s3.bucket.prefix")

    /**
     * Whether to use Pre-Signed URLs to upload
     */
    var enablePresignedURLUpload by booleanParameter("storage.s3.upload.presignedUrl.enabled", trueValue = "true", falseValue = "")

    /**
     * Whether to force Virtual Host Addressing
     */
    var forceVirtualHostAddressing by booleanParameter("storage.s3.forceVirtualHostAddressing")

    /**
     * Whether to enable Transfer Acceleration
     */
    var enableTransferAcceleration by booleanParameter("storage.s3.accelerateModeEnabled")

    /**
     * Initiates multipart upload for files larger than the specified value.
     * Minimum value is 5MB. Allowed suffixes: KB, MB, GB, TB.
     * Leave empty to use the default value.
     */
    var multipartThreshold by stringParameter("storage.s3.upload.multipart_threshold")

    /**
     * Specify the maximum allowed part size. Minimum value is 5MB.
     * Allowed suffixes: KB, MB, GB, TB. Leave empty to use the default value.
     */
    var multipartChunksize by stringParameter("storage.s3.upload.multipart_chunksize")

    /**
     * Whether to use CloudFront for artifact transport
     */
    var cloudFrontEnabled by booleanParameter("storage.s3.cloudfront.enabled", trueValue = "true", falseValue = "")

    /**
     * Name of CloudFront distribution for uploads
     */
    var cloudFrontUploadDistribution by stringParameter("storage.s3.cloudfront.upload.distribution")

    /**
     * Name of CloudFront distribution for downloads
     */
    var cloudFrontDownloadDistribution by stringParameter("storage.s3.cloudfront.download.distribution")

    /**
     * Id of Public Key in CloudFront that has access to selected distribution
     */
    var cloudFrontPublicKeyId by stringParameter("storage.s3.cloudfront.publicKeyId")

    /**
     * Private key that corresponds to chosen public key
     */
    var cloudFrontPrivateKey by stringParameter("secure:storage.s3.cloudfront.privateKey")

    /**
     * Whether to verify integrity of artifacts after upload
     */
    var verifyIntegrityAfterUpload by booleanParameter("storage.s3.verifyIntegrityAfterUpload")

    var awsEnvironment by compoundParameter<AwsEnvironment>("aws.environment")

    sealed class AwsEnvironment(value: String? = null): CompoundParam<AwsEnvironment>(value) {
        abstract fun validate(consumer: ErrorConsumer)

        class Default() : AwsEnvironment("") {

            /**
             * AWS region
             */
            var awsRegionName by stringParameter("aws.region.name")

            override fun validate(consumer: ErrorConsumer) {
            }
        }

        class Custom() : AwsEnvironment("custom") {

            /**
             * Endpoint URL
             */
            var endpoint by stringParameter("aws.service.endpoint")

            /**
             * AWS region
             */
            var awsRegionName by stringParameter("aws.region.name")

            override fun validate(consumer: ErrorConsumer) {
                if (endpoint == null && !hasParam("aws.service.endpoint")) {
                    consumer.consumePropertyError("awsEnvironment.endpoint", "mandatory 'awsEnvironment.endpoint' property is not specified")
                }
            }
        }
    }

    fun default(init: AwsEnvironment.Default.() -> Unit = {}) : AwsEnvironment.Default {
        val result = AwsEnvironment.Default()
        result.init()
        return result
    }

    fun custom(init: AwsEnvironment.Custom.() -> Unit = {}) : AwsEnvironment.Custom {
        val result = AwsEnvironment.Custom()
        result.init()
        return result
    }

    var credentials by compoundParameter<Credentials>("aws.credentials.type")

    sealed class Credentials(value: String? = null): CompoundParam<Credentials>(value) {
        abstract fun validate(consumer: ErrorConsumer)

        class AccessKeys() : Credentials("aws.access.keys") {

            override fun validate(consumer: ErrorConsumer) {
            }
        }

        class Temporary() : Credentials("aws.temp.credentials") {

            /**
             * Pre-configured IAM role with necessary permissions
             */
            var iamRoleARN by stringParameter("aws.iam.role.arn")

            /**
             * External ID is strongly recommended to be used in role trust relationship condition
             */
            var externalID by stringParameter("aws.external.id")

            override fun validate(consumer: ErrorConsumer) {
                if (iamRoleARN == null && !hasParam("aws.iam.role.arn")) {
                    consumer.consumePropertyError("credentials.iamRoleARN", "mandatory 'credentials.iamRoleARN' property is not specified")
                }
            }
        }
    }

    /**
     * Use pre-configured AWS account access keys
     */
    fun accessKeys() = Credentials.AccessKeys()

    /**
     * Get temporary access keys via AWS STS
     */
    fun temporary(init: Credentials.Temporary.() -> Unit = {}) : Credentials.Temporary {
        val result = Credentials.Temporary()
        result.init()
        return result
    }

    /**
     * Use default credential provider chain
     */
    var useDefaultCredentialProviderChain by booleanParameter("aws.use.default.credential.provider.chain")

    /**
     * AWS account access key ID
     */
    var accessKeyID by stringParameter("aws.access.key.id")

    /**
     * AWS account secret access key
     */
    var accessKey by stringParameter("secure:aws.secret.access.key")

    /**
     *
     *
     * The ID of configured AWS Connection to access the AWS S3. @see AwsConnection
     */
    var connectionId by stringParameter("awsConnectionId")

    override fun validate(consumer: ErrorConsumer) {
        super.validate(consumer)
        if (bucketName == null && !hasParam("storage.s3.bucket.name")) {
            consumer.consumePropertyError("bucketName", "mandatory 'bucketName' property is not specified")
        }
        awsEnvironment?.validate(consumer)
        credentials?.validate(consumer)
    }
}


/**
 * Adds a Amazon S3 Artifact Storage project feature
 *
 * **Example.**
 * Adds new storage that allows TeamCity to store build artifacts in S3
 * ```
 * s3Storage {
 *   id = "S3_STORAGE_ID"
 *   storageName = "Storage Name"
 *
 *   bucketName = "Bucket_Name"
 *   bucketPrefix = "path/prefix"
 *
 *   forceVirtualHostAddressing = true
 *   enableTransferAcceleration = false
 *   multipartThreshold = "8MB"
 *   multipartChunksize = "8MB"
 *
 *   // AWS S3 storage requires configured Credentials
 *   connectionId = "AWS Connection ID"
 * }
 * ```
 *
 * **Example.**
 * Adds new storage that allows TeamCity to store build artifacts in S3 and upload/download them using CloudFront
 * ```
 * s3Storage {
 *   id = "S3_STORAGE_ID"
 *   storageName = "Storage Name"
 *   bucketName = "Bucket_Name"
 *   bucketPrefix = "path/prefix"
 *   forceVirtualHostAddressing = true
 *   multipartThreshold = "6MB"
 *   multipartChunksize = "8MB"
 *   connectionId = "AWS Connection ID"
 *
 *   //CloudFront configuration
 *   cloudFrontEnabled = true
 *   cloudFrontUploadDistribution = "ID of CloudFront Distribution used for uploads"
 *   cloudFrontDownloadDistribution = "ID of CloudFront Distribution used for downloads"
 *   cloudFrontPublicKeyId = "ID of CloudFront public key"
 *   cloudFrontPrivateKey = "credentialsJSON:CloudFront-private-key-link"
 * }
 * ```
 *
 * **Example.**
 * Adds new storage that allows TeamCity to store build artifacts in S3 and upload/download them with Transfer Acceleration
 * ```
 * s3Storage {
 *   id = "S3_STORAGE_ID"
 *   storageName = "Storage Name"
 *   bucketName = "Bucket_Name"
 *   connectionId = "AWS Connection ID"
 *
 *   // must be set to true to enable Transfer Acceleration
 *   forceVirtualHostAddressing = true
 *   enableTransferAcceleration = true
 * }
 * ```
 *
 * **Example.**
 * S3 Storage. Disable integrity verification
 * ```
 * s3Storage {
 *   id = "S3_STORAGE_ID"
 *   storageName = "Storage Name"
 *   bucketName = "Bucket_Name"
 *   connectionId = "AWS Connection ID"
 *
 *   verifyIntegrityAfterUpload = false
 * }
 * ```
 *
 * **Example.**
 * AWSEnvironment. Selects default environment with specific AWS region
 * ```
 * awsEnvironment = default {
 *   awsRegionName = ""
 * }
 * ```
 *
 * **Example.**
 * AWSEnvironment. Selects custom environment with specific region
 * ```
 * awsEnvironment = custom {
 *   endpoint = "URL of custom endpoint"
 *   awsRegionName = "region"
 * }
 * ```
 *
 *
 * @see S3Storage
 */
fun ProjectFeatures.s3Storage(init: S3Storage.() -> Unit): S3Storage {
    val result = S3Storage(init)
    feature(result)
    return result
}
