package jetbrains.buildServer.configs.kotlin.v10.projectFeatures

import jetbrains.buildServer.configs.kotlin.v10.*

/**
 * Project feature defining an OAuth connection settings for Slack
 *
 * **Example.**
 * Creates a Slack connection
 * ```
 * slackConnection {
 *     id = "PROJECT_EXT_4"
 *     displayName = "Connection to Slack"
 *     botToken = "credentialsJSON:321-321"
 *     clientId = "Slack client id"
 *     clientSecret = "credentialsJSON:123-123"
 *     serviceMessageMaxNotificationsPerBuild = 0
 *     serviceMessageAllowedDomainNames = "jetbrains.com,*.example.com"
 * }
 * ```
 *
 *
 * @see slackConnection
 */
open class SlackConnection : ProjectFeature {
    constructor(init: SlackConnection.() -> Unit = {}, base: SlackConnection? = null): super(base = base as ProjectFeature?) {
        type = "OAuthProvider"
        param("providerType", "slackConnection")
        init()
    }

    /**
     * Human friendly connection name
     */
    var displayName by stringParameter()

    /**
     * Bot token, xoxb-***
     */
    var botToken by stringParameter("secure:token")

    /**
     * Client ID
     */
    var clientId by stringParameter()

    /**
     * Client secret
     */
    var clientSecret by stringParameter("secure:clientSecret")

    /**
     * Max number of service message notifications per build
     */
    var serviceMessageMaxNotificationsPerBuild by stringParameter()

    /**
     * Whitelist of domain names which may be included in service message notification
     */
    var serviceMessageAllowedDomainNames by stringParameter()

}


/**
 * Creates a Slack connection in the current project
 *
 * **Example.**
 * Creates a Slack connection
 * ```
 * slackConnection {
 *     id = "PROJECT_EXT_4"
 *     displayName = "Connection to Slack"
 *     botToken = "credentialsJSON:321-321"
 *     clientId = "Slack client id"
 *     clientSecret = "credentialsJSON:123-123"
 *     serviceMessageMaxNotificationsPerBuild = 0
 *     serviceMessageAllowedDomainNames = "jetbrains.com,*.example.com"
 * }
 * ```
 *
 *
 * @see SlackConnection
 */
fun ProjectFeatures.slackConnection(base: SlackConnection? = null, init: SlackConnection.() -> Unit = {}) {
    feature(SlackConnection(init, base))
}
