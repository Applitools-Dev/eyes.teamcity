package jetbrains.buildServer.configs.kotlin.v2017_2.projectFeatures

import jetbrains.buildServer.configs.kotlin.v2017_2.*

/**
 * Project feature enabling integration with [YouTrack](https://www.jetbrains.com/help/teamcity/?YouTrack) issue tracker
 *
 * **Example.**
 * It is not recommended to store secure values such as access tokens directly in the DSL code,
 * see [Managing Tokens](https://www.jetbrains.com/help/teamcity/storing-project-settings-in-version-control.html#Managing+Tokens)
 * section of our documentation.
 * ```
 * youtrack {
 *   id = "<Connection ID>" // arbitrary ID, must be unique in the project
 *   displayName = "<Connection display name>"
 *   host = "<YouTrack URL>"
 *   projectExtIds = "<Project ids>" // Space separated list of project ids
 *   accessToken = "credentialsJSON:*****"
 * }
 * ```
 *
 *
 * @see youtrack
 */
open class YouTrackIssueTracker() : ProjectFeature() {

    init {
        type = "IssueTracker"
        param("type", "youtrack")
        param("username", "")
        param("secure:password", "")
        param("secure:accessToken", "")
    }

    constructor(init: YouTrackIssueTracker.() -> Unit): this() {
        init()
    }

    /**
     * Issue tracker integration display name
     */
    var displayName by stringParameter("name")

    /**
     * YouTrack server URL
     */
    var host by stringParameter()

    /**
     * A username for YouTrack connection
     */
    var userName by stringParameter("username")

    /**
     * A password for YouTrack connection
     */
    var password by stringParameter("secure:password")

    /**
     * A space-separated list of YouTrack Project IDs
     */
    var projectExtIds by stringParameter("idPrefix")

    /**
     * Permanent token to access YouTrack
     */
    var accessToken by stringParameter("secure:accessToken")

    /**
     * Automatically populate YouTrack Project IDs
     */
    var useAutomaticIds by booleanParameter("autoSync", trueValue = "true", falseValue = "")

    /**
     * Authentication type for YouTrack.
     * Token authentication is used by default.
     */
    var authType by enumParameter<AuthType>(mapping = AuthType.mapping)

    enum class AuthType {
        TOKEN,
        USERNAME_AND_PASSWORD;

        companion object {
            val mapping = mapOf<AuthType, String>(TOKEN to "accesstoken", USERNAME_AND_PASSWORD to "loginpassword")
        }

    }
    override fun validate(consumer: ErrorConsumer) {
        super.validate(consumer)
        if (displayName == null && !hasParam("name")) {
            consumer.consumePropertyError("displayName", "mandatory 'displayName' property is not specified")
        }
        if (host == null && !hasParam("host")) {
            consumer.consumePropertyError("host", "mandatory 'host' property is not specified")
        }
        if (projectExtIds == null && !hasParam("idPrefix")) {
            consumer.consumePropertyError("projectExtIds", "mandatory 'projectExtIds' property is not specified")
        }
    }
}


/**
 * Enables integration with [YouTrack](https://www.jetbrains.com/help/teamcity/?YouTrack) issue tracker
 *
 * **Example.**
 * It is not recommended to store secure values such as access tokens directly in the DSL code,
 * see [Managing Tokens](https://www.jetbrains.com/help/teamcity/storing-project-settings-in-version-control.html#Managing+Tokens)
 * section of our documentation.
 * ```
 * youtrack {
 *   id = "<Connection ID>" // arbitrary ID, must be unique in the project
 *   displayName = "<Connection display name>"
 *   host = "<YouTrack URL>"
 *   projectExtIds = "<Project ids>" // Space separated list of project ids
 *   accessToken = "credentialsJSON:*****"
 * }
 * ```
 *
 *
 * @see YouTrackIssueTracker
 */
fun ProjectFeatures.youtrack(init: YouTrackIssueTracker.() -> Unit): YouTrackIssueTracker {
    val result = YouTrackIssueTracker(init)
    feature(result)
    return result
}
