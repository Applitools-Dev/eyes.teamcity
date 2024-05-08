package jetbrains.buildServer.configs.kotlin.v10.projectFeatures

import jetbrains.buildServer.configs.kotlin.v10.*

/**
 * Project feature enabling integration with GitLab issue tracker
 *
 * **Example.**
 * It is not recommended to store secure values such as tokens directly in the DSL code,
 * see [Managing Tokens](https://www.jetbrains.com/help/teamcity/storing-project-settings-in-version-control.html#Managing+Tokens)
 * section of our documentation.
 * ```
 * gitlabIssues {
 *   id = "<Connection ID>" // arbitrary ID, must be unique in the project
 *   displayName = "<Connection display name>"
 *   repositoryURL = "<GitLab project URL>"
 *   authType = accessToken {
 *     accessToken = "credentialsJSON:*****"
 *   }
 *   issuesPattern = "<Issue id pattern>" // optional, assumed #(\d+) if omitted
 * }
 * ```
 *
 *
 * @see gitlabIssues
 */
open class GitLabIssueTracker : ProjectFeature {
    constructor(init: GitLabIssueTracker.() -> Unit = {}, base: GitLabIssueTracker? = null): super(base = base as ProjectFeature?) {
        type = "IssueTracker"
        param("type", "GitLabIssues")
        param("secure:accessToken", "")
        init()
    }

    /**
     * Issue tracker integration display name.
     */
    var displayName by stringParameter("name")

    /**
     * GitLab server URL.
     */
    var repositoryURL by stringParameter("repository")

    var authType by compoundParameter<AuthType>()

    sealed class AuthType(value: String? = null): CompoundParam(value) {
        class Anonymous() : AuthType("anonymous") {

        }

        class AccessToken() : AuthType("accesstoken") {

            var accessToken by stringParameter("secure:accessToken")

        }
    }

    fun anonymous() = AuthType.Anonymous()

    fun accessToken(init: AuthType.AccessToken.() -> Unit = {}) : AuthType.AccessToken {
        val result = AuthType.AccessToken()
        result.init()
        return result
    }

    /**
     * Issues ID pattern. Use regex syntax, e.g. '#(\d+)'.
     */
    var issuesPattern by stringParameter("pattern")

}


/**
 * Adds a project features enabling integration with GitLab issue tracker
 *
 * **Example.**
 * It is not recommended to store secure values such as tokens directly in the DSL code,
 * see [Managing Tokens](https://www.jetbrains.com/help/teamcity/storing-project-settings-in-version-control.html#Managing+Tokens)
 * section of our documentation.
 * ```
 * gitlabIssues {
 *   id = "<Connection ID>" // arbitrary ID, must be unique in the project
 *   displayName = "<Connection display name>"
 *   repositoryURL = "<GitLab project URL>"
 *   authType = accessToken {
 *     accessToken = "credentialsJSON:*****"
 *   }
 *   issuesPattern = "<Issue id pattern>" // optional, assumed #(\d+) if omitted
 * }
 * ```
 *
 *
 * @see GitLabIssueTracker
 */
fun ProjectFeatures.gitlabIssues(base: GitLabIssueTracker? = null, init: GitLabIssueTracker.() -> Unit = {}) {
    feature(GitLabIssueTracker(init, base))
}
