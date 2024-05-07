package jetbrains.buildServer.configs.kotlin.v2018_1.buildSteps

import jetbrains.buildServer.configs.kotlin.v2018_1.*

/**
 * A build step running a Kotlin script with the specified content
 *
 * **Example.**
 * Adds a Kotlin script build step with [default Kotlin compiler](https://www.jetbrains.com/help/teamcity/?Kotlin+Script#Prerequisites) and default JVM.
 * ```
 * kotlinScript {
 *     name = "My Kotlin Script step"
 *     content = """
 *         // Koltin script content
 *         println("Hello, World")
 *     """.trimIndent()
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
 *     workingDir = "data/"
 *     content = """
 *         // Koltin script content
 *         println("Hello, World")
 *     """.trimIndent()
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
 *     workingDir = "data/"
 *     content = """
 *         // Koltin script content
 *         println("Hello, World")
 *     """.trimIndent()
 *
 *     compiler = "path/to/kotlin/compiler"
 *
 *     jdkHome = "path/to/jdk"
 *     jvmArgs = "-Xmx2048m"
 * }
 * ```
 *
 *
 * @see kotlinScript
 */
open class KotlinScriptCustomBuildStep() : BuildStep() {

    init {
        type = "kotlinScript"
        param("scriptType", "customScript")
    }

    constructor(init: KotlinScriptCustomBuildStep.() -> Unit): this() {
        init()
    }

    /**
     * [Build working directory](https://www.jetbrains.com/help/teamcity/?Build+Working+Directory) for the script,
     * specify it if it is different from the [checkout directory](https://www.jetbrains.com/help/teamcity/?Build+Checkout+Directory).
     */
    var workingDir by stringParameter("teamcity.build.workingDir")

    /**
     * Content of the script to run
     */
    var content by stringParameter("scriptContent")

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
        if (content == null && !hasParam("scriptContent")) {
            consumer.consumePropertyError("content", "mandatory 'content' property is not specified")
        }
    }
}


/**
 * Adds a build step running a Kotlin script with the specified content
 *
 * **Example.**
 * Adds a Kotlin script build step with [default Kotlin compiler](https://www.jetbrains.com/help/teamcity/?Kotlin+Script#Prerequisites) and default JVM.
 * ```
 * kotlinScript {
 *     name = "My Kotlin Script step"
 *     content = """
 *         // Koltin script content
 *         println("Hello, World")
 *     """.trimIndent()
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
 *     workingDir = "data/"
 *     content = """
 *         // Koltin script content
 *         println("Hello, World")
 *     """.trimIndent()
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
 *     workingDir = "data/"
 *     content = """
 *         // Koltin script content
 *         println("Hello, World")
 *     """.trimIndent()
 *
 *     compiler = "path/to/kotlin/compiler"
 *
 *     jdkHome = "path/to/jdk"
 *     jvmArgs = "-Xmx2048m"
 * }
 * ```
 *
 *
 * @see KotlinCustomScriptBuildStep
 */
fun BuildSteps.kotlinScript(init: KotlinScriptCustomBuildStep.() -> Unit): KotlinScriptCustomBuildStep {
    val result = KotlinScriptCustomBuildStep(init)
    step(result)
    return result
}
