package jetbrains.buildServer.configs.kotlin.v10.buildSteps

import jetbrains.buildServer.configs.kotlin.v10.*

/**
 * A [build step](https://www.jetbrains.com/help/teamcity/?Command+Line) running a script with the specified content
 *
 * **Example.**
 * Runs a script in the [checkout directory](https://www.jetbrains.com/help/teamcity/?Build+Checkout+Directory).
 * ```
 * script {
 *     scriptContent = "echo 'hello'"
 * }
 * ```
 *
 * **Example.**
 * Runs a multiline script in the specified [build working directory](https://www.jetbrains.com/help/teamcity/?Build+Working+Directory) with an argument referencing
 * to the configuration [parameter](https://www.jetbrains.com/help/teamcity/?Using+Build+Parameters). Any of stderr output will be treated as errors.
 * ```
 * script {
 *     name = "Run tests"
 *     workingDir = "tests"
 *     scriptContent = """
 *         echo "Running tests for version %product.version%"
 *         ./run_tests.sh "%product.version%"
 *     """.trimIndent()
 *     formatStderrAsError = true
 * }
 * ```
 *
 * **Example.**
 * Runs a multiline script in the specified [build working directory](https://www.jetbrains.com/help/teamcity/?Build+Working+Directory)
 * in the specified [Docker container](https://www.jetbrains.com/help/teamcity/?Docker+Wrapper)
 * with an argument referencing to the configuration [parameter](https://www.jetbrains.com/help/teamcity/?Using+Build+Parameters).
 * ```
 * script {
 *     name = "Run tests in Docker"
 *
 *     workingDir = "tests"
 *     scriptContent = """
 *         echo "Running tests for version %product.version%"
 *         ./run_tests.sh "%product.version%"
 *     """.trimIndent()
 *
 *     dockerImage = "nodejs:lts"
 *     dockerImagePlatform = ScriptBuildStep.ImagePlatform.Linux
 *     dockerRunParameters = "--rm --interactive=false"
 * }
 * ```
 *
 *
 * @see script
 */
open class ScriptBuildStep : BuildStep {
    constructor(init: ScriptBuildStep.() -> Unit = {}, base: ScriptBuildStep? = null): super(base = base as BuildStep?) {
        type = "simpleRunner"
        param("use.custom.script", "true")
        init()
    }

    /**
     * [Build working directory](https://www.jetbrains.com/help/teamcity/?Build+Working+Directory) for script,
     * specify it if it is different from the [checkout directory](https://www.jetbrains.com/help/teamcity/?Build+Checkout+Directory).
     */
    var workingDir by stringParameter("teamcity.build.workingDir")

    /**
     * Content of the script to run
     */
    var scriptContent by stringParameter("script.content")

    /**
     * Log stderr output as errors in the build log
     */
    var formatStderrAsError by booleanParameter("log.stderr.as.errors", trueValue = "true", falseValue = "")

    /**
     * Specifies which Docker image to use for running this build step. I.e. the build step will be run inside specified docker image, using 'docker run' wrapper.
     */
    var dockerImage by stringParameter("plugin.docker.imageId")

    /**
     * Specifies which Docker image platform will be used to run this build step.
     */
    var dockerImagePlatform by enumParameter<ImagePlatform>("plugin.docker.imagePlatform", mapping = ImagePlatform.mapping)

    /**
     * If enabled, "pull [image][dockerImage]" command will be run before docker run.
     */
    var dockerPull by booleanParameter("plugin.docker.pull.enabled", trueValue = "true", falseValue = "")

    /**
     * Additional docker run command arguments
     */
    var dockerRunParameters by stringParameter("plugin.docker.run.parameters")

    /**
     * Docker image platforms
     */
    enum class ImagePlatform {
        Any,
        Linux,
        Windows;

        companion object {
            val mapping = mapOf<ImagePlatform, String>(Any to "", Linux to "linux", Windows to "windows")
        }

    }
}


/**
 * Adds a [build step](https://www.jetbrains.com/help/teamcity/?Command+Line) running a script with the specified content
 *
 * **Example.**
 * Runs a script in the [checkout directory](https://www.jetbrains.com/help/teamcity/?Build+Checkout+Directory).
 * ```
 * script {
 *     scriptContent = "echo 'hello'"
 * }
 * ```
 *
 * **Example.**
 * Runs a multiline script in the specified [build working directory](https://www.jetbrains.com/help/teamcity/?Build+Working+Directory) with an argument referencing
 * to the configuration [parameter](https://www.jetbrains.com/help/teamcity/?Using+Build+Parameters). Any of stderr output will be treated as errors.
 * ```
 * script {
 *     name = "Run tests"
 *     workingDir = "tests"
 *     scriptContent = """
 *         echo "Running tests for version %product.version%"
 *         ./run_tests.sh "%product.version%"
 *     """.trimIndent()
 *     formatStderrAsError = true
 * }
 * ```
 *
 * **Example.**
 * Runs a multiline script in the specified [build working directory](https://www.jetbrains.com/help/teamcity/?Build+Working+Directory)
 * in the specified [Docker container](https://www.jetbrains.com/help/teamcity/?Docker+Wrapper)
 * with an argument referencing to the configuration [parameter](https://www.jetbrains.com/help/teamcity/?Using+Build+Parameters).
 * ```
 * script {
 *     name = "Run tests in Docker"
 *
 *     workingDir = "tests"
 *     scriptContent = """
 *         echo "Running tests for version %product.version%"
 *         ./run_tests.sh "%product.version%"
 *     """.trimIndent()
 *
 *     dockerImage = "nodejs:lts"
 *     dockerImagePlatform = ScriptBuildStep.ImagePlatform.Linux
 *     dockerRunParameters = "--rm --interactive=false"
 * }
 * ```
 *
 *
 * @see ScriptBuildStep
 */
fun BuildSteps.script(base: ScriptBuildStep? = null, init: ScriptBuildStep.() -> Unit = {}) {
    step(ScriptBuildStep(init, base))
}
