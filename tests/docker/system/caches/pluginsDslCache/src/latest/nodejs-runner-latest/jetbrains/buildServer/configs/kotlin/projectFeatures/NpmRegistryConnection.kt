package jetbrains.buildServer.configs.kotlin.projectFeatures

import jetbrains.buildServer.configs.kotlin.*

/**
 * Adds an [npm registries](https://www.jetbrains.com/help/teamcity/?nodejs#Accessing+Private+NPM+Registries)
 * connection project feature.
 *
 * **Example.**
 * Adds an [npm registry](https://www.jetbrains.com/help/teamcity/?nodejs#Accessing+Private+NPM+Registries)
 * connection project feature.
 * Please note that corresponding build feature should be provided in build configuration to use this connection.
 * This connection will be used only inside `nodeJS` build steps.
 * ```
 * project {
 *     // ...
 *     buildType(Build)
 *
 *     features {
 *         npmRegistry {
 *             id = "PROJECT_EXT_NPM_REG"
 *             name = "NPM Registry"
 *             scope = "scope"
 *             token = "credentialsJSON:xxx"
 *         }
 *     }
 * }
 *
 * object Build : BuildType({
 *     name = "Build"
 *     // ...
 *     features {
 *         npmRegistry {
 *             connectionId = "PROJECT_EXT_NPM_REG"
 *         }
 *     }
 *
 *     steps {
 *         nodeJS {
 *             shellScript = """
 *                 npm ci
 *                 npm run test
 *             """.trimIndent()
 *         }
 *     }
 * })
 * ```
 *
 *
 * @see npmRegistry
 */
open class NpmRegistryConnection() : ProjectFeature() {

    init {
        type = "OAuthProvider"
        param("providerType", "NpmRegistry")
    }

    constructor(init: NpmRegistryConnection.() -> Unit): this() {
        init()
    }

    /**
     * Npm registry connection display name
     */
    var name by stringParameter("displayName")

    /**
     * Npm registry URL, Format: 'http(s)://hostname[:port]'
     */
    var url by stringParameter("npmRegistryHost")

    /**
     * Scope for registry. Leave empty to override default registry.
     */
    var scope by stringParameter("npmRegistryScope")

    /**
     * Token with access to repository
     */
    var token by stringParameter("secure:npmRegistryToken")

    override fun validate(consumer: ErrorConsumer) {
        super.validate(consumer)
    }
}


/**
 *
 * **Example.**
 * Adds an [npm registry](https://www.jetbrains.com/help/teamcity/?nodejs#Accessing+Private+NPM+Registries)
 * connection project feature.
 * Please note that corresponding build feature should be provided in build configuration to use this connection.
 * This connection will be used only inside `nodeJS` build steps.
 * ```
 * project {
 *     // ...
 *     buildType(Build)
 *
 *     features {
 *         npmRegistry {
 *             id = "PROJECT_EXT_NPM_REG"
 *             name = "NPM Registry"
 *             scope = "scope"
 *             token = "credentialsJSON:xxx"
 *         }
 *     }
 * }
 *
 * object Build : BuildType({
 *     name = "Build"
 *     // ...
 *     features {
 *         npmRegistry {
 *             connectionId = "PROJECT_EXT_NPM_REG"
 *         }
 *     }
 *
 *     steps {
 *         nodeJS {
 *             shellScript = """
 *                 npm ci
 *                 npm run test
 *             """.trimIndent()
 *         }
 *     }
 * })
 * ```
 *
 *
 * @see NpmRegistryConnection
 */
fun ProjectFeatures.npmRegistry(init: NpmRegistryConnection.() -> Unit): NpmRegistryConnection {
    val result = NpmRegistryConnection(init)
    feature(result)
    return result
}
