package jetbrains.buildServer.configs.kotlin.v2018_2.projectFeatures

import jetbrains.buildServer.configs.kotlin.v2018_2.*

/**
 * This connection is used in
 * [Docker Support build feature](https://www.jetbrains.com/help/teamcity/?Docker+Support).
 * 
 *
 * **Example.**
 * Configure a connection to https://docker.io with the given username and password.
 * ```
 * project {
 *     ...
 *     features {
 *         dockerRegistry {
 *             id = "PROJECT_EXT_01"
 *             name = "Docker Registry"
 *             userName = "dockerIoUser"
 *             password = "credentialsJSON:******"
 *         }
 *     }
 * }
 * ```
 *
 * **Example.**
 * Configure a connection to https://my.docker.registry.net with the given username and password.
 * ```
 * project {
 *     ...
 *     features {
 *         dockerRegistry {
 *             id = "PROJECT_EXT_02"
 *             name = "Registry at my.docker.registry.net"
 *             url = "https://my.docker.registry.net"
 *
 *             userName = "dockerIoUser"
 *             password = "credentialsJSON:******"
 *         }
 *     }
 * }
 * ```
 *
 *
 * @see [DockerSupportFeature][jetbrains.buildServer.configs.kotlin.buildFeatures.DockerSupportFeature]
 */
open class DockerRegistryConnection() : ProjectFeature() {

    init {
        type = "OAuthProvider"
        param("providerType", "Docker")
    }

    constructor(init: DockerRegistryConnection.() -> Unit): this() {
        init()
    }

    /**
     * Docker registry connection display name
     */
    var name by stringParameter("displayName")

    /**
     * Docker registry URL, like 'https://docker.io'
     */
    var url by stringParameter("repositoryUrl")

    var userName by stringParameter()

    var password by stringParameter("secure:userPass")

    override fun validate(consumer: ErrorConsumer) {
        super.validate(consumer)
    }
}


/**
 *
 * **Example.**
 * Configure a connection to https://docker.io with the given username and password.
 * ```
 * project {
 *     ...
 *     features {
 *         dockerRegistry {
 *             id = "PROJECT_EXT_01"
 *             name = "Docker Registry"
 *             userName = "dockerIoUser"
 *             password = "credentialsJSON:******"
 *         }
 *     }
 * }
 * ```
 *
 * **Example.**
 * Configure a connection to https://my.docker.registry.net with the given username and password.
 * ```
 * project {
 *     ...
 *     features {
 *         dockerRegistry {
 *             id = "PROJECT_EXT_02"
 *             name = "Registry at my.docker.registry.net"
 *             url = "https://my.docker.registry.net"
 *
 *             userName = "dockerIoUser"
 *             password = "credentialsJSON:******"
 *         }
 *     }
 * }
 * ```
 *
 *
 * @see DockerRegistryConnection
 */
fun ProjectFeatures.dockerRegistry(init: DockerRegistryConnection.() -> Unit): DockerRegistryConnection {
    val result = DockerRegistryConnection(init)
    feature(result)
    return result
}
