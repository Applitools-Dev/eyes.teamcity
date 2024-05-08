package jetbrains.buildServer.configs.kotlin.v10.projectFeatures

import jetbrains.buildServer.configs.kotlin.v10.*

/**
 * S3 Compatible Artifact Storage
 *
 * **Example.**
 * Adds new storage that allows TeamCity to store build artifacts in S3
 * ```
 * s3CompatibleStorage {
 *   id = "S3_STORAGE_ID"
 *   storageName = "Storage Name"
 *   bucketName = "Bucket Name"
 *   bucketPrefix = "path/prefix"
 *
 *   forceVirtualHostAddressing = true
 *   verifyIntegrityAfterUpload = false
 *
 *   multipartThreshold = "8MB"
 *   multipartChunksize = "8MB"
 *
 *   // Endpoint is required:
 *   endpoint = "https://s3-compatible-endpoint.com"
 *   // Storage also requires configured Credentials
 *   accessKeyID = "Access key ID"
 *   accessKey = "credentialsJSON:link-to-property-containing-access-key"
 * }
 * ```
 *
 *
 * @see s3CompatibleStorage
 */
open class S3CompatibleStorage : ProjectFeature {
    constructor(init: S3CompatibleStorage.() -> Unit = {}, base: S3CompatibleStorage? = null): super(base = base as ProjectFeature?) {
        type = "storage_settings"
        param("storage.type", "S3_storage_compatible")
        param("storage.s3.bucket.name.wasProvidedAsString", "true")
        init()
    }

    /**
     * Account access key ID
     */
    var accessKeyID by stringParameter("aws.access.key.id")

    /**
     * Account secret access key
     */
    var accessKey by stringParameter("secure:aws.secret.access.key")

    /**
     * Endpoint URL
     */
    var endpoint by stringParameter("aws.service.endpoint")

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
     * Whether to verify integrity of artifacts after upload
     */
    var verifyIntegrityAfterUpload by booleanParameter("storage.s3.verifyIntegrityAfterUpload")

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

}


/**
 * Adds a S3 Compatible Artifact Storage project feature
 *
 * **Example.**
 * Adds new storage that allows TeamCity to store build artifacts in S3
 * ```
 * s3CompatibleStorage {
 *   id = "S3_STORAGE_ID"
 *   storageName = "Storage Name"
 *   bucketName = "Bucket Name"
 *   bucketPrefix = "path/prefix"
 *
 *   forceVirtualHostAddressing = true
 *   verifyIntegrityAfterUpload = false
 *
 *   multipartThreshold = "8MB"
 *   multipartChunksize = "8MB"
 *
 *   // Endpoint is required:
 *   endpoint = "https://s3-compatible-endpoint.com"
 *   // Storage also requires configured Credentials
 *   accessKeyID = "Access key ID"
 *   accessKey = "credentialsJSON:link-to-property-containing-access-key"
 * }
 * ```
 *
 *
 * @see S3CompatibleStorage
 */
fun ProjectFeatures.s3CompatibleStorage(base: S3CompatibleStorage? = null, init: S3CompatibleStorage.() -> Unit = {}) {
    feature(S3CompatibleStorage(init, base))
}
