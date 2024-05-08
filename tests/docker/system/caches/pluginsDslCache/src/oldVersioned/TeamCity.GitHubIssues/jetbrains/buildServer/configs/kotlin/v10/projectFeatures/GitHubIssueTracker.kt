package jetbrains.buildServer.configs.kotlin.v10.projectFeatures

import jetbrains.buildServer.configs.kotlin.v10.*

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
open class GitHubIssueTracker : ProjectFeature {
    constructor(init: GitHubIssueTracker.() -> Unit = {}, base: GitHubIssueTracker? = null): super(base = base as ProjectFeature?) {
        type = "IssueTracker"
        param("type", "GithubIssues")
        param("secure:accessToken", "")
        param("username", "")
        param("secure:password", "")
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

    sealed class AuthType(value: String? = null): CompoundParam(value) {
        class Anonymous() : AuthType("anonymous") {

        }

        class AccessToken() : AuthType("accesstoken") {

            var accessToken by stringParameter("secure:accessToken")

        }

        class UsernameAndPassword() : AuthType("loginpassword") {

            var userName by stringParameter("username")

            var password by stringParameter("secure:password")

        }

        class StoredToken() : AuthType("storedToken") {

            /**
             * Internal ID of a token in TeamCity token storage
             */
            var tokenId by stringParameter()

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
fun ProjectFeatures.githubIssues(base: GitHubIssueTracker? = null, init: GitHubIssueTracker.() -> Unit = {}) {
    feature(GitHubIssueTracker(init, base))
}
