package jetbrains.buildServer.configs.kotlin.v2017_2.buildSteps

import jetbrains.buildServer.configs.kotlin.v2017_2.*

/**
 * A [dotnet test step](https://github.com/JetBrains/teamcity-dotnet-plugin) to run .NET CLI command
 *
 * **Example.**
 * Runs [`dotnet test`](https://learn.microsoft.com/en-us/dotnet/core/tools/dotnet-test) command for all the solution files in the checkout directory.
 * Does not try to build the project, assumes this is already done by some previous step.
 * ```
 * dotnetTest {
 *     projects = "*.sln"
 *     skipBuild = true
 * }
 * ```
 *
 * **Example.**
 * Runs [`dotnet test`](https://learn.microsoft.com/en-us/dotnet/core/tools/dotnet-test) for the MyProject.sln solution file.
 * Collects code coverage with help of [JetBrains DotCover](https://www.jetbrains.com/dotcover/) for the specified assemblies.
 * ```
 * dotnetTest {
 *     projects = "MyProject.sln"
 *     skipBuild = true
 *     coverage = dotcover {
 *         toolPath = "%teamcity.tool.JetBrains.dotCover.CommandLineTools.DEFAULT%"
 *         assemblyFilters = "build/*.dll"
 *     }
 * }
 * ```
 * <pre style='display:none'>*/</pre>
 *
 * **Example.**
 * Runs [`dotnet test`](https://learn.microsoft.com/en-us/dotnet/core/tools/dotnet-test) for the specified test assemblies.
 * ```
 * dotnetTest {
 *     projects = "tests/*.dll"
 *     excludedProjects = "tests/ExcludedTests.dll"
 *     skipBuild = true
 * }
 * ```
 * <pre style='display:none'>*/</pre>
 *
 *
 * @see dotnetTest
 */
open class DotnetTestStep() : BuildStep() {

    init {
        type = "dotnet"
        param("command", "test")
    }

    constructor(init: DotnetTestStep.() -> Unit): this() {
        init()
    }

    /**
     * Specify paths to projects and solutions. Wildcards are supported.
     */
    var projects by stringParameter("paths")

    /**
     * Specify paths to excluded projects. Wildcards are supported.
     */
    var excludedProjects by stringParameter("excludedPaths")

    /**
     * [Build working directory](https://www.jetbrains.com/help/teamcity/?Build+Working+Directory) for
     * script,
     * specify it if it is different from the [checkout
     * directory](https://www.jetbrains.com/help/teamcity/?Build+Checkout+Directory).
     */
    var workingDir by stringParameter("teamcity.build.workingDir")

    /**
     * Specify the test case filter.
     */
    var filter by stringParameter("test.testCaseFilter")

    /**
     * Target framework to test for.
     */
    var framework by stringParameter()

    /**
     * Target configuration to test for.
     */
    var configuration by stringParameter()

    /**
     * The directory where to find the binaries to be run.
     */
    var outputDir by stringParameter()

    /**
     * Do not build the project before testing
     */
    var skipBuild by booleanParameter(trueValue = "true", falseValue = "")

    /**
     * Whether TeamCity should run tests in a single session
     */
    var singleSession by booleanParameter(trueValue = "true", falseValue = "")

    /**
     * Run tests that match the given expression.
     */
    var settingsFile by stringParameter("test.settingsFile")

    /**
     * Rerun failed tests until they pass or until the maximum number of attempts is reached.
     */
    var maxRetries by stringParameter("test.retry.maxRetries")

    /**
     * Enter additional command line parameters for dotnet test.
     */
    var args by stringParameter()

    /**
     * Specify logging verbosity
     *
     *
     * @see Verbosity
     */
    var logging by enumParameter<Verbosity>("verbosity")

    /**
     * .NET SDK versions separated by semicolon to be required on agents.
     */
    var sdk by stringParameter("required.sdk")

    /**
     * Specifies coverage tool to use
     */
    var coverage by compoundParameter<Coverage>("dotNetCoverage.tool")

    sealed class Coverage(value: String? = null): CompoundParam<Coverage>(value) {
        class Dotcover() : Coverage("dotcover") {

            /**
             * Specify the path to dotCover CLT.
             */
            var toolPath by stringParameter("dotNetCoverage.dotCover.home.path")

            /**
             * Specify a new-line separated list of filters for code coverage.
             */
            var assemblyFilters by stringParameter("dotNetCoverage.dotCover.filters")

            /**
             * Specify a new-line separated list of attribute filters for code coverage.
             * Supported only with dotCover 2.0 or later.
             */
            var attributeFilters by stringParameter("dotNetCoverage.dotCover.attributeFilters")

            /**
             * Enter additional new-line separated command line parameters for dotCover.
             */
            var args by stringParameter("dotNetCoverage.dotCover.customCmd")

        }
    }

    fun dotcover(init: Coverage.Dotcover.() -> Unit = {}) : Coverage.Dotcover {
        val result = Coverage.Dotcover()
        result.init()
        return result
    }

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
     * Logging verbosity
     */
    enum class Verbosity {
        Quiet,
        Minimal,
        Normal,
        Detailed,
        Diagnostic;

    }
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
    override fun validate(consumer: ErrorConsumer) {
        super.validate(consumer)
    }
}


/**
 * Adds a [dotnet test step](https://github.com/JetBrains/teamcity-dotnet-plugin) to run .NET CLI command
 *
 * **Example.**
 * Runs [`dotnet test`](https://learn.microsoft.com/en-us/dotnet/core/tools/dotnet-test) command for all the solution files in the checkout directory.
 * Does not try to build the project, assumes this is already done by some previous step.
 * ```
 * dotnetTest {
 *     projects = "*.sln"
 *     skipBuild = true
 * }
 * ```
 *
 * **Example.**
 * Runs [`dotnet test`](https://learn.microsoft.com/en-us/dotnet/core/tools/dotnet-test) for the MyProject.sln solution file.
 * Collects code coverage with help of [JetBrains DotCover](https://www.jetbrains.com/dotcover/) for the specified assemblies.
 * ```
 * dotnetTest {
 *     projects = "MyProject.sln"
 *     skipBuild = true
 *     coverage = dotcover {
 *         toolPath = "%teamcity.tool.JetBrains.dotCover.CommandLineTools.DEFAULT%"
 *         assemblyFilters = "build/*.dll"
 *     }
 * }
 * ```
 * <pre style='display:none'>*/</pre>
 *
 * **Example.**
 * Runs [`dotnet test`](https://learn.microsoft.com/en-us/dotnet/core/tools/dotnet-test) for the specified test assemblies.
 * ```
 * dotnetTest {
 *     projects = "tests/*.dll"
 *     excludedProjects = "tests/ExcludedTests.dll"
 *     skipBuild = true
 * }
 * ```
 * <pre style='display:none'>*/</pre>
 *
 *
 * @see DotnetTestStep
 */
fun BuildSteps.dotnetTest(init: DotnetTestStep.() -> Unit): DotnetTestStep {
    val result = DotnetTestStep(init)
    step(result)
    return result
}
