package jetbrains.buildServer.configs.kotlin.v2017_2.projectFeatures

import jetbrains.buildServer.configs.kotlin.v2017_2.*

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
open class GitLabEEConnection() : ProjectFeature() {

    init {
        type = "OAuthProvider"
        param("providerType", "GitLabCEorEE")
    }

    constructor(init: GitLabEEConnection.() -> Unit): this() {
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

    override fun validate(consumer: ErrorConsumer) {
        super.validate(consumer)
        if (displayName == null && !hasParam("displayName")) {
            consumer.consumePropertyError("displayName", "mandatory 'displayName' property is not specified")
        }
        if (serverUrl == null && !hasParam("gitLabUrl")) {
            consumer.consumePropertyError("serverUrl", "mandatory 'serverUrl' property is not specified")
        }
        if (applicationId == null && !hasParam("clientId")) {
            consumer.consumePropertyError("applicationId", "mandatory 'applicationId' property is not specified")
        }
        if (clientSecret == null && !hasParam("secure:clientSecret")) {
            consumer.consumePropertyError("clientSecret", "mandatory 'clientSecret' property is not specified")
        }
    }
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
fun ProjectFeatures.gitlabEEConnection(init: GitLabEEConnection.() -> Unit): GitLabEEConnection {
    val result = GitLabEEConnection(init)
    feature(result)
    return result
}
