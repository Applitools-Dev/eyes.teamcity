package jetbrains.buildServer.configs.kotlin.v2017_2.projectFeatures

import jetbrains.buildServer.configs.kotlin.v2017_2.*

/**
 * Active storage
 *
 * **Example.**
 * Set up some Artifact storage as active.
 * ```
 * features {
 *     activeStorage {
 *         activeStorageID = "S3Storage_1"
 *     }
 *
 *     s3Storage {
 *         id = "S3Storage_1"
 *         // ...
 *     }
 * }
 * ```
 *
 * **Example.**
 * Set up default Artifact storage as active, even if some other Artifact storages are defined.
 * ```
 * features {
 *     activeStorage {
 *         activeStorageID = "DefaultStorage"
 *     }
 *
 *     s3Storage {
 *         id = "S3Storage_1"
 *         // ...
 *     }
 * }
 * ```
 *
 *
 * @see activeStorage
 */
open class ActiveStorage() : ProjectFeature() {

    init {
        type = "active_storage"
    }

    constructor(init: ActiveStorage.() -> Unit): this() {
        init()
    }

    /**
     * Active storage project feature external ID.
     * Set to "DefaultStorage" to use TeamCity built-in storage.
     */
    var activeStorageID by stringParameter("active.storage.feature.id")

    override fun validate(consumer: ErrorConsumer) {
        super.validate(consumer)
    }
}


/**
 * Adds an Active Storage project feature
 *
 * **Example.**
 * Set up some Artifact storage as active.
 * ```
 * features {
 *     activeStorage {
 *         activeStorageID = "S3Storage_1"
 *     }
 *
 *     s3Storage {
 *         id = "S3Storage_1"
 *         // ...
 *     }
 * }
 * ```
 *
 * **Example.**
 * Set up default Artifact storage as active, even if some other Artifact storages are defined.
 * ```
 * features {
 *     activeStorage {
 *         activeStorageID = "DefaultStorage"
 *     }
 *
 *     s3Storage {
 *         id = "S3Storage_1"
 *         // ...
 *     }
 * }
 * ```
 *
 *
 * @see ActiveStorage
 */
fun ProjectFeatures.activeStorage(init: ActiveStorage.() -> Unit): ActiveStorage {
    val result = ActiveStorage(init)
    feature(result)
    return result
}
