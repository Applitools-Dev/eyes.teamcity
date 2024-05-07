package jetbrains.buildServer.configs.kotlin.v2017_2.buildSteps

import jetbrains.buildServer.configs.kotlin.v2017_2.*

/**
 * A build step running a Kotlin script with from a specified file
 *
 * **Example.**
 * Adds a Kotlin script build step with [default Kotlin compiler](https://www.jetbrains.com/help/teamcity/?Kotlin+Script#Prerequisites) and default JVM.
 * ```
 * kotlinScript {
 *     name = "My Kotlin Script step"
 *     path = "script.kts"
 * }
 * ```
 *
 * **Example.**
 * Adds a Kotlin script build step with [bundled Kotlin compiler](https://www.jetbrains.com/help/teamcity/?Kotlin+Script#Prerequisites)
 * and custom JDK is set to the [environment variable](https://www.jetbrains.com/help/teamcity/?Using+Build+Parameters) value with additional parameter.
 * Additional agruments are passed to the Kotlin script with a reference to the [configuration parameter](https://www.jetbrains.com/help/teamcity/?Using+Build+Parameters) value.
 * Custom [working directory](https://www.jetbrains.com/help/teamcity/?Build+Working+Directory) is provided.
 * ```
 * kotlinScript {
 *     name = "My Kotlin Script step"
 *     workingDir = "scripts/"
 *     path = "script.kts"
 *
 *     compiler = "%teamcity.tool.kotlin.compiler.bundled%"
 *     arguments = "%myscript.params%"
 *
 *     jdkHome = "%env.JDK_15_0%"
 *     jvmArgs = "-Xmx2048m"
 * }
 * ```
 *
 * **Example.**
 * Adds a Kotlin script build step with custom Kotlin compiler
 * and custom JDK with additional parameter.
 * Custom [working directory](https://www.jetbrains.com/help/teamcity/?Build+Working+Directory) is provided.
 * ```
 * kotlinScript {
 *     name = "My Kotlin Script step"
 *     workingDir = "scripts/"
 *     path = "script.kts"
 *
 *     compiler = "path/to/kotlin/compiler"
 *
 *     jdkHome = "path/to/jdk"
 *     jvmArgs = "-Xmx2048m"
 * }
 * ```
 *
 *
 * @see kotlinFile
 */
open class KotlinScriptFileBuildStep() : BuildStep() {

    init {
        type = "kotlinScript"
        param("scriptType", "file")
    }

    constructor(init: KotlinScriptFileBuildStep.() -> Unit): this() {
        init()
    }

    /**
     * [Build working directory](https://www.jetbrains.com/help/teamcity/?Build+Working+Directory) for the script,
     * specify it if it is different from the [checkout directory](https://www.jetbrains.com/help/teamcity/?Build+Checkout+Directory).
     */
    var workingDir by stringParameter("teamcity.build.workingDir")

    /**
     * A path to a file with .main.kts extension
     */
    var path by stringParameter("scriptFile")

    /**
     * Path to Kotlin compiler
     */
    var compiler by stringParameter("kotlinPath")

    /**
     * Space-separated list of additional arguments for Kotlin script
     */
    var arguments by stringParameter("kotlinArgs")

    /**
     * Custom [JDK](https://www.jetbrains.com/help/teamcity/?Predefined+Build+Parameters#PredefinedBuildParameters-DefiningJava-relatedEnvironmentVariables) to use.
     * The default is JAVA_HOME environment variable or the agent's own Java.
     */
    var jdkHome by stringParameter("target.jdk.home")

    /**
     * Space-separated list of additional arguments for JVM
     */
    var jvmArgs by stringParameter()

    override fun validate(consumer: ErrorConsumer) {
        super.validate(consumer)
        if (path == null && !hasParam("scriptFile")) {
            consumer.consumePropertyError("path", "mandatory 'path' property is not specified")
        }
    }
}


/**
 * Adds a build step running a Kotlin script from a specified file
 *
 * **Example.**
 * Adds a Kotlin script build step with [default Kotlin compiler](https://www.jetbrains.com/help/teamcity/?Kotlin+Script#Prerequisites) and default JVM.
 * ```
 * kotlinScript {
 *     name = "My Kotlin Script step"
 *     path = "script.kts"
 * }
 * ```
 *
 * **Example.**
 * Adds a Kotlin script build step with [bundled Kotlin compiler](https://www.jetbrains.com/help/teamcity/?Kotlin+Script#Prerequisites)
 * and custom JDK is set to the [environment variable](https://www.jetbrains.com/help/teamcity/?Using+Build+Parameters) value with additional parameter.
 * Additional agruments are passed to the Kotlin script with a reference to the [configuration parameter](https://www.jetbrains.com/help/teamcity/?Using+Build+Parameters) value.
 * Custom [working directory](https://www.jetbrains.com/help/teamcity/?Build+Working+Directory) is provided.
 * ```
 * kotlinScript {
 *     name = "My Kotlin Script step"
 *     workingDir = "scripts/"
 *     path = "script.kts"
 *
 *     compiler = "%teamcity.tool.kotlin.compiler.bundled%"
 *     arguments = "%myscript.params%"
 *
 *     jdkHome = "%env.JDK_15_0%"
 *     jvmArgs = "-Xmx2048m"
 * }
 * ```
 *
 * **Example.**
 * Adds a Kotlin script build step with custom Kotlin compiler
 * and custom JDK with additional parameter.
 * Custom [working directory](https://www.jetbrains.com/help/teamcity/?Build+Working+Directory) is provided.
 * ```
 * kotlinScript {
 *     name = "My Kotlin Script step"
 *     workingDir = "scripts/"
 *     path = "script.kts"
 *
 *     compiler = "path/to/kotlin/compiler"
 *
 *     jdkHome = "path/to/jdk"
 *     jvmArgs = "-Xmx2048m"
 * }
 * ```
 *
 *
 * @see KotlinScriptFileBuildStep
 */
fun BuildSteps.kotlinFile(init: KotlinScriptFileBuildStep.() -> Unit): KotlinScriptFileBuildStep {
    val result = KotlinScriptFileBuildStep(init)
    step(result)
    return result
}
