package jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps

import jetbrains.buildServer.configs.kotlin.v2018_2.*

/**
 *
 * **Example.**
 * Adds a simple Qodana step for analyzing JVM-based projects
 * ```
 * qodana {
 *     linter = jvm {}
 * }
 * ```
 *
 * **Example.**
 * Adds a simple Qodana step for analyzing Python projects
 * ```
 * qodana {
 *     linter = python {}
 * }
 * ```
 *
 * **Example.**
 * Adds a Qodana step for analyzing JVM-based projects and uses `qodana.sarif.json` file as a baseline
 * ```
 * qodana {
 *     linter = jvm {}
 *     additionalQodanaArguments = "--baseline qodana.sarif.json"
 * }
 * ```
 *
 * **Example.**
 * Adds a Qodana step for analyzing JVM-based projects using provided `qodana.starter` inspection profile
 * ```
 * qodana {
 *     linter = jvm {}
 *     inspectionProfile = embedded {
 *         name = "qodana.starter"
 *     }
 * }
 * ```
 *
 * **Example.**
 * Adds a Qodana step for analyzing JVM-based projects using specific version of IntelliJ Community
 * ```
 * qodana {
 *     linter = jvmCommunity {
 *         version = Qodana.JVMCommunityVersion.v2022_1
 *     }
 * }
 * ```
 *
 *
 * @see qodana
 */
open class Qodana() : BuildStep() {

    init {
        type = "Qodana"
    }

    constructor(init: Qodana.() -> Unit): this() {
        init()
    }

    /**
     * Working directory
     */
    var workingDir by stringParameter("teamcity.build.workingDir")

    /**
     * Provide this name if you have several Qodana steps in one build, or you combine several builds into
     * one composite configuration. If empty, auto-generated step name will be used
     */
    var projectKey by stringParameter("project-key")

    /**
     * Enable report problems as tests
     */
    var reportAsTests by booleanParameter("report-as-test")

    /**
     * Tool names separated by ','. Example tool = "Code Inspection"
     */
    var tools by stringParameter()

    /**
     * Linter that performs the analysis
     */
    var linter by compoundParameter<Linter>("namesAndTags")

    sealed class Linter(value: String? = null): CompoundParam<Linter>(value) {
        abstract fun validate(consumer: ErrorConsumer)

        class Php() : Linter("public-image-qodana-php") {

            var version by enumParameter<PHPVersion>("linterVersion", mapping = PHPVersion.mapping)

            override fun validate(consumer: ErrorConsumer) {
            }
        }

        class Jvm() : Linter("public-image-qodana-jvm") {

            var version by enumParameter<JVMVersion>("linterVersion", mapping = JVMVersion.mapping)

            override fun validate(consumer: ErrorConsumer) {
            }
        }

        class JvmCommunity() : Linter("public-image-qodana-jvm-community") {

            var version by enumParameter<JVMCommunityVersion>("linterVersion", mapping = JVMCommunityVersion.mapping)

            override fun validate(consumer: ErrorConsumer) {
            }
        }

        class Android() : Linter("public-image-qodana-jvm-android") {

            var version by enumParameter<AndroidVersion>("linterVersion", mapping = AndroidVersion.mapping)

            override fun validate(consumer: ErrorConsumer) {
            }
        }

        class Python() : Linter("public-image-qodana-python") {

            var version by enumParameter<PythonVersion>("linterVersion", mapping = PythonVersion.mapping)

            override fun validate(consumer: ErrorConsumer) {
            }
        }

        class Javascript() : Linter("public-image-qodana-js") {

            var version by enumParameter<JSVersion>("linterVersion", mapping = JSVersion.mapping)

            override fun validate(consumer: ErrorConsumer) {
            }
        }

        class Go() : Linter("public-image-qodana-go") {

            var version by enumParameter<GoVersion>("linterVersion", mapping = GoVersion.mapping)

            override fun validate(consumer: ErrorConsumer) {
            }
        }

        class DotNet() : Linter("public-image-qodana-dot-net") {

            var version by enumParameter<DotNetVersion>("linterVersion", mapping = DotNetVersion.mapping)

            override fun validate(consumer: ErrorConsumer) {
            }
        }

        class CustomLinter() : Linter("custom") {

            var image by stringParameter("namesAndTagsCustom")

            override fun validate(consumer: ErrorConsumer) {
                if (image == null && !hasParam("namesAndTagsCustom")) {
                    consumer.consumePropertyError("linter.image", "mandatory 'linter.image' property is not specified")
                }
            }
        }
    }

    /**
     * Official Docker image for analyzing PHP projects
     */
    fun php(init: Linter.Php.() -> Unit = {}) : Linter.Php {
        val result = Linter.Php()
        result.init()
        return result
    }

    /**
     * Official Docker image for analyzing JVM projects
     */
    fun jvm(init: Linter.Jvm.() -> Unit = {}) : Linter.Jvm {
        val result = Linter.Jvm()
        result.init()
        return result
    }

    /**
     * Official Docker image for analyzing JVM projects with IntelliJ Community edition
     */
    fun jvmCommunity(init: Linter.JvmCommunity.() -> Unit = {}) : Linter.JvmCommunity {
        val result = Linter.JvmCommunity()
        result.init()
        return result
    }

    /**
     * Official Docker image for analyzing Android projects
     */
    fun android(init: Linter.Android.() -> Unit = {}) : Linter.Android {
        val result = Linter.Android()
        result.init()
        return result
    }

    /**
     * Official Docker image for analyzing Python projects
     */
    fun python(init: Linter.Python.() -> Unit = {}) : Linter.Python {
        val result = Linter.Python()
        result.init()
        return result
    }

    /**
     * Official Docker image for analyzing JavaScript projects
     */
    fun javascript(init: Linter.Javascript.() -> Unit = {}) : Linter.Javascript {
        val result = Linter.Javascript()
        result.init()
        return result
    }

    /**
     * Official Docker image for analyzing Go projects
     */
    fun go(init: Linter.Go.() -> Unit = {}) : Linter.Go {
        val result = Linter.Go()
        result.init()
        return result
    }

    /**
     * Official Docker image for analyzing .NET projects
     */
    fun dotNet(init: Linter.DotNet.() -> Unit = {}) : Linter.DotNet {
        val result = Linter.DotNet()
        result.init()
        return result
    }

    /**
     * Custom Docker image
     */
    fun customLinter(init: Linter.CustomLinter.() -> Unit = {}) : Linter.CustomLinter {
        val result = Linter.CustomLinter()
        result.init()
        return result
    }

    /**
     * Inspection profile
     */
    var inspectionProfile by compoundParameter<InspectionProfile>("code-inspection-xml-config")

    sealed class InspectionProfile(value: String? = null): CompoundParam<InspectionProfile>(value) {
        abstract fun validate(consumer: ErrorConsumer)

        class Default() : InspectionProfile("Default") {

            override fun validate(consumer: ErrorConsumer) {
            }
        }

        class Embedded() : InspectionProfile("Name") {

            var name by stringParameter("code-inspection-profile-name")

            override fun validate(consumer: ErrorConsumer) {
                if (name == null && !hasParam("code-inspection-profile-name")) {
                    consumer.consumePropertyError("inspectionProfile.name", "mandatory 'inspectionProfile.name' property is not specified")
                }
            }
        }

        class CustomProfile() : InspectionProfile("Custom") {

            var path by stringParameter("code-inspection-custom-xml-config-path")

            override fun validate(consumer: ErrorConsumer) {
                if (path == null && !hasParam("code-inspection-custom-xml-config-path")) {
                    consumer.consumePropertyError("inspectionProfile.path", "mandatory 'inspectionProfile.path' property is not specified")
                }
            }
        }
    }

    /**
     * Default inspection profile
     */
    fun default() = InspectionProfile.Default()

    /**
     * Select embedded profile name
     */
    fun embedded(init: InspectionProfile.Embedded.() -> Unit = {}) : InspectionProfile.Embedded {
        val result = InspectionProfile.Embedded()
        result.init()
        return result
    }

    /**
     * Path to the custom profile
     */
    fun customProfile(init: InspectionProfile.CustomProfile.() -> Unit = {}) : InspectionProfile.CustomProfile {
        val result = InspectionProfile.CustomProfile()
        result.init()
        return result
    }

    /**
     * Arguments for running a Docker image
     */
    @Deprecated("Use `additionalDockerArguments` instead", replaceWith = ReplaceWith("additionalDockerArguments"))
    var argumentsCommandDocker by stringParameter("arguments-command-docker")

    /**
     * Additional Docker arguments
     */
    var additionalDockerArguments by stringParameter("arguments-command-docker")

    /**
     * Entry points for running a Docker image
     */
    @Deprecated("Use `additionalQodanaArguments` instead", replaceWith = ReplaceWith("additionalQodanaArguments"))
    var argumentsEntryPointDocker by stringParameter("arguments-entry-point-docker")

    /**
     * Additional Qodana arguments
     */
    var additionalQodanaArguments by stringParameter("arguments-entry-point-docker")

    /**
     * Cloud Token
     */
    var cloudToken by stringParameter("secure:cloud-token")

    /**
     * By setting this property, you agree to let Qodana collect statistical information during linter
     * execution. It lets us better improve linter performance.
     * All data is collected anonymously in accordance with the JetBrains Privacy Policy:
     * https://www.jetbrains.com/legal/docs/privacy/privacy/
     */
    var collectAnonymousStatistics by booleanParameter("collect-anonymous-statistics")

    enum class JVMCommunityVersion {
        v2021_2,
        v2021_3,
        v2022_1,
        v2022_2,
        LATEST;

        companion object {
            val mapping = mapOf<JVMCommunityVersion, String>(v2021_2 to "2021.2", v2021_3 to "2021.3", v2022_1 to "2022.1", v2022_2 to "2022.2", LATEST to "latest")
        }

    }
    enum class JVMVersion {
        LATEST;

        companion object {
            val mapping = mapOf<JVMVersion, String>(LATEST to "latest")
        }

    }
    enum class PythonVersion {
        LATEST;

        companion object {
            val mapping = mapOf<PythonVersion, String>(LATEST to "latest")
        }

    }
    enum class JSVersion {
        LATEST;

        companion object {
            val mapping = mapOf<JSVersion, String>(LATEST to "latest")
        }

    }
    enum class GoVersion {
        LATEST;

        companion object {
            val mapping = mapOf<GoVersion, String>(LATEST to "latest")
        }

    }
    enum class AndroidVersion {
        LATEST;

        companion object {
            val mapping = mapOf<AndroidVersion, String>(LATEST to "latest")
        }

    }
    enum class PHPVersion {
        LATEST;

        companion object {
            val mapping = mapOf<PHPVersion, String>(LATEST to "latest")
        }

    }
    enum class DotNetVersion {
        LATEST;

        companion object {
            val mapping = mapOf<DotNetVersion, String>(LATEST to "latest")
        }

    }
    override fun validate(consumer: ErrorConsumer) {
        super.validate(consumer)
        if (linter == null && !hasParam("namesAndTags")) {
            consumer.consumePropertyError("linter", "mandatory 'linter' property is not specified")
        }
        linter?.validate(consumer)
        inspectionProfile?.validate(consumer)
    }
}


/**
 * Add 'Qodana' build step
 *
 * **Example.**
 * Adds a simple Qodana step for analyzing JVM-based projects
 * ```
 * qodana {
 *     linter = jvm {}
 * }
 * ```
 *
 * **Example.**
 * Adds a simple Qodana step for analyzing Python projects
 * ```
 * qodana {
 *     linter = python {}
 * }
 * ```
 *
 * **Example.**
 * Adds a Qodana step for analyzing JVM-based projects and uses `qodana.sarif.json` file as a baseline
 * ```
 * qodana {
 *     linter = jvm {}
 *     additionalQodanaArguments = "--baseline qodana.sarif.json"
 * }
 * ```
 *
 * **Example.**
 * Adds a Qodana step for analyzing JVM-based projects using provided `qodana.starter` inspection profile
 * ```
 * qodana {
 *     linter = jvm {}
 *     inspectionProfile = embedded {
 *         name = "qodana.starter"
 *     }
 * }
 * ```
 *
 * **Example.**
 * Adds a Qodana step for analyzing JVM-based projects using specific version of IntelliJ Community
 * ```
 * qodana {
 *     linter = jvmCommunity {
 *         version = Qodana.JVMCommunityVersion.v2022_1
 *     }
 * }
 * ```
 *
 *
 * @see Qodana
 */
fun BuildSteps.qodana(init: Qodana.() -> Unit): Qodana {
    val result = Qodana(init)
    step(result)
    return result
}
