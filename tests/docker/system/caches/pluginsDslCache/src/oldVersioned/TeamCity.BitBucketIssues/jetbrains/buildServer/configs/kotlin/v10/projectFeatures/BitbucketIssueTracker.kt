package jetbrains.buildServer.configs.kotlin.v10.projectFeatures

import jetbrains.buildServer.configs.kotlin.v10.*

/**
 * Project feature enabling integration with Bitbucket issue tracker
 *
 * **Example.**
 * It is not recommended to store secure values such as passwords directly in the DSL code,
 * see [Managing Tokens](https://www.jetbrains.com/help/teamcity/storing-project-settings-in-version-control.html#Managing+Tokens)
 * section of our documentation.
 * ```
 * bitbucketIssues {
 *     id = "<Connection ID>" // arbitrary ID, must be unique in the project
 *     displayName = "<Connection display name>"
 *     repositoryURL = "<Bitbucket Cloud repository URL>"
 *     authType = usernameAndPassword {
 *         userName = "<Username>"
 *         password = "credentialsJSON:*****"
 *     }
 *     issuesPattern = "<Issue id pattern>" // optional, assumed #(\d+) if omitted
 * }
 * ```
 *
 *
 * @see bitbucketIssues
 */
open class BitbucketIssueTracker : ProjectFeature {
    constructor(init: BitbucketIssueTracker.() -> Unit = {}, base: BitbucketIssueTracker? = null): super(base = base as ProjectFeature?) {
        type = "IssueTracker"
        param("type", "BitBucketIssues")
        param("username", "")
        param("secure:password", "")
        init()
    }

    /**
     * Issue tracker integration display name.
     */
    var displayName by stringParameter("name")

    /**
     * Bitbucket server URL.
     */
    var repositoryURL by stringParameter("repository")

    var authType by compoundParameter<AuthType>()

    sealed class AuthType(value: String? = null): CompoundParam(value) {
        class Anonymous() : AuthType("anonymous") {

        }

        class UsernameAndPassword() : AuthType("loginpassword") {

            var userName by stringParameter("username")

            var password by stringParameter("secure:password")

        }
    }

    fun anonymous() = AuthType.Anonymous()

    fun usernameAndPassword(init: AuthType.UsernameAndPassword.() -> Unit = {}) : AuthType.UsernameAndPassword {
        val result = AuthType.UsernameAndPassword()
        result.init()
        return result
    }

    /**
     * Issues ID pattern. Use regex syntax, e.g. '#(\d+)'.
     */
    var issuesPattern by stringParameter("pattern")

}


/**
 * Adds a project features enabling integration with Bitbucket issue tracker
 *
 * **Example.**
 * It is not recommended to store secure values such as passwords directly in the DSL code,
 * see [Managing Tokens](https://www.jetbrains.com/help/teamcity/storing-project-settings-in-version-control.html#Managing+Tokens)
 * section of our documentation.
 * ```
 * bitbucketIssues {
 *     id = "<Connection ID>" // arbitrary ID, must be unique in the project
 *     displayName = "<Connection display name>"
 *     repositoryURL = "<Bitbucket Cloud repository URL>"
 *     authType = usernameAndPassword {
 *         userName = "<Username>"
 *         password = "credentialsJSON:*****"
 *     }
 *     issuesPattern = "<Issue id pattern>" // optional, assumed #(\d+) if omitted
 * }
 * ```
 *
 *
 * @see BitbucketIssueTracker
 */
fun ProjectFeatures.bitbucketIssues(base: BitbucketIssueTracker? = null, init: BitbucketIssueTracker.() -> Unit = {}) {
    feature(BitbucketIssueTracker(init, base))
}
