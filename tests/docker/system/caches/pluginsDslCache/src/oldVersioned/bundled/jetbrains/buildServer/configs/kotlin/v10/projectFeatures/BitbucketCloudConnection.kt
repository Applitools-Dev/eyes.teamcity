package jetbrains.buildServer.configs.kotlin.v10.projectFeatures

import jetbrains.buildServer.configs.kotlin.v10.*

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
open class BitbucketCloudConnection : ProjectFeature {
    constructor(init: BitbucketCloudConnection.() -> Unit = {}, base: BitbucketCloudConnection? = null): super(base = base as ProjectFeature?) {
        type = "OAuthProvider"
        param("providerType", "BitBucketCloud")
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
fun ProjectFeatures.bitbucketCloudConnection(base: BitbucketCloudConnection? = null, init: BitbucketCloudConnection.() -> Unit = {}) {
    feature(BitbucketCloudConnection(init, base))
}
