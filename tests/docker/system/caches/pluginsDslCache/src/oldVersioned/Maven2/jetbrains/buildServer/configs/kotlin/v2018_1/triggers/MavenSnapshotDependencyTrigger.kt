package jetbrains.buildServer.configs.kotlin.v2018_1.triggers

import jetbrains.buildServer.configs.kotlin.v2018_1.*

/**
 * A [trigger](https://www.jetbrains.com/help/teamcity/?Configuring+Maven+Triggers#ConfiguringMavenTriggers-MavenSnapshotDependencyTrigger)
 * running builds when there is a modification of the snapshot dependency content in the remote repository.
 *
 * **Example.**
 * Adds a simple Maven Sanpshot Dependency trigger.
 * ```
 * mavenSnapshot { }
 * ```
 *
 * **Example.**
 * Adds a Maven Sanpshot Dependency trigger which will not trigger builds if currently running builds can produce snapshot dependencies.
 * Enables [build customization](https://www.jetbrains.com/help/teamcity/?Configuring+Maven+Triggers#Triggered+Build+Customization) which
 * deletes all the files from the [checkout directory](https://www.jetbrains.com/help/teamcity/?Build+Checkout+Directory) for the triggered build and its dependencies and
 * also adds a new parameter to the triggered build with the dependency [parameter reference](https://www.jetbrains.com/help/teamcity/?Predefined+Build+Parameters#Dependency+Parameters).
 * ```
 * mavenSnapshot {
 *     skipIfRunning = true
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
 * @see mavenSnapshot
 */
open class MavenSnapshotDependencyTrigger() : Trigger() {

    init {
        type = "mavenSnapshotDependencyTrigger"
    }

    constructor(init: MavenSnapshotDependencyTrigger.() -> Unit): this() {
        init()
    }

    /**
     * Do not trigger a build if currently running builds can produce this artifact
     */
    var skipIfRunning by booleanParameter()

    override fun validate(consumer: ErrorConsumer) {
        super.validate(consumer)
    }
}


/**
 * Adds a [trigger](https://www.jetbrains.com/help/teamcity/?Configuring+Maven+Triggers#ConfiguringMavenTriggers-MavenSnapshotDependencyTrigger)
 * running builds when there is a modification of the snapshot dependency content in the remote repository.
 *
 * **Example.**
 * Adds a simple Maven Sanpshot Dependency trigger.
 * ```
 * mavenSnapshot { }
 * ```
 *
 * **Example.**
 * Adds a Maven Sanpshot Dependency trigger which will not trigger builds if currently running builds can produce snapshot dependencies.
 * Enables [build customization](https://www.jetbrains.com/help/teamcity/?Configuring+Maven+Triggers#Triggered+Build+Customization) which
 * deletes all the files from the [checkout directory](https://www.jetbrains.com/help/teamcity/?Build+Checkout+Directory) for the triggered build and its dependencies and
 * also adds a new parameter to the triggered build with the dependency [parameter reference](https://www.jetbrains.com/help/teamcity/?Predefined+Build+Parameters#Dependency+Parameters).
 * ```
 * mavenSnapshot {
 *     skipIfRunning = true
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
 * @see MavenSnapshotDependencyTrigger
 */
fun Triggers.mavenSnapshot(init: MavenSnapshotDependencyTrigger.() -> Unit): MavenSnapshotDependencyTrigger {
    val result = MavenSnapshotDependencyTrigger(init)
    trigger(result)
    return result
}
