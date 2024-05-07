package jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures

import jetbrains.buildServer.configs.kotlin.v2019_2.*

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
open class BitbucketIssueTracker() : ProjectFeature() {

    init {
        type = "IssueTracker"
        param("type", "BitBucketIssues")
        param("username", "")
        param("secure:password", "")
    }

    constructor(init: BitbucketIssueTracker.() -> Unit): this() {
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

    sealed class AuthType(value: String? = null): CompoundParam<AuthType>(value) {
        abstract fun validate(consumer: ErrorConsumer)

        class Anonymous() : AuthType("anonymous") {

            override fun validate(consumer: ErrorConsumer) {
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
fun ProjectFeatures.bitbucketIssues(init: BitbucketIssueTracker.() -> Unit): BitbucketIssueTracker {
    val result = BitbucketIssueTracker(init)
    feature(result)
    return result
}
