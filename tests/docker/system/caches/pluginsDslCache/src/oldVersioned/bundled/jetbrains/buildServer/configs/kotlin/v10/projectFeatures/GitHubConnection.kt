package jetbrains.buildServer.configs.kotlin.v10.projectFeatures

import jetbrains.buildServer.configs.kotlin.v10.*

/**
 * Project feature defining an OAuth connection settings for GitHub.com
 *
 * **Example.**
 * It is not recommended to store secure values such as the secret directly in the DSL code,
 * see [Managing Tokens](https://www.jetbrains.com/help/teamcity/storing-project-settings-in-version-control.html#Managing+Tokens)
 * section of our documentation.
 * ```
 * githubConnection {
 *   id = "<Connection id>" // arbitrary ID that can be later used to refer to the connection
 *   displayName = "<Connection display name>"
 *   clientId = "<OAuth2 application client ID>"
 *   clientSecret = "credentialsJSON:*****"
 * }
 * ```
 *
 *
 * @see githubConnection
 */
open class GitHubConnection : ProjectFeature {
    constructor(init: GitHubConnection.() -> Unit = {}, base: GitHubConnection? = null): super(base = base as ProjectFeature?) {
        type = "OAuthProvider"
        param("providerType", "GitHub")
        param("defaultTokenScope", "public_repo,repo,repo:status,write:repo_hook")
        param("gitHubUrl", "https://github.com/")
        init()
    }

    /**
     * Human friendly connection name
     */
    var displayName by stringParameter()

    /**
     * OAuth connection client id
     */
    var clientId by stringParameter()

    /**
     * OAuth connection client secret
     */
    var clientSecret by stringParameter("secure:clientSecret")

}


/**
 * Creates a GitHub.com OAuth connection in the current project
 *
 * **Example.**
 * It is not recommended to store secure values such as the secret directly in the DSL code,
 * see [Managing Tokens](https://www.jetbrains.com/help/teamcity/storing-project-settings-in-version-control.html#Managing+Tokens)
 * section of our documentation.
 * ```
 * githubConnection {
 *   id = "<Connection id>" // arbitrary ID that can be later used to refer to the connection
 *   displayName = "<Connection display name>"
 *   clientId = "<OAuth2 application client ID>"
 *   clientSecret = "credentialsJSON:*****"
 * }
 * ```
 *
 *
 * @see GitHubConnection
 */
fun ProjectFeatures.githubConnection(base: GitHubConnection? = null, init: GitHubConnection.() -> Unit = {}) {
    feature(GitHubConnection(init, base))
}
