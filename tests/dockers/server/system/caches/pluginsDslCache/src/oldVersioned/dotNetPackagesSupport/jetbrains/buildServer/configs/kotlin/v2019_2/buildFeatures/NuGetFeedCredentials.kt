package jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures

import jetbrains.buildServer.configs.kotlin.v2019_2.*

/**
 * A [build feature](https://confluence.jetbrains.com/display/TCDL/NuGet+Feed+Credentials) to provide feed credentials.
 *
 * **Example.**
 * Defines credentials to use for a NuGet feed which requires authentication.
 * Note: instead of an actual password a [token](https://www.jetbrains.com/help/teamcity/storing-project-settings-in-version-control.html#Storing+Secure+Settings) should be provided.
 * ```
 * nuGetFeedCredentials {
 *   feedUrl = "https://api.nuget.org/v3/index.json"
 *   username = "publisher"
 *   password = "<a token representing a password>"
 * }
 * ```
 *
 *
 * @see nuGetFeedCredentials
 */
open class NuGetFeedCredentials() : BuildFeature() {

    init {
        type = "jb.nuget.auth"
    }

    constructor(init: NuGetFeedCredentials.() -> Unit): this() {
        init()
    }

    /**
     * Specify a feed URL which credentials will be used in the build.
     */
    var feedUrl by stringParameter("nuget.auth.feed")

    /**
     * Specify username for the feed.
     */
    var username by stringParameter("nuget.auth.username")

    /**
     * Specify password for the feed.
     */
    var password by stringParameter("secure:nuget.auth.password")

    override fun validate(consumer: ErrorConsumer) {
        super.validate(consumer)
    }
}


/**
 * Provides [NuGet feed credentials](https://confluence.jetbrains.com/display/TCDL/NuGet+Feed+Credentials) for feed
 *
 * **Example.**
 * Defines credentials to use for a NuGet feed which requires authentication.
 * Note: instead of an actual password a [token](https://www.jetbrains.com/help/teamcity/storing-project-settings-in-version-control.html#Storing+Secure+Settings) should be provided.
 * ```
 * nuGetFeedCredentials {
 *   feedUrl = "https://api.nuget.org/v3/index.json"
 *   username = "publisher"
 *   password = "<a token representing a password>"
 * }
 * ```
 *
 *
 * @see NuGetFeedCredentials
 */
fun BuildFeatures.nuGetFeedCredentials(init: NuGetFeedCredentials.() -> Unit): NuGetFeedCredentials {
    val result = NuGetFeedCredentials(init)
    feature(result)
    return result
}
