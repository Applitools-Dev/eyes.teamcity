package jetbrains.buildServer.configs.kotlin.v10.projectFeatures

import jetbrains.buildServer.configs.kotlin.v10.*

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
open class ActiveStorage : ProjectFeature {
    constructor(init: ActiveStorage.() -> Unit = {}, base: ActiveStorage? = null): super(base = base as ProjectFeature?) {
        type = "active_storage"
        init()
    }

    /**
     * Active storage project feature external ID.
     * Set to "DefaultStorage" to use TeamCity built-in storage.
     */
    var activeStorageID by stringParameter("active.storage.feature.id")

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
fun ProjectFeatures.activeStorage(base: ActiveStorage? = null, init: ActiveStorage.() -> Unit = {}) {
    feature(ActiveStorage(init, base))
}
