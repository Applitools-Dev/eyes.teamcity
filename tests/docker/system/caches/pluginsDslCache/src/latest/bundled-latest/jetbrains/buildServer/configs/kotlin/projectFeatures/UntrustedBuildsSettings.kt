package jetbrains.buildServer.configs.kotlin.projectFeatures

import jetbrains.buildServer.configs.kotlin.*

/**
 * Project feature for Untrusted Builds Settings
 *
 * **Example.**
 * Untrusted Settings that require approval for all untrusted builds in the project. The builds will be canceled in 60 minutes if not approved
 * ```
 * untrustedBuildsSettings {
 *     id = "<Connection id>" // arbitrary ID that can be later used to refer to the project feature
 *     defaultAction = UntrustedBuildsSettings.DefaultAction.APPROVE
 *     approvalRules = "user:teamcity_user"
 *     enableLog = false
 *     timeoutMinutes = 60
 * }
 * ```
 *
 * **Example.**
 * Untrusted Settings that force all detected untrusted builds to be canceled and enable logging of untrusted builds
 * ```
 * untrustedBuildsSettings {
 *     id = "<Connection id>" // arbitrary ID that can be later used to refer to the project feature
 *     defaultAction = UntrustedBuildsSettings.DefaultAction.CANCEL
 *     enableLog = true
 * }
 * ```
 *
 *
 * @see untrustedBuildsSettings
 */
open class UntrustedBuildsSettings() : ProjectFeature() {

    init {
        type = "UntrustedBuildsSettings"
    }

    constructor(init: UntrustedBuildsSettings.() -> Unit): this() {
        init()
    }

    /**
     * If APPROVE action is choosen, then approvalRules field should be specified
     *
     *
     * Default action for untrusted builds found in queue. @see DefaultAction
     */
    var defaultAction by enumParameter<DefaultAction>(mapping = DefaultAction.mapping)

    /**
     * Indicates that logging of untrusted builds in the server log and build log should be enabled
     */
    var enableLog by booleanParameter()

    /**
     * Approval rules for untrusted builds. Should be specified only if APPROVE is set as a defaultAction
     */
    var approvalRules by stringParameter("rules")

    /**
     * Time (in minutes) before the build is cancelled, defaults to 360 minutes. Should be specified only if APPROVE is set as a defaultAction
     */
    var timeoutMinutes by intParameter("timeout")

    /**
     * If started by user with sufficient permissions, mark build as approved by user
     */
    var manualRunsApproved by booleanParameter("manualStartIsApproval")

    enum class DefaultAction {
        /**
         * Nothing should be done with untrusted builds
         */
        IGNORE,
        /**
         * All untrusted builds should be canceled automatically
         */
        CANCEL,
        /**
         * All untrusted builds should be approved by the users specified in approval rules
         */
        APPROVE;

        companion object {
            val mapping = mapOf<DefaultAction, String>(IGNORE to "ignore", CANCEL to "cancel", APPROVE to "approve")
        }

    }
    override fun validate(consumer: ErrorConsumer) {
        super.validate(consumer)
        if (defaultAction == null && !hasParam("defaultAction")) {
            consumer.consumePropertyError("defaultAction", "mandatory 'defaultAction' property is not specified")
        }
        if (enableLog == null && !hasParam("enableLog")) {
            consumer.consumePropertyError("enableLog", "mandatory 'enableLog' property is not specified")
        }
    }
}


/**
 * Sets Untrusted Builds Settings for the current project
 *
 * **Example.**
 * Untrusted Settings that require approval for all untrusted builds in the project. The builds will be canceled in 60 minutes if not approved
 * ```
 * untrustedBuildsSettings {
 *     id = "<Connection id>" // arbitrary ID that can be later used to refer to the project feature
 *     defaultAction = UntrustedBuildsSettings.DefaultAction.APPROVE
 *     approvalRules = "user:teamcity_user"
 *     enableLog = false
 *     timeoutMinutes = 60
 * }
 * ```
 *
 * **Example.**
 * Untrusted Settings that force all detected untrusted builds to be canceled and enable logging of untrusted builds
 * ```
 * untrustedBuildsSettings {
 *     id = "<Connection id>" // arbitrary ID that can be later used to refer to the project feature
 *     defaultAction = UntrustedBuildsSettings.DefaultAction.CANCEL
 *     enableLog = true
 * }
 * ```
 *
 *
 * @see UntrustedBuildsSettings
 */
fun ProjectFeatures.untrustedBuildsSettings(init: UntrustedBuildsSettings.() -> Unit): UntrustedBuildsSettings {
    val result = UntrustedBuildsSettings(init)
    feature(result)
    return result
}
