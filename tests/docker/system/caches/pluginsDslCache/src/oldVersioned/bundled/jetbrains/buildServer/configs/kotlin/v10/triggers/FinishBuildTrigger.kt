package jetbrains.buildServer.configs.kotlin.v10.triggers

import jetbrains.buildServer.configs.kotlin.v10.*

/**
 * Base class for [Finish Build Triggers](https://www.jetbrains.com/help/teamcity/?Configuring+Finish+Build+Trigger).
 * Finish build trigger runs build when some other build finishes.
 *
 * **Example.**
 * Trigger a build if a new finished build or builds appear in the history of the build configuration with the specified id and with the branch starting with 'release/' prefix.
 * ```
 * finishBuildTrigger {
 *   buildType = "<id of a build configuration>"
 *   branchFilter = "+:release/*"
 * }
 * ```
 * <pre style='display:none'>*/</pre>
 *
 *
 * @see finishBuildTrigger
 */
open class FinishBuildTrigger : Trigger {
    constructor(init: FinishBuildTrigger.() -> Unit = {}, base: FinishBuildTrigger? = null): super(base = base as Trigger?) {
        type = "buildDependencyTrigger"
        init()
    }

    /**
     * Use buildType instead
     */
    @Deprecated("", replaceWith = ReplaceWith("buildType"))
    var buildTypeExtId by stringParameter("dependsOn")

    /**
     * Id of the build configuration to watch. A new build will be triggered when a build
     * in the watched build configuration is finished.
     */
    var buildType by stringParameter("dependsOn")

    /**
     * Whether the build should be triggered only after a successful build in the watched build configuration
     */
    var successfulOnly by booleanParameter("afterSuccessfulBuildOnly", trueValue = "true", falseValue = "")

    /**
     * [Branch filter](https://www.jetbrains.com/help/teamcity/?Configuring+Finish+Build+Trigger#ConfiguringFinishBuildTrigger-branchFilter)
     * allows to limit the branches where finished builds are watched.
     */
    var branchFilter by stringParameter()

}


/**
 * Adds [Finish Build Trigger](https://www.jetbrains.com/help/teamcity/?Configuring+Finish+Build+Trigger) to build configuration or template
 *
 * **Example.**
 * Trigger a build if a new finished build or builds appear in the history of the build configuration with the specified id and with the branch starting with 'release/' prefix.
 * ```
 * finishBuildTrigger {
 *   buildType = "<id of a build configuration>"
 *   branchFilter = "+:release/*"
 * }
 * ```
 * <pre style='display:none'>*/</pre>
 *
 *
 * @see FinishBuildTrigger
 */
fun Triggers.finishBuildTrigger(base: FinishBuildTrigger? = null, init: FinishBuildTrigger.() -> Unit = {}) {
    trigger(FinishBuildTrigger(init, base))
}
