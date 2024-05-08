package jetbrains.buildServer.configs.kotlin.projectFeatures

import jetbrains.buildServer.configs.kotlin.*

/**
 * Project feature defining an OAuth connection settings for Bitbucket Cloud
 *
 * **Example.**
 * It is not recommended to store secure values such as the secret directly in the DSL code,
 * see [Managing Tokens](https://www.jetbrains.com/help/teamcity/storing-project-settings-in-version-control.html#Managing+Tokens)
 * section of our documentation.
 * ```
 * bitbucketCloudConnection {
 *   id = "<Connection id>" // arbitrary ID that can be later used to refer to the connection
 *   displayName = "<Connection display name>"
 *   key = "<OAuth2 consumer key>"
 *   clientSecret = "credentialsJSON:*****"
 * }
 * ```
 *
 *
 * @see bitbucketCloudConnection
 */
open class BitbucketCloudConnection() : ProjectFeature() {

    init {
        type = "OAuthProvider"
        param("providerType", "BitBucketCloud")
    }

    constructor(init: BitbucketCloudConnection.() -> Unit): this() {
        init()
    }

    /**
     * Human friendly connection name
     */
    var displayName by stringParameter()

    /**
     * Bitbucket OAuth connection key
     */
    var key by stringParameter("clientId")

    /**
     * Bitbucket OAuth connection client secret
     */
    var clientSecret by stringParameter("secure:clientSecret")

    override fun validate(consumer: ErrorConsumer) {
        super.validate(consumer)
        if (displayName == null && !hasParam("displayName")) {
            consumer.consumePropertyError("displayName", "mandatory 'displayName' property is not specified")
        }
        if (key == null && !hasParam("clientId")) {
            consumer.consumePropertyError("key", "mandatory 'key' property is not specified")
        }
        if (clientSecret == null && !hasParam("secure:clientSecret")) {
            consumer.consumePropertyError("clientSecret", "mandatory 'clientSecret' property is not specified")
        }
    }
}


/**
 * Creates a Bitbucket Cloud OAuth connection in the current project
 *
 * **Example.**
 * It is not recommended to store secure values such as the secret directly in the DSL code,
 * see [Managing Tokens](https://www.jetbrains.com/help/teamcity/storing-project-settings-in-version-control.html#Managing+Tokens)
 * section of our documentation.
 * ```
 * bitbucketCloudConnection {
 *   id = "<Connection id>" // arbitrary ID that can be later used to refer to the connection
 *   displayName = "<Connection display name>"
 *   key = "<OAuth2 consumer key>"
 *   clientSecret = "credentialsJSON:*****"
 * }
 * ```
 *
 *
 * @see BitbucketCloudConnection
 */
fun ProjectFeatures.bitbucketCloudConnection(init: BitbucketCloudConnection.() -> Unit): BitbucketCloudConnection {
    val result = BitbucketCloudConnection(init)
    feature(result)
    return result
}
