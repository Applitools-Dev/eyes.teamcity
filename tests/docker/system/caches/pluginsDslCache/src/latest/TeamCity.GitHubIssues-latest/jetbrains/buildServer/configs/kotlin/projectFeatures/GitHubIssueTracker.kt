package jetbrains.buildServer.configs.kotlin.projectFeatures

import jetbrains.buildServer.configs.kotlin.*

/**
 * Project feature enabling integration with GitHub issue tracker
 *
 * **Example.**
 * It is not recommended to store secure values such as tokens directly in the DSL code,
 * see [Managing Tokens](https://www.jetbrains.com/help/teamcity/storing-project-settings-in-version-control.html#Managing+Tokens)
 * section of our documentation.
 * ```
 * githubIssues {
 *     id = "<Connection ID>" // arbitrary ID, must be unique in the project
 *     displayName = "<Connection display name>"
 *     repositoryURL = "<GitHub repository URL>"
 *     authType = accessToken {
 *         accessToken = "credentialsJSON:*****"
 *     }
 *     issuesPattern = "<Issue id pattern>" // optional, assumed #(\d+) if omitted
 * }
 * ```
 *
 * **Example.**
 * Uses a GitHub App installation token stored in TeamCity.
 * ```
 * githubIssues {
 *     id = "<Connection ID>" // arbitrary ID, must be unique in the project
 *     displayName = "<Connection display name>"
 *     repositoryURL = "<GitHub repository URL>"
 *     authType = storedToken {
 *         tokenId = "tc_token_id:*****"
 *     }
 *     issuesPattern = "<Issue id pattern>" // optional, assumed #(\d+) if omitted
 * }
 * ```
 *
 *
 * @see githubIssues
 */
open class GitHubIssueTracker() : ProjectFeature() {

    init {
        type = "IssueTracker"
        param("type", "GithubIssues")
        param("secure:accessToken", "")
        param("username", "")
        param("secure:password", "")
    }

    constructor(init: GitHubIssueTracker.() -> Unit): this() {
        init()
    }

    /**
     * Issue tracker integration display name.
     */
    var displayName by stringParameter("name")

    /**
     * GitHub server URL.
     */
    var repositoryURL by stringParameter("repository")

    var authType by compoundParameter<AuthType>()

    sealed class AuthType(value: String? = null): CompoundParam<AuthType>(value) {
        abstract fun validate(consumer: ErrorConsumer)

        class Anonymous() : AuthType("anonymous") {

            override fun validate(consumer: ErrorConsumer) {
            }
        }

        class AccessToken() : AuthType("accesstoken") {

            var accessToken by stringParameter("secure:accessToken")

            override fun validate(consumer: ErrorConsumer) {
                if (accessToken == null && !hasParam("secure:accessToken")) {
                    consumer.consumePropertyError("authType.accessToken", "mandatory 'authType.accessToken' property is not specified")
                }
            }
        }

        class UsernameAndPassword() : AuthType("loginpassword") {

            var userName by stringParameter("username")

            var password by stringParameter("secure:password")

            override fun validate(consumer: ErrorConsumer) {
                if (userName == null && !hasParam("username")) {
                    consumer.consumePropertyError("authType.userName", "mandatory 'authType.userName' property is not specified")
                }
                if (password == null && !hasParam("secure:password")) {
                    consumer.consumePropertyError("authType.password", "mandatory 'authType.password' property is not specified")
                }
            }
        }

        class StoredToken() : AuthType("storedToken") {

            /**
             * Internal ID of a token in TeamCity token storage
             */
            var tokenId by stringParameter()

            override fun validate(consumer: ErrorConsumer) {
                if (tokenId == null && !hasParam("tokenId")) {
                    consumer.consumePropertyError("authType.tokenId", "mandatory 'authType.tokenId' property is not specified")
                }
            }
        }
    }

    fun anonymous() = AuthType.Anonymous()

    fun accessToken(init: AuthType.AccessToken.() -> Unit = {}) : AuthType.AccessToken {
        val result = AuthType.AccessToken()
        result.init()
        return result
    }

    /**
     * Authentication via login/password is no longer supported by GitHub.
     * We highly recommend that you authenticate with access tokens instead.
     */
    fun usernameAndPassword(init: AuthType.UsernameAndPassword.() -> Unit = {}) : AuthType.UsernameAndPassword {
        val result = AuthType.UsernameAndPassword()
        result.init()
        return result
    }

    /**
     * Use GitHub App credentials
     */
    fun storedToken(init: AuthType.StoredToken.() -> Unit = {}) : AuthType.StoredToken {
        val result = AuthType.StoredToken()
        result.init()
        return result
    }

    /**
     * Issues ID pattern. Use regex syntax, e.g. '#(\d+)'.
     */
    var issuesPattern by stringParameter("pattern")

    override fun validate(consumer: ErrorConsumer) {
        super.validate(consumer)
        if (displayName == null && !hasParam("name")) {
            consumer.consumePropertyError("displayName", "mandatory 'displayName' property is not specified")
        }
        if (repositoryURL == null && !hasParam("repository")) {
            consumer.consumePropertyError("repositoryURL", "mandatory 'repositoryURL' property is not specified")
        }
        authType?.validate(consumer)
    }
}


/**
 * Adds a project features enabling integration with GitHub issue tracker
 *
 * **Example.**
 * It is not recommended to store secure values such as tokens directly in the DSL code,
 * see [Managing Tokens](https://www.jetbrains.com/help/teamcity/storing-project-settings-in-version-control.html#Managing+Tokens)
 * section of our documentation.
 * ```
 * githubIssues {
 *     id = "<Connection ID>" // arbitrary ID, must be unique in the project
 *     displayName = "<Connection display name>"
 *     repositoryURL = "<GitHub repository URL>"
 *     authType = accessToken {
 *         accessToken = "credentialsJSON:*****"
 *     }
 *     issuesPattern = "<Issue id pattern>" // optional, assumed #(\d+) if omitted
 * }
 * ```
 *
 * **Example.**
 * Uses a GitHub App installation token stored in TeamCity.
 * ```
 * githubIssues {
 *     id = "<Connection ID>" // arbitrary ID, must be unique in the project
 *     displayName = "<Connection display name>"
 *     repositoryURL = "<GitHub repository URL>"
 *     authType = storedToken {
 *         tokenId = "tc_token_id:*****"
 *     }
 *     issuesPattern = "<Issue id pattern>" // optional, assumed #(\d+) if omitted
 * }
 * ```
 *
 *
 * @see GitHubIssueTracker
 */
fun ProjectFeatures.githubIssues(init: GitHubIssueTracker.() -> Unit): GitHubIssueTracker {
    val result = GitHubIssueTracker(init)
    feature(result)
    return result
}
