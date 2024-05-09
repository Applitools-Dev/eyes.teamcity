package jetbrains.buildServer.configs.kotlin.v2018_1.projectFeatures

import jetbrains.buildServer.configs.kotlin.v2018_1.*

/**
 * [Project feature](https://www.jetbrains.com/help/teamcity/?Configuring+Connections#Google)
 * defining an OAuth connection settings for Google.
 *
 * **Example.**
 * Adds a [Google OAuth connection](https://www.jetbrains.com/help/teamcity/?Configuring+Connections#Google)
 * with specified [credentials](https://www.jetbrains.com/help/teamcity/storing-project-settings-in-version-control.html#Managing+Tokens).
 * ```
 * googleConnection {
 *     id = "GoogleConnection_1"
 *     displayName = "Google Connection"
 *     clientId = "<clientID>"
 *     clientSecret = "credentialsJSON:******"
 * }
 * ```
 *
 *
 * @see googleConnection
 */
open class GoogleConnection() : ProjectFeature() {

    init {
        type = "OAuthProvider"
        param("providerType", "Google")
    }

    constructor(init: GoogleConnection.() -> Unit): this() {
        init()
    }

    /**
     * Human friendly connection name
     */
    var displayName by stringParameter()

    /**
     * OAuth connection client id
     */
    var clientId by stringParameter("googleClientId")

    /**
     * OAuth connection client secret
     */
    var clientSecret by stringParameter("secure:googleClientSecret")

    override fun validate(consumer: ErrorConsumer) {
        super.validate(consumer)
        if (displayName == null && !hasParam("displayName")) {
            consumer.consumePropertyError("displayName", "mandatory 'displayName' property is not specified")
        }
        if (clientId == null && !hasParam("googleClientId")) {
            consumer.consumePropertyError("clientId", "mandatory 'clientId' property is not specified")
        }
        if (clientSecret == null && !hasParam("secure:googleClientSecret")) {
            consumer.consumePropertyError("clientSecret", "mandatory 'clientSecret' property is not specified")
        }
    }
}


/**
 * Creates a [Google OAuth connection](https://www.jetbrains.com/help/teamcity/?Configuring+Connections#Google) in the current project
 *
 * **Example.**
 * Adds a [Google OAuth connection](https://www.jetbrains.com/help/teamcity/?Configuring+Connections#Google)
 * with specified [credentials](https://www.jetbrains.com/help/teamcity/storing-project-settings-in-version-control.html#Managing+Tokens).
 * ```
 * googleConnection {
 *     id = "GoogleConnection_1"
 *     displayName = "Google Connection"
 *     clientId = "<clientID>"
 *     clientSecret = "credentialsJSON:******"
 * }
 * ```
 *
 *
 * @see GoogleConnection
 */
fun ProjectFeatures.googleConnection(init: GoogleConnection.() -> Unit): GoogleConnection {
    val result = GoogleConnection(init)
    feature(result)
    return result
}
