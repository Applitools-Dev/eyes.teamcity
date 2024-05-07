package jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures

import jetbrains.buildServer.configs.kotlin.v2019_2.*

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
open class GitHubAppConnection() : ProjectFeature() {

    init {
        type = "OAuthProvider"
        param("providerType", "GitHubApp")
        param("connectionSubtype", "gitHubApp")
    }

    constructor(init: GitHubAppConnection.() -> Unit): this() {
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

    override fun validate(consumer: ErrorConsumer) {
        super.validate(consumer)
        if (displayName == null && !hasParam("displayName")) {
            consumer.consumePropertyError("displayName", "mandatory 'displayName' property is not specified")
        }
        if (appId == null && !hasParam("gitHubApp.appId")) {
            consumer.consumePropertyError("appId", "mandatory 'appId' property is not specified")
        }
        if (clientId == null && !hasParam("gitHubApp.clientId")) {
            consumer.consumePropertyError("clientId", "mandatory 'clientId' property is not specified")
        }
        if (clientSecret == null && !hasParam("secure:gitHubApp.clientSecret")) {
            consumer.consumePropertyError("clientSecret", "mandatory 'clientSecret' property is not specified")
        }
        if (privateKey == null && !hasParam("secure:gitHubApp.privateKey")) {
            consumer.consumePropertyError("privateKey", "mandatory 'privateKey' property is not specified")
        }
        if (ownerUrl == null && !hasParam("gitHubApp.ownerUrl")) {
            consumer.consumePropertyError("ownerUrl", "mandatory 'ownerUrl' property is not specified")
        }
    }
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
fun ProjectFeatures.githubAppConnection(init: GitHubAppConnection.() -> Unit): GitHubAppConnection {
    val result = GitHubAppConnection(init)
    feature(result)
    return result
}
