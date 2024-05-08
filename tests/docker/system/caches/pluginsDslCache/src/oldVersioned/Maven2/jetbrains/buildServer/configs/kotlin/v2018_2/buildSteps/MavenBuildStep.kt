package jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps

import jetbrains.buildServer.configs.kotlin.v2018_2.*

/**
 * A [build step](https://www.jetbrains.com/help/teamcity/?Maven) running maven
 *
 * **Example.**
 * Adds a simple Maven build step in the [checkout directory](https://www.jetbrains.com/help/teamcity/?Build+Checkout+Directory)
 * and pom.xml located in the checkout directory.
 * Default bundled [maven tool](https://www.jetbrains.com/help/teamcity/?Installing+Agent+Tools) is used, JDK from JAVA_HOME environment variable is used.
 * ```
 * maven {
 *     name = "Install step"
 *     goals = "clean install"
 * }
 * ```
 *
 * **Example.**
 * Adds a Maven build step with custom path to project root and pom.xml. Extra maven command line argumant is specified.
 * Custom bundled maven version is specified. JDK is set to the [environment variable](https://www.jetbrains.com/help/teamcity/?Using+Build+Parameters) value.
 * ```
 * maven {
 *     goals = "clean install"
 *     pomLocation = "intergation-tests/pom.xml"
 *     runnerArgs = "-X"
 *     workingDir = "intergation-tests"
 *
 *     mavenVersion = bundled_3_9()
 *     userSettingsSelection = "settings.xml"
 *
 *     jdkHome = "%env.JDK_11_0_x64%"
 *     jvmArgs = "-Xmx2048m"
 * }
 * ```
 *
 * **Example.**
 * Adds a Maven build step with customized path to maven executable and JDK.
 * Custom [Maven settings](https://www.jetbrains.com/help/teamcity/?Maven+Server-Side+Settings) provided on project level are set.
 * This build step will run even if some previous build steps are failed.
 * ```
 * maven {
 *     executionMode = BuildStep.ExecutionMode.RUN_ON_FAILURE
 *     goals = "clean install"
 *     mavenVersion = custom {
 *         path = "my/own/mvn"
 *     }
 *     userSettingsSelection = "settings.xml"
 *     jdkHome = "/path/to/java/home"
 * }
 * ```
 *
 * **Example.**
 * Adds a Maven build step with Maven executable taken from M2_HOME environment variable, otherwise the current default version is used.
 * [Incremental Building](https://www.jetbrains.com/help/teamcity/?Maven#Incremental+Building) is enabled,
 * and [local artifact repository settings](https://www.jetbrains.com/help/teamcity/?Maven#Local+Artifact+Repository+Settings) is set to use
 * a separate repository to store artifacts, produced by all builds of the current build configuration.
 * IDEA-based [code coverage](https://www.jetbrains.com/help/teamcity/?Maven#Code+Coverage) is enabled.
 * ```
 * maven {
 *     goals = "clean install"
 *     mavenVersion = auto()
 *     localRepoScope = MavenBuildStep.RepositoryScope.BUILD_CONFIGURATION
 *     isIncremental = true
 *
 *     coverageEngine = idea {
 *         includeClasses = """
 *             org.myorg.*
 *             org.package.sample
 *         """.trimIndent()
 *         excludeClasses = "org.myorg.test"
 *     }
 * }
 * ```
 *
 * **Example.**
 * Adds a Maven build step to be run inside a [Docker](https://www.jetbrains.com/help/teamcity/?Maven#Docker+Settings) container.
 * This step will we executed only if build status is sucessfull for previous build steps and extra condition is met.
 * ```
 * maven {
 *     executionMode = BuildStep.ExecutionMode.RUN_ON_SUCCESS
 *     conditions {
 *         equals("teamcity.build.branch", "release")
 *     }
 *
 *     goals = "clean install"
 *     mavenVersion = auto()
 *
 *     dockerImage = "dockerImage"
 *     dockerImagePlatform = MavenBuildStep.ImagePlatform.Linux
 *     dockerPull = true
 * }
 * ```
 *
 *
 * @see maven
 */
open class MavenBuildStep() : BuildStep() {

    init {
        type = "Maven2"
    }

    constructor(init: MavenBuildStep.() -> Unit): this() {
        init()
    }

    /**
     * Space-separated list of goals to execute
     */
    var goals by stringParameter()

    /**
     * Path to POM file. Should be relative to the checkout directory.
     */
    var pomLocation by stringParameter()

    /**
     * Additional Maven command line parameters.
     */
    var runnerArgs by stringParameter()

    /**
     * Custom working directory for maven. If not specified, the checkout directory is used.
     */
    var workingDir by stringParameter("teamcity.build.workingDir")

    /**
     * Maven version to use
     */
    var mavenVersion by compoundParameter<MavenVersion>("maven.path")

    sealed class MavenVersion(value: String? = null): CompoundParam<MavenVersion>(value) {
        class Default() : MavenVersion("%teamcity.tool.maven.AUTO%") {

        }

        class Auto() : MavenVersion("%teamcity.tool.maven.AUTO%") {

        }

        class DefaultProvidedVersion() : MavenVersion("%teamcity.tool.maven.DEFAULT%") {

        }

        class Custom() : MavenVersion(null) {

            /**
             * The path to a custom Maven installation
             */
            var path by stringParameter("maven.path")

        }

        class Bundled_2() : MavenVersion("%teamcity.tool.maven%") {

        }

        class Bundled_3_0() : MavenVersion("%teamcity.tool.maven3%") {

        }

        class Bundled_3_1() : MavenVersion("%teamcity.tool.maven3_1%") {

        }

        class Bundled_3_2() : MavenVersion("%teamcity.tool.maven3_2%") {

        }

        class Bundled_3_3() : MavenVersion("%teamcity.tool.maven3_3%") {

        }

        class Bundled_3_5() : MavenVersion("%teamcity.tool.maven3_5%") {

        }

        class Bundled_3_6() : MavenVersion("%teamcity.tool.maven3_6%") {

        }

        class Bundled_3_8() : MavenVersion("%teamcity.tool.maven3_8%") {

        }

        class Bundled_3_9() : MavenVersion("%teamcity.tool.maven3_9%") {

        }
    }

    /**
     * In TeamCity 10.0 the meaning of this option was: Maven version specified in M2_HOME environment variable, if the environment variable is empty, then the Maven version 3.0.5.
     * In TeamCity 2017.1 this option is renamed to auto(), please use it instead.
     * If you want to always use the default Maven version provided by TeamCity server, switch to defaultProvidedVersion().
     *
     *
     * @see auto
     * @see defaultProvidedVersion
     */
    @Deprecated("Please use auto() instead", replaceWith = ReplaceWith("auto()"))
    fun default() = MavenVersion.Default()

    /**
     * Maven version specified by the M2_HOME environment variable.
     * If the environment variable is empty, then the default Maven version provided by TeamCity server will be used.
     */
    fun auto() = MavenVersion.Auto()

    /**
     * The default Maven version provided by TeamCity server
     */
    fun defaultProvidedVersion() = MavenVersion.DefaultProvidedVersion()

    /**
     * The custom Maven version found at the specified path
     */
    fun custom(init: MavenVersion.Custom.() -> Unit = {}) : MavenVersion.Custom {
        val result = MavenVersion.Custom()
        result.init()
        return result
    }

    /**
     * Use maven 2 bundled with TeamCity
     */
    fun bundled_2() = MavenVersion.Bundled_2()

    /**
     * Use maven 3.0.5 bundled with TeamCity
     */
    fun bundled_3_0() = MavenVersion.Bundled_3_0()

    /**
     * Use maven 3.1.1 bundled with TeamCity
     */
    fun bundled_3_1() = MavenVersion.Bundled_3_1()

    /**
     * Use maven 3.2.5 bundled with TeamCity
     */
    fun bundled_3_2() = MavenVersion.Bundled_3_2()

    /**
     * Use maven 3.3.9 bundled with TeamCity
     */
    fun bundled_3_3() = MavenVersion.Bundled_3_3()

    /**
     * Use maven 3.5.4 bundled with TeamCity
     */
    fun bundled_3_5() = MavenVersion.Bundled_3_5()

    /**
     * Use maven 3.6.3 bundled with TeamCity
     */
    fun bundled_3_6() = MavenVersion.Bundled_3_6()

    /**
     * Use maven 3.8.6 bundled with TeamCity
     */
    fun bundled_3_8() = MavenVersion.Bundled_3_8()

    /**
     * Use maven 3.9.6 bundled with TeamCity
     */
    fun bundled_3_9() = MavenVersion.Bundled_3_9()

    /**
     * Use one of the predefined settings files or provide a custom path. By default, the standard Maven settings file location is used.
     */
    var userSettingsSelection by stringParameter()

    /**
     * The path to a user settings file
     */
    var userSettingsPath by stringParameter()

    /**
     * Use own local artifact repository
     */
    @Deprecated("Please use localRepoScope instead.", replaceWith = ReplaceWith("localRepoScope"))
    var useOwnLocalRepo by booleanParameter()

    var localRepoScope by enumParameter<RepositoryScope>(mapping = RepositoryScope.mapping)

    /**
     * Enable incremental building
     */
    var isIncremental by booleanParameter()

    /**
     * A path to [JDK](https://www.jetbrains.com/help/teamcity/?Predefined+Build+Parameters#PredefinedBuildParameters-DefiningJava-relatedEnvironmentVariables) to use.
     * By default the JAVA_HOME environment variable or the agent's own Java is used.
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
     * Maven local repository scope.
     */
    enum class RepositoryScope {
        /**
         * Shared by all build configurations and all agents on the machine.
         */
        MAVEN_DEFAULT,
        /**
         * Shared by all the builds on the agent. Can be cleaned by agent to free disk space.
         */
        AGENT,
        /**
         * Shared by all the builds in a single build configuration. Can be cleaned by agent to free disk space.
         */
        BUILD_CONFIGURATION;

        companion object {
            val mapping = mapOf<RepositoryScope, String>(MAVEN_DEFAULT to "mavenDefault", AGENT to "agent", BUILD_CONFIGURATION to "buildConfiguration")
        }

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
 * Adds a [build step](https://www.jetbrains.com/help/teamcity/?Maven) running maven
 *
 * **Example.**
 * Adds a simple Maven build step in the [checkout directory](https://www.jetbrains.com/help/teamcity/?Build+Checkout+Directory)
 * and pom.xml located in the checkout directory.
 * Default bundled [maven tool](https://www.jetbrains.com/help/teamcity/?Installing+Agent+Tools) is used, JDK from JAVA_HOME environment variable is used.
 * ```
 * maven {
 *     name = "Install step"
 *     goals = "clean install"
 * }
 * ```
 *
 * **Example.**
 * Adds a Maven build step with custom path to project root and pom.xml. Extra maven command line argumant is specified.
 * Custom bundled maven version is specified. JDK is set to the [environment variable](https://www.jetbrains.com/help/teamcity/?Using+Build+Parameters) value.
 * ```
 * maven {
 *     goals = "clean install"
 *     pomLocation = "intergation-tests/pom.xml"
 *     runnerArgs = "-X"
 *     workingDir = "intergation-tests"
 *
 *     mavenVersion = bundled_3_9()
 *     userSettingsSelection = "settings.xml"
 *
 *     jdkHome = "%env.JDK_11_0_x64%"
 *     jvmArgs = "-Xmx2048m"
 * }
 * ```
 *
 * **Example.**
 * Adds a Maven build step with customized path to maven executable and JDK.
 * Custom [Maven settings](https://www.jetbrains.com/help/teamcity/?Maven+Server-Side+Settings) provided on project level are set.
 * This build step will run even if some previous build steps are failed.
 * ```
 * maven {
 *     executionMode = BuildStep.ExecutionMode.RUN_ON_FAILURE
 *     goals = "clean install"
 *     mavenVersion = custom {
 *         path = "my/own/mvn"
 *     }
 *     userSettingsSelection = "settings.xml"
 *     jdkHome = "/path/to/java/home"
 * }
 * ```
 *
 * **Example.**
 * Adds a Maven build step with Maven executable taken from M2_HOME environment variable, otherwise the current default version is used.
 * [Incremental Building](https://www.jetbrains.com/help/teamcity/?Maven#Incremental+Building) is enabled,
 * and [local artifact repository settings](https://www.jetbrains.com/help/teamcity/?Maven#Local+Artifact+Repository+Settings) is set to use
 * a separate repository to store artifacts, produced by all builds of the current build configuration.
 * IDEA-based [code coverage](https://www.jetbrains.com/help/teamcity/?Maven#Code+Coverage) is enabled.
 * ```
 * maven {
 *     goals = "clean install"
 *     mavenVersion = auto()
 *     localRepoScope = MavenBuildStep.RepositoryScope.BUILD_CONFIGURATION
 *     isIncremental = true
 *
 *     coverageEngine = idea {
 *         includeClasses = """
 *             org.myorg.*
 *             org.package.sample
 *         """.trimIndent()
 *         excludeClasses = "org.myorg.test"
 *     }
 * }
 * ```
 *
 * **Example.**
 * Adds a Maven build step to be run inside a [Docker](https://www.jetbrains.com/help/teamcity/?Maven#Docker+Settings) container.
 * This step will we executed only if build status is sucessfull for previous build steps and extra condition is met.
 * ```
 * maven {
 *     executionMode = BuildStep.ExecutionMode.RUN_ON_SUCCESS
 *     conditions {
 *         equals("teamcity.build.branch", "release")
 *     }
 *
 *     goals = "clean install"
 *     mavenVersion = auto()
 *
 *     dockerImage = "dockerImage"
 *     dockerImagePlatform = MavenBuildStep.ImagePlatform.Linux
 *     dockerPull = true
 * }
 * ```
 *
 *
 * @see MavenBuildStep
 */
fun BuildSteps.maven(init: MavenBuildStep.() -> Unit): MavenBuildStep {
    val result = MavenBuildStep(init)
    step(result)
    return result
}
