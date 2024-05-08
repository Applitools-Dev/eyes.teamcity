package jetbrains.buildServer.configs.kotlin.v10.buildFeatures

import jetbrains.buildServer.configs.kotlin.v10.*

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
open class NpmConnectionBuildFeature : BuildFeature {
    constructor(init: NpmConnectionBuildFeature.() -> Unit = {}, base: NpmConnectionBuildFeature? = null): super(base = base as BuildFeature?) {
        type = "NpmRegistryConnection"
        init()
    }

    /**
     * Log in to the npm registry with the given ID before the build.
     */
    var connectionId by stringParameter()

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
fun BuildFeatures.npmRegistry(base: NpmConnectionBuildFeature? = null, init: NpmConnectionBuildFeature.() -> Unit = {}) {
    feature(NpmConnectionBuildFeature(init, base))
}
