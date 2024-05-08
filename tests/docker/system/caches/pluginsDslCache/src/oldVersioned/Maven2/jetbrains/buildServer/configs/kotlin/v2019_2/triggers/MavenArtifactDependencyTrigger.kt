package jetbrains.buildServer.configs.kotlin.v2019_2.triggers

import jetbrains.buildServer.configs.kotlin.v2019_2.*

/**
 * A [trigger](https://www.jetbrains.com/help/teamcity/?Configuring+Maven+Triggers#ConfiguringMavenTriggers-MavenArtifactDependencyTrigger)
 * running builds when there is a modification of the maven dependency content.
 *
 * **Example.**
 * Adds a simple Maven Artifact Dependency Trigger for given atrifact the latest snapshot version with default Maven settings.
 * ```
 * mavenArtifact {
 *     groupId = "org.example.group"
 *     artifactId = "example-atrifact"
 *     version = "1.0-SNAPSHOT"
 *     userSettingsSelection = "userSettingsSelection:default"
 * }
 * ```
 *
 * **Example.**
 * Adds a Maven Artifact Dependency Trigger for given atrifact with specified
 * [version range](https://www.jetbrains.com/help/teamcity/?Configuring+Maven+Triggers#Version+Ranges),
 * custom classifier, Maven repository and user settings.
 * This trigger will not trigger a build if currently running builds can produce this artifact.
 * ```
 * mavenArtifact {
 *     groupId = "org.example.group"
 *     artifactId = "example-atrifact"
 *     version = "[1.0,2.0)"
 *     classifier = "sources"
 *     repoUrl = "https://mypackages.org/maven/"
 *     repoId = "my-repo-id"
 *     skipIfRunning = true
 *     userSettingsSelection = "userSettingsSelection:byPath"
 *     userSettingsPath = "path/to/user/settings.xml"
 * }
 * ```
 *
 * **Example.**
 * Adds a simple Maven Artifact Dependency Trigger for given atrifact the latest snapshot version with
 * [Maven settings](https://www.jetbrains.com/help/teamcity/?Maven+Server-Side+Settings) provided on project level.
 * Enables [build customization](https://www.jetbrains.com/help/teamcity/?Configuring+Maven+Triggers#Triggered+Build+Customization) which
 * deletes all the files from the [checkout directory](https://www.jetbrains.com/help/teamcity/?Build+Checkout+Directory) for the triggered build and its dependencies and
 * also adds a new parameter to the triggered build with the dependency [parameter reference](https://www.jetbrains.com/help/teamcity/?Predefined+Build+Parameters#Dependency+Parameters).
 * ```
 * mavenArtifact {
 *     groupId = "org.example.group"
 *     artifactId = "example-atrifact"
 *     version = "1.0-SNAPSHOT"
 *     userSettingsSelection = "settings.xml"
 *
 *     enforceCleanCheckout = true
 *     enforceCleanCheckoutForDependencies = true
 *     buildParams {
 *         param("triggered.version", "dep.Project_BuildID.version")
 *     }
 * }
 * ```
 *
 *
 * @see mavenArtifact
 */
open class MavenArtifactDependencyTrigger() : Trigger() {

    init {
        type = "mavenArtifactDependencyTrigger"
    }

    constructor(init: MavenArtifactDependencyTrigger.() -> Unit): this() {
        init()
    }

    /**
     * A maven group identifier to the watched artifact belongs to
     */
    var groupId by stringParameter()

    /**
     * A watched maven artifact id
     */
    var artifactId by stringParameter()

    /**
     * Version or Version range
     */
    var version by stringParameter()

    /**
     * A type of the watched artifact. By default, the type is "jar".
     */
    var artifactType by stringParameter("type")

    /**
     * Optional classifier of the watched artifact.
     */
    var classifier by stringParameter()

    /**
     * Maven repository URL
     */
    var repoUrl by stringParameter()

    /**
     * Maven repository ID to match authentication from applied maven settings
     */
    var repoId by stringParameter()

    /**
     * Do not trigger a build if currently running builds can produce this artifact
     */
    var skipIfRunning by booleanParameter()

    /**
     * Use one of the predefined settings files or provide a custom path. By default, the standard Maven settings file location is used.
     */
    var userSettingsSelection by stringParameter()

    /**
     * The path to a user settings file
     */
    var userSettingsPath by stringParameter()

    override fun validate(consumer: ErrorConsumer) {
        super.validate(consumer)
        if (groupId == null && !hasParam("groupId")) {
            consumer.consumePropertyError("groupId", "mandatory 'groupId' property is not specified")
        }
        if (artifactId == null && !hasParam("artifactId")) {
            consumer.consumePropertyError("artifactId", "mandatory 'artifactId' property is not specified")
        }
        if (version == null && !hasParam("version")) {
            consumer.consumePropertyError("version", "mandatory 'version' property is not specified")
        }
    }
}


/**
 * Adds a [trigger](https://www.jetbrains.com/help/teamcity/?Configuring+Maven+Triggers#ConfiguringMavenTriggers-MavenArtifactDependencyTrigger).
 * running builds when there is a modification of the maven dependency content.
 *
 * **Example.**
 * Adds a simple Maven Artifact Dependency Trigger for given atrifact the latest snapshot version with default Maven settings.
 * ```
 * mavenArtifact {
 *     groupId = "org.example.group"
 *     artifactId = "example-atrifact"
 *     version = "1.0-SNAPSHOT"
 *     userSettingsSelection = "userSettingsSelection:default"
 * }
 * ```
 *
 * **Example.**
 * Adds a Maven Artifact Dependency Trigger for given atrifact with specified
 * [version range](https://www.jetbrains.com/help/teamcity/?Configuring+Maven+Triggers#Version+Ranges),
 * custom classifier, Maven repository and user settings.
 * This trigger will not trigger a build if currently running builds can produce this artifact.
 * ```
 * mavenArtifact {
 *     groupId = "org.example.group"
 *     artifactId = "example-atrifact"
 *     version = "[1.0,2.0)"
 *     classifier = "sources"
 *     repoUrl = "https://mypackages.org/maven/"
 *     repoId = "my-repo-id"
 *     skipIfRunning = true
 *     userSettingsSelection = "userSettingsSelection:byPath"
 *     userSettingsPath = "path/to/user/settings.xml"
 * }
 * ```
 *
 * **Example.**
 * Adds a simple Maven Artifact Dependency Trigger for given atrifact the latest snapshot version with
 * [Maven settings](https://www.jetbrains.com/help/teamcity/?Maven+Server-Side+Settings) provided on project level.
 * Enables [build customization](https://www.jetbrains.com/help/teamcity/?Configuring+Maven+Triggers#Triggered+Build+Customization) which
 * deletes all the files from the [checkout directory](https://www.jetbrains.com/help/teamcity/?Build+Checkout+Directory) for the triggered build and its dependencies and
 * also adds a new parameter to the triggered build with the dependency [parameter reference](https://www.jetbrains.com/help/teamcity/?Predefined+Build+Parameters#Dependency+Parameters).
 * ```
 * mavenArtifact {
 *     groupId = "org.example.group"
 *     artifactId = "example-atrifact"
 *     version = "1.0-SNAPSHOT"
 *     userSettingsSelection = "settings.xml"
 *
 *     enforceCleanCheckout = true
 *     enforceCleanCheckoutForDependencies = true
 *     buildParams {
 *         param("triggered.version", "dep.Project_BuildID.version")
 *     }
 * }
 * ```
 *
 *
 * @see MavenArtifactDependencyTrigger
 */
fun Triggers.mavenArtifact(init: MavenArtifactDependencyTrigger.() -> Unit): MavenArtifactDependencyTrigger {
    val result = MavenArtifactDependencyTrigger(init)
    trigger(result)
    return result
}
