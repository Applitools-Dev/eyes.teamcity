package jetbrains.buildServer.configs.kotlin.v2018_1.projectFeatures

import jetbrains.buildServer.configs.kotlin.v2018_1.*

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
open class SlackConnection() : ProjectFeature() {

    init {
        type = "OAuthProvider"
        param("providerType", "slackConnection")
    }

    constructor(init: SlackConnection.() -> Unit): this() {
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

    override fun validate(consumer: ErrorConsumer) {
        super.validate(consumer)
        if (displayName == null && !hasParam("displayName")) {
            consumer.consumePropertyError("displayName", "mandatory 'displayName' property is not specified")
        }
        if (botToken == null && !hasParam("secure:token")) {
            consumer.consumePropertyError("botToken", "mandatory 'botToken' property is not specified")
        }
        if (clientId == null && !hasParam("clientId")) {
            consumer.consumePropertyError("clientId", "mandatory 'clientId' property is not specified")
        }
        if (clientSecret == null && !hasParam("secure:clientSecret")) {
            consumer.consumePropertyError("clientSecret", "mandatory 'clientSecret' property is not specified")
        }
    }
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
fun ProjectFeatures.slackConnection(init: SlackConnection.() -> Unit): SlackConnection {
    val result = SlackConnection(init)
    feature(result)
    return result
}
