package jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures

import jetbrains.buildServer.configs.kotlin.v2019_2.*

/**
 * Adds a connection to [npm registry](https://www.jetbrains.com/help/teamcity/?nodejs#Accessing+Private+NPM+Registries)
 * to build.
 *
 * **Example.**
 * Adds an [npm registry](https://www.jetbrains.com/help/teamcity/?nodejs#Accessing+Private+NPM+Registries)
 * build feature.
 * Please note that corresponding connection project feature should be provided in project.
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
open class NpmConnectionBuildFeature() : BuildFeature() {

    init {
        type = "NpmRegistryConnection"
    }

    constructor(init: NpmConnectionBuildFeature.() -> Unit): this() {
        init()
    }

    /**
     * Log in to the npm registry with the given ID before the build.
     */
    var connectionId by stringParameter()

    override fun validate(consumer: ErrorConsumer) {
        super.validate(consumer)
        if (connectionId == null && !hasParam("connectionId")) {
            consumer.consumePropertyError("connectionId", "mandatory 'connectionId' property is not specified")
        }
    }
}


/**
 *
 * **Example.**
 * Adds an [npm registry](https://www.jetbrains.com/help/teamcity/?nodejs#Accessing+Private+NPM+Registries)
 * build feature.
 * Please note that corresponding connection project feature should be provided in project.
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
 * @see NpmConnectionBuildFeature
 */
fun BuildFeatures.npmRegistry(init: NpmConnectionBuildFeature.() -> Unit): NpmConnectionBuildFeature {
    val result = NpmConnectionBuildFeature(init)
    feature(result)
    return result
}
