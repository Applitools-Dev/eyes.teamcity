package jetbrains.buildServer.configs.kotlin.v2018_1.buildSteps

import jetbrains.buildServer.configs.kotlin.v2018_1.*

/**
 * A [build step](https://www.jetbrains.com/help/teamcity/?Gradle) running gradle script
 *
 * **Example.**
 * Adds a simple Gradle step with custom tasks and build file determined by Gradle.
 * Gralde Wrapper located in the checkout directory is used.
 * ```
 * gradle {
 *     name = "Build myproject"
 *     tasks = ":myproject:clean :myproject:build"
 * }
 * ```
 *
 * **Example.**
 * Add a Gradles build step with custom Gradle task and build file localted in also custom
 * [working directory](https://www.jetbrains.com/help/teamcity/?Build+Working+Directory).
 * Gradle incremental building feature is enabled.
 * Additional Gradle command line parameters is specified with a reference to a
 * [configuration parameter](https://www.jetbrains.com/help/teamcity/?Using+Build+Parameters).
 * Gralde build step is set up not to use Gradle Wrapper, so Gradle will be taken from the agent's GRADLE_HOME environment variable.
 * Additional [run parameter](https://www.jetbrains.com/help/teamcity/?Gradle#Run+Parameters) for printing stacktrace is enabled.
 * This step will be run inside a [Docker](https://www.jetbrains.com/help/teamcity/?Gradle#Docker+Settings) container.
 * IDEA-based [code coverage](https://www.jetbrains.com/help/teamcity/?Gradle#Code+Coverage) is enabled.
 * ```
 * gradle {
 *     name = "Test my project in Docker"
 *
 *     tasks = "clean test"
 *     buildFile = "build-test.gradle"
 *     incremental = true
 *     workingDir = "tests/"
 *     gradleParams = "%myproject.version%"
 *
 *     useGradleWrapper = false
 *
 *     enableStacktrace = true
 *
 *     coverageEngine = idea {
 *         includeClasses = """
 *             org.group.example.*
 *             org.group.common
 *         """.trimIndent()
 *         excludeClasses = "org.group.common.test.*"
 *     }
 *
 *     dockerImage = "gradle:jdk11"
 *     dockerImagePlatform = GradleBuildStep.ImagePlatform.Linux
 * }
 * ```
 *
 * **Example.**
 * Adds a Gradle build step with 'default' Gradle task and custom Gradle build file.
 * Gradle Wrapper using is disabled, so Gradle will be taken with a reference to an
 * [environment variable](https://www.jetbrains.com/help/teamcity/?Using+Build+Parameters).
 * JDK is set to the [environment variable](https://www.jetbrains.com/help/teamcity/?Using+Build+Parameters) value
 * with custom command line [parameters](https://www.jetbrains.com/help/teamcity/?Gradle#Java+Parameters).
 * This build step will run even if some previous build steps are failed.
 * ```
 * gradle {
 *     name = "Default run on JDK 11"
 *     executionMode = BuildStep.ExecutionMode.RUN_ON_FAILURE
 *
 *     buildFile = "build-dev.gradle"
 *     gradleHome = "%env.GRADLE_DEV_HOME%"
 *     useGradleWrapper = false
 *
 *     jdkHome = "%env.JDK_11_0%"
 *     jvmArgs = "-Xmx2048m"
 * }
 * ```
 *
 *
 * @see gradle
 */
open class GradleBuildStep() : BuildStep() {

    init {
        type = "gradle-runner"
    }

    constructor(init: GradleBuildStep.() -> Unit): this() {
        init()
    }

    /**
     * Space separated task names, when not set the 'default' task is used
     */
    var tasks by stringParameter("ui.gradleRunner.gradle.tasks.names")

    /**
     * Path to build file
     */
    var buildFile by stringParameter("ui.gradleRUnner.gradle.build.file")

    /**
     * When set to true the :buildDependents task will be run on projects affected by changes
     */
    var incremental by booleanParameter("ui.gradleRunner.gradle.incremental", trueValue = "true", falseValue = "")

    /**
     * Custom working directory for gradle script
     */
    var workingDir by stringParameter("teamcity.build.workingDir")

    /**
     * Path to the Gradle home directory (parent of 'bin' directory). Overrides agent GRADLE_HOME environment variable
     */
    var gradleHome by stringParameter("ui.gradleRunner.gradle.home")

    /**
     * Additional parameters will be added to the 'Gradle' command line
     */
    var gradleParams by stringParameter("ui.gradleRunner.additional.gradle.cmd.params")

    /**
     * Whether TeamCity should look for Gradle Wrapper scripts in the checkout directory and run script using it
     */
    var useGradleWrapper by booleanParameter("ui.gradleRunner.gradle.wrapper.useWrapper", trueValue = "true", falseValue = "")

    /**
     * Optional path to the Gradle wrapper script, relative to the working directory
     */
    var gradleWrapperPath by stringParameter("ui.gradleRunner.gradle.wrapper.path")

    /**
     * Whether Gradle should be executed with the -d option
     */
    var enableDebug by booleanParameter("ui.gradleRunner.gradle.debug.enabled", trueValue = "true", falseValue = "")

    /**
     * Whether Gradle should be executed with the -s option
     */
    var enableStacktrace by booleanParameter("ui.gradleRunner.gradle.stacktrace.enabled", trueValue = "true", falseValue = "")

    /**
     * Custom [JDK](https://www.jetbrains.com/help/teamcity/?Predefined+Build+Parameters#PredefinedBuildParameters-DefiningJava-relatedEnvironmentVariables) to use.
     * The default is JAVA_HOME environment variable or the agent's own Java.
     */
    var jdkHome by stringParameter("target.jdk.home")

    /**
     * Space-separated list of additional arguments for JVM
     */
    var jvmArgs by stringParameter()

    /**
     * Specifies coverage engine to use
     */
    var coverageEngine by compoundParameter<CoverageEngine>("teamcity.coverage.runner")

    sealed class CoverageEngine(value: String? = null): CompoundParam<CoverageEngine>(value) {
        class Idea() : CoverageEngine("IDEA") {

            /**
             * Newline-separated patterns for fully qualified class names to be analyzed by code coverage.
             * A pattern should start with a valid package name and can contain a wildcard, for example: org.apache.*
             */
            var includeClasses by stringParameter("teamcity.coverage.idea.includePatterns")

            /**
             * Newline-separated patterns for fully qualified class names to be excluded from the coverage. Exclude patterns have priority over include patterns.
             */
            var excludeClasses by stringParameter("teamcity.coverage.idea.excludePatterns")

        }

        class Jacoco() : CoverageEngine("JACOCO") {

            /**
             * Newline-delimited set of path patterns in the form of +|-:path to scan for classfiles to be analyzed.
             * Excluding libraries and test classes from analysis is recommended. Ant like patterns are supported.
             */
            var classLocations by stringParameter("teamcity.coverage.jacoco.classpath")

            /**
             * Newline-separated patterns for fully qualified class names to be excluded from the coverage.
             * Exclude patterns have priority over include patterns.
             */
            var excludeClasses by stringParameter("teamcity.coverage.jacoco.patterns")

            /**
             * JaCoCo version to use
             */
            var jacocoVersion by stringParameter("teamcity.tool.jacoco")

        }
    }

    fun idea(init: CoverageEngine.Idea.() -> Unit = {}) : CoverageEngine.Idea {
        val result = CoverageEngine.Idea()
        result.init()
        return result
    }

    fun jacoco(init: CoverageEngine.Jacoco.() -> Unit = {}) : CoverageEngine.Jacoco {
        val result = CoverageEngine.Jacoco()
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
 * Adds a [build step](https://www.jetbrains.com/help/teamcity/?Gradle) running gradle script
 *
 * **Example.**
 * Adds a simple Gradle step with custom tasks and build file determined by Gradle.
 * Gralde Wrapper located in the checkout directory is used.
 * ```
 * gradle {
 *     name = "Build myproject"
 *     tasks = ":myproject:clean :myproject:build"
 * }
 * ```
 *
 * **Example.**
 * Add a Gradles build step with custom Gradle task and build file localted in also custom
 * [working directory](https://www.jetbrains.com/help/teamcity/?Build+Working+Directory).
 * Gradle incremental building feature is enabled.
 * Additional Gradle command line parameters is specified with a reference to a
 * [configuration parameter](https://www.jetbrains.com/help/teamcity/?Using+Build+Parameters).
 * Gralde build step is set up not to use Gradle Wrapper, so Gradle will be taken from the agent's GRADLE_HOME environment variable.
 * Additional [run parameter](https://www.jetbrains.com/help/teamcity/?Gradle#Run+Parameters) for printing stacktrace is enabled.
 * This step will be run inside a [Docker](https://www.jetbrains.com/help/teamcity/?Gradle#Docker+Settings) container.
 * IDEA-based [code coverage](https://www.jetbrains.com/help/teamcity/?Gradle#Code+Coverage) is enabled.
 * ```
 * gradle {
 *     name = "Test my project in Docker"
 *
 *     tasks = "clean test"
 *     buildFile = "build-test.gradle"
 *     incremental = true
 *     workingDir = "tests/"
 *     gradleParams = "%myproject.version%"
 *
 *     useGradleWrapper = false
 *
 *     enableStacktrace = true
 *
 *     coverageEngine = idea {
 *         includeClasses = """
 *             org.group.example.*
 *             org.group.common
 *         """.trimIndent()
 *         excludeClasses = "org.group.common.test.*"
 *     }
 *
 *     dockerImage = "gradle:jdk11"
 *     dockerImagePlatform = GradleBuildStep.ImagePlatform.Linux
 * }
 * ```
 *
 * **Example.**
 * Adds a Gradle build step with 'default' Gradle task and custom Gradle build file.
 * Gradle Wrapper using is disabled, so Gradle will be taken with a reference to an
 * [environment variable](https://www.jetbrains.com/help/teamcity/?Using+Build+Parameters).
 * JDK is set to the [environment variable](https://www.jetbrains.com/help/teamcity/?Using+Build+Parameters) value
 * with custom command line [parameters](https://www.jetbrains.com/help/teamcity/?Gradle#Java+Parameters).
 * This build step will run even if some previous build steps are failed.
 * ```
 * gradle {
 *     name = "Default run on JDK 11"
 *     executionMode = BuildStep.ExecutionMode.RUN_ON_FAILURE
 *
 *     buildFile = "build-dev.gradle"
 *     gradleHome = "%env.GRADLE_DEV_HOME%"
 *     useGradleWrapper = false
 *
 *     jdkHome = "%env.JDK_11_0%"
 *     jvmArgs = "-Xmx2048m"
 * }
 * ```
 *
 *
 * @see GradleBuildStep
 */
fun BuildSteps.gradle(init: GradleBuildStep.() -> Unit): GradleBuildStep {
    val result = GradleBuildStep(init)
    step(result)
    return result
}
