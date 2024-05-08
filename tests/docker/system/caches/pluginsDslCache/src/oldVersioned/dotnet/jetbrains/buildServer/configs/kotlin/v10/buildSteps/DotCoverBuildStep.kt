package jetbrains.buildServer.configs.kotlin.v10.buildSteps

import jetbrains.buildServer.configs.kotlin.v10.*

/**
 * A [dotCover build step](https://github.com/JetBrains/teamcity-dotnet-plugin) to run dotCover – .NET code coverage tool
 *
 * **Example.**
 * Runs [`dotCover cover`](https://www.jetbrains.com/help/dotcover/dotCover__Console_Runner_Commands.html#cover) command for
 * a provided command line and generate report by running
 * [`dotCover report`](https://www.jetbrains.com/help/dotcover/dotCover__Console_Runner_Commands.html#report) command
 * ```
 * dotCover {
 *     executable = "/path/to/dotnet"
 *     commandLineArguments = "test MyProjectTests.dll"
 * }
 * ```
 *
 * **Example.**
 * Runs [`dotCover cover`](https://www.jetbrains.com/help/dotcover/dotCover__Console_Runner_Commands.html#cover) command for
 * a provided command line and passes additinal command-line arguments
 * ```
 * dotCover {
 *     executable = "/path/to/dotnet"
 *     commandLineArguments = "test MyProjectTests.dll"
 *     coverArguments = "--Output=/path/to/MyProjectTests.dcvr"
 *     generateReport = "false"
 * }
 * ```
 *
 * **Example.**
 * Runs [`dotCover merge`](https://www.jetbrains.com/help/dotcover/dotCover__Console_Runner_Commands.html#merge) command for
 * provided additional snapshots paths and then generate report by running
 * [`dotCover report`](https://www.jetbrains.com/help/dotcover/dotCover__Console_Runner_Commands.html#report) command
 * ```
 * dotCover {
 *     snapshotPaths = """
 *         /path/to/MyProject1Tests.dcvr
 *         /path/to/MyProject2Tests.dcvr
 *         /path/to/MyProject3Tests.dcvr
 *     """.trimIndent()
 * }
 * ```
 *
 *
 * @see dotCover
 */
open class DotCoverBuildStep : BuildStep {
    constructor(init: DotCoverBuildStep.() -> Unit = {}, base: DotCoverBuildStep? = null): super(base = base as BuildStep?) {
        type = "dotcover"
        param("dotNetCoverage.tool", "dotcover")
        init()
    }

    /**
     * Specify the path to dotCover CLT
     */
    var toolPath by stringParameter("dotNetCoverage.dotCover.home.path")

    /**
     * Specify path to an executable file to run the process under dotCover coverage profile and produce a dotCover snapshot file. This parameter is optional
     */
    var executable by stringParameter("dotNetCoverage.dotCover.coveredProcessExecutable")

    /**
     * Space or new-line separated command line parameters for covering process
     */
    var commandLineArguments by stringParameter("dotNetCoverage.dotCover.coveredProcessArguments")

    /**
     * Generates a TeamCity coverage report that will be displayed on the Code Coverage tab after the build is complete. Default value is `true`
     */
    var generateReport by booleanParameter("dotNetCoverage.dotCover.generateReport")

    /**
     * Specify dotCover snapshot (.dcvr) files paths separated by spaces or new lines.
     * Wildcards are supported. Note that you can merge snapshots generated only by the selected or earlier version of dotCover tool
     */
    var snapshotPaths by stringParameter("dotNetCoverage.dotCover.additionalShapshotPaths")

    /**
     * Specify a new-line separated list of filters for code coverage
     */
    var assemblyFilters by stringParameter("dotNetCoverage.dotCover.filters")

    /**
     * Specify a new-line separated list of attribute filters for code coverage.
     * Supported only with dotCover 2.0 or later
     */
    var attributeFilters by stringParameter("dotNetCoverage.dotCover.attributeFilters")

    /**
     * Enter additional new-line separated command line parameters for
     * [`dotCover cover`](https://www.jetbrains.com/help/dotcover/dotCover__Console_Runner_Commands.html#cover) command
     */
    var coverArguments by stringParameter("dotNetCoverage.dotCover.customCmd")

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
 * Adds a [dotCover build step](https://github.com/JetBrains/teamcity-dotnet-plugin) to run dotCover coverage tool
 *
 * **Example.**
 * Runs [`dotCover cover`](https://www.jetbrains.com/help/dotcover/dotCover__Console_Runner_Commands.html#cover) command for
 * a provided command line and generate report by running
 * [`dotCover report`](https://www.jetbrains.com/help/dotcover/dotCover__Console_Runner_Commands.html#report) command
 * ```
 * dotCover {
 *     executable = "/path/to/dotnet"
 *     commandLineArguments = "test MyProjectTests.dll"
 * }
 * ```
 *
 * **Example.**
 * Runs [`dotCover cover`](https://www.jetbrains.com/help/dotcover/dotCover__Console_Runner_Commands.html#cover) command for
 * a provided command line and passes additinal command-line arguments
 * ```
 * dotCover {
 *     executable = "/path/to/dotnet"
 *     commandLineArguments = "test MyProjectTests.dll"
 *     coverArguments = "--Output=/path/to/MyProjectTests.dcvr"
 *     generateReport = "false"
 * }
 * ```
 *
 * **Example.**
 * Runs [`dotCover merge`](https://www.jetbrains.com/help/dotcover/dotCover__Console_Runner_Commands.html#merge) command for
 * provided additional snapshots paths and then generate report by running
 * [`dotCover report`](https://www.jetbrains.com/help/dotcover/dotCover__Console_Runner_Commands.html#report) command
 * ```
 * dotCover {
 *     snapshotPaths = """
 *         /path/to/MyProject1Tests.dcvr
 *         /path/to/MyProject2Tests.dcvr
 *         /path/to/MyProject3Tests.dcvr
 *     """.trimIndent()
 * }
 * ```
 *
 *
 * @see DotnetBuildStep
 */
fun BuildSteps.dotCover(base: DotCoverBuildStep? = null, init: DotCoverBuildStep.() -> Unit = {}) {
    step(DotCoverBuildStep(init, base))
}
