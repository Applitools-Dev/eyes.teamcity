package jetbrains.buildServer.configs.kotlin.v10.projectFeatures

import jetbrains.buildServer.configs.kotlin.v10.*

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
open class GoogleConnection : ProjectFeature {
    constructor(init: GoogleConnection.() -> Unit = {}, base: GoogleConnection? = null): super(base = base as ProjectFeature?) {
        type = "OAuthProvider"
        param("providerType", "Google")
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
fun ProjectFeatures.googleConnection(base: GoogleConnection? = null, init: GoogleConnection.() -> Unit = {}) {
    feature(GoogleConnection(init, base))
}
