package jetbrains.buildServer.configs.kotlin.vcs

import jetbrains.buildServer.configs.kotlin.*

/**
 * TFS [VCS root](https://www.jetbrains.com/help/teamcity/?Team+Foundation+Server)
 *
 * **Example.**
 * Adds a [TFS VCS root](https://www.jetbrains.com/help/teamcity/?Team+Foundation+Server)
 * with the TeamCity server user account access to Azure DevOps.
 * ```
 * project {
 *     vcsRoot(TFSRoot)
 *     // ...
 * }
 *
 * object TFSRoot : TfsVcsRoot({
 *     name = "TFSRoot"
 *     url = "https://dev.azure.com/organization"
 *     root = "${'$'}/path"
 * })
 * ```
 *
 * **Example.**
 * Adds a [TFS VCS root](https://www.jetbrains.com/help/teamcity/?Team+Foundation+Server)
 * with specified [Azure DevOps credentials](https://www.jetbrains.com/help/teamcity/?Team+Foundation+Server#Authentication+in+Azure+DevOps)
 * ([token](https://www.jetbrains.com/help/teamcity/storing-project-settings-in-version-control.html#Managing+Tokens))
 * enforced overwriting all files in the workspace while
 * [Agent-Side Checkout](https://www.jetbrains.com/help/teamcity/?Team+Foundation+Server#Agent-Side+Checkout)
 * and custom [minimum polling interval](https://www.jetbrains.com/help/teamcity/?Configuring+VCS+Roots#Common+VCS+Root+Properties) in seconds.
 * ```
 * project {
 *     vcsRoot(TFSRoot)
 *     // ...
 * }
 *
 * object TFSRoot : TfsVcsRoot({
 *     name = "TFSRoot"
 *     pollInterval = 120
 *     url = "https://dev.azure.com/organization"
 *     root = "${'$'}/path"
 *     userName = "username"
 *     password = "credentialsJSON:******"
 *     forceOverwrite = true
 * })
 * ```
 */
open class TfsVcsRoot() : VcsRoot() {

    init {
        type = "tfs"
    }

    constructor(init: TfsVcsRoot.() -> Unit): this() {
        init()
    }

    /**
     * URL format:
     * * Azure DevOps Server: <public URL>/<collection>
     * * Azure DevOps Sevices: https://dev.azure.com/<organization> or https://<organization>.visualstudio.com
     * * TFS before 2018: http[s]://<host>[:<port>]/tfs/<collection>
     */
    var url by stringParameter("tfs-url")

    /**
     * TFS path to checkout. Format: $/path.
     */
    var root by stringParameter("tfs-root")

    /**
     * A username for TFS connection
     */
    var userName by stringParameter("tfs-username")

    /**
     * A password for TFS connection
     */
    var password by stringParameter("secure:tfs-password")

    /**
     * When set to true, TeamCity will call TFS to update workspace rewriting all files
     */
    var forceOverwrite by booleanParameter("tfs-force-get", trueValue = "true", falseValue = "")

    override fun validate(consumer: ErrorConsumer) {
        super.validate(consumer)
        if (url == null && !hasParam("tfs-url")) {
            consumer.consumePropertyError("url", "mandatory 'url' property is not specified")
        }
        if (root == null && !hasParam("tfs-root")) {
            consumer.consumePropertyError("root", "mandatory 'root' property is not specified")
        }
    }
}


