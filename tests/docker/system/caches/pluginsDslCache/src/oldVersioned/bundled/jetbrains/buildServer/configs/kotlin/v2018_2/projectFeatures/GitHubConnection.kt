package jetbrains.buildServer.configs.kotlin.v2018_2.projectFeatures

import jetbrains.buildServer.configs.kotlin.v2018_2.*

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
open class GitHubConnection() : ProjectFeature() {

    init {
        type = "OAuthProvider"
        param("providerType", "GitHub")
        param("defaultTokenScope", "public_repo,repo,repo:status,write:repo_hook")
        param("gitHubUrl", "https://github.com/")
    }

    constructor(init: GitHubConnection.() -> Unit): this() {
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

    override fun validate(consumer: ErrorConsumer) {
        super.validate(consumer)
        if (displayName == null && !hasParam("displayName")) {
            consumer.consumePropertyError("displayName", "mandatory 'displayName' property is not specified")
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
fun ProjectFeatures.githubConnection(init: GitHubConnection.() -> Unit): GitHubConnection {
    val result = GitHubConnection(init)
    feature(result)
    return result
}
