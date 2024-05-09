package jetbrains.buildServer.configs.kotlin.v10.projectFeatures

import jetbrains.buildServer.configs.kotlin.v10.*

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
open class DockerRegistryConnection : ProjectFeature {
    constructor(init: DockerRegistryConnection.() -> Unit = {}, base: DockerRegistryConnection? = null): super(base = base as ProjectFeature?) {
        type = "OAuthProvider"
        param("providerType", "Docker")
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
fun ProjectFeatures.dockerRegistry(base: DockerRegistryConnection? = null, init: DockerRegistryConnection.() -> Unit = {}) {
    feature(DockerRegistryConnection(init, base))
}
