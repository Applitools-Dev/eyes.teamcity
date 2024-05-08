package jetbrains.buildServer.configs.kotlin.v10.projectFeatures

import jetbrains.buildServer.configs.kotlin.v10.*

/**
 * Project feature defining an OAuth connection settings for GitLab CE/EE
 *
 * **Example.**
 * It is not recommended to store secure values such as the secret directly in the DSL code,
 * see [Managing Tokens](https://www.jetbrains.com/help/teamcity/storing-project-settings-in-version-control.html#Managing+Tokens)
 * section of our documentation.
 * ```
 * gitlabEEConnection {
 *   id = "<Connection id>" // arbitrary ID that can be later used to refer to the connection
 *   displayName = "<Connection display name>"
 *   serverUrl = "<GitLab CE/EE server URL>"
 *   applicationId = "<OAuth2 application ID>"
 *   clientSecret = "credentialsJSON:*****"
 * }
 * ```
 *
 *
 * @see gitlabEEConnection
 */
open class GitLabEEConnection : ProjectFeature {
    constructor(init: GitLabEEConnection.() -> Unit = {}, base: GitLabEEConnection? = null): super(base = base as ProjectFeature?) {
        type = "OAuthProvider"
        param("providerType", "GitLabCEorEE")
        init()
    }

    /**
     * Human friendly connection name
     */
    var displayName by stringParameter()

    /**
     * GitLab server URL
     */
    var serverUrl by stringParameter("gitLabUrl")

    /**
     * GitLab OAuth connection application ID
     */
    var applicationId by stringParameter("clientId")

    /**
     * GitLab OAuth connection client secret
     */
    var clientSecret by stringParameter("secure:clientSecret")

}


/**
 * Creates a GitLab CE/EE OAuth connection in the current project
 *
 * **Example.**
 * It is not recommended to store secure values such as the secret directly in the DSL code,
 * see [Managing Tokens](https://www.jetbrains.com/help/teamcity/storing-project-settings-in-version-control.html#Managing+Tokens)
 * section of our documentation.
 * ```
 * gitlabEEConnection {
 *   id = "<Connection id>" // arbitrary ID that can be later used to refer to the connection
 *   displayName = "<Connection display name>"
 *   serverUrl = "<GitLab CE/EE server URL>"
 *   applicationId = "<OAuth2 application ID>"
 *   clientSecret = "credentialsJSON:*****"
 * }
 * ```
 *
 *
 * @see GitLabConnection
 */
fun ProjectFeatures.gitlabEEConnection(base: GitLabEEConnection? = null, init: GitLabEEConnection.() -> Unit = {}) {
    feature(GitLabEEConnection(init, base))
}
