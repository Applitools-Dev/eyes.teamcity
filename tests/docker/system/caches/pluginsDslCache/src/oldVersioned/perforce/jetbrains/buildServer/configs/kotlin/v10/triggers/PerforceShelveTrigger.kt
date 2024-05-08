package jetbrains.buildServer.configs.kotlin.v10.triggers

import jetbrains.buildServer.configs.kotlin.v10.*

/**
 * Perforce Shelve Trigger queues a build when a shelved changelist is created/updated in
 * the VCS Root of the build configuration.
 *
 * **Example.**
 * Configure a Perforce Shelve Trigger which runs a personal build when a new or modified shelved changelist is detected.
 * The shelved changelist must have #review keyword in its description.
 * The build will enforce clean checkout, and will have an extra `inShelve=true` configuration parameter.
 * ```
 * object Docker : BuildType({
 *     name = "Test build for shelves"
 *     ...
 *     triggers {
 *         perforceShelveTrigger {
 *             keyword = "#review"
 *
 *             enforceCleanCheckout = true
 *             buildParams {
 *                 param("inShelve", "true")
 *             }
 *         }
 *     }
 * })
 * ```
 *
 *
 * @see perforceShelveTrigger
 */
open class PerforceShelveTrigger : Trigger {
    constructor(init: PerforceShelveTrigger.() -> Unit = {}, base: PerforceShelveTrigger? = null): super(base = base as Trigger?) {
        type = "perforceShelveTrigger"
        init()
    }

    /**
     * The keyword which should be present in Perforce shelved changelist description to trigger the build.
     * Default keyword is `#teamcity`
     */
    var keyword by stringParameter("clDescriptionKeyword")

}


/**
 * Adds [Perforce Build Trigger](https://www.jetbrains.com/help/teamcity/?Perforce+Shelve+Trigger) to build configuration or template
 *
 * **Example.**
 * Configure a Perforce Shelve Trigger which runs a personal build when a new or modified shelved changelist is detected.
 * The shelved changelist must have #review keyword in its description.
 * The build will enforce clean checkout, and will have an extra `inShelve=true` configuration parameter.
 * ```
 * object Docker : BuildType({
 *     name = "Test build for shelves"
 *     ...
 *     triggers {
 *         perforceShelveTrigger {
 *             keyword = "#review"
 *
 *             enforceCleanCheckout = true
 *             buildParams {
 *                 param("inShelve", "true")
 *             }
 *         }
 *     }
 * })
 * ```
 *
 *
 * @see PerforceShelveTrigger
 */
fun Triggers.perforceShelveTrigger(base: PerforceShelveTrigger? = null, init: PerforceShelveTrigger.() -> Unit = {}) {
    trigger(PerforceShelveTrigger(init, base))
}
