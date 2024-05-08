package jetbrains.buildServer.configs.kotlin.v10.projectFeatures

import jetbrains.buildServer.configs.kotlin.v10.*

/**
 * Project feature defining a single level connection with a GitHub App.
 * Such a connection encompasses both application and installation level settings.
 *
 * **Example.**
 * It is not recommended to store secure values such as the secret directly in the DSL code,
 * see [Managing Tokens](https://www.jetbrains.com/help/teamcity/storing-project-settings-in-version-control.html#Managing+Tokens)
 * section of our documentation.
 * ```
 * githubAppConnection {
 *   id = "<Connection ID>" // arbitrary ID that can be later used to refer to the connection
 *   displayName = "<Connection display name>"
 *   appId = "<GitHub App ID>"
 *   clientId = "<GitHub App Client ID>"
 *   clientSecret = "credentialsJSON:*****"
 *   privateKey = "credentialsJSON:*****"
 *   ownerUrl = "https://github.com/owner"
 *   webhookSecret = "credentialsJSON:*****" //optional
 * }
 * ```
 *
 *
 * @see githubAppConnection
 */
open class GitHubAppConnection : ProjectFeature {
    constructor(init: GitHubAppConnection.() -> Unit = {}, base: GitHubAppConnection? = null): super(base = base as ProjectFeature?) {
        type = "OAuthProvider"
        param("providerType", "GitHubApp")
        param("connectionSubtype", "gitHubApp")
        init()
    }

    /**
     * Human friendly connection name
     */
    var displayName by stringParameter()

    /**
     * GitHub App application ID
     */
    var appId by stringParameter("gitHubApp.appId")

    /**
     * GitHub App client ID
     */
    var clientId by stringParameter("gitHubApp.clientId")

    /**
     * GitHub App client secret
     */
    var clientSecret by stringParameter("secure:gitHubApp.clientSecret")

    /**
     * GitHub App private key
     */
    var privateKey by stringParameter("secure:gitHubApp.privateKey")

    /**
     * GitHub App webhook secret
     */
    var webhookSecret by stringParameter("secure:gitHubApp.webhookSecret")

    /**
     * GitHub App installation owner URL (e.g. URL to a GitHub user or organization)
     */
    var ownerUrl by stringParameter("gitHubApp.ownerUrl")

}


/**
 * Creates a GitHub App single level connection in the current project
 *
 * **Example.**
 * It is not recommended to store secure values such as the secret directly in the DSL code,
 * see [Managing Tokens](https://www.jetbrains.com/help/teamcity/storing-project-settings-in-version-control.html#Managing+Tokens)
 * section of our documentation.
 * ```
 * githubAppConnection {
 *   id = "<Connection ID>" // arbitrary ID that can be later used to refer to the connection
 *   displayName = "<Connection display name>"
 *   appId = "<GitHub App ID>"
 *   clientId = "<GitHub App Client ID>"
 *   clientSecret = "credentialsJSON:*****"
 *   privateKey = "credentialsJSON:*****"
 *   ownerUrl = "https://github.com/owner"
 *   webhookSecret = "credentialsJSON:*****" //optional
 * }
 * ```
 *
 *
 * @see GitHubAppConnection
 */
fun ProjectFeatures.githubAppConnection(base: GitHubAppConnection? = null, init: GitHubAppConnection.() -> Unit = {}) {
    feature(GitHubAppConnection(init, base))
}
