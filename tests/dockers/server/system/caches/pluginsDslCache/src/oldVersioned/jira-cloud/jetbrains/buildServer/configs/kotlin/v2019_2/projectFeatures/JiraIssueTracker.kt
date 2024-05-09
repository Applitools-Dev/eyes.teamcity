package jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures

import jetbrains.buildServer.configs.kotlin.v2019_2.*

/**
 * Project feature enabling integration with [JIRA](https://www.jetbrains.com/help/teamcity/?JIRA) issue tracker
 *
 * **Example.**
 * It is not recommended to store secure values such as tokens or client secrets directly in the DSL code,
 * see [Managing Tokens](https://www.jetbrains.com/help/teamcity/storing-project-settings-in-version-control.html#Managing+Tokens)
 * section of our documentation.
 * ```
 * jira {
 *   id = "< Connection ID>" // arbitrary ID that can be later used to refer to the connection
 *   displayName = "<Connection display name>"
 *   host = "<Jira server URL>"
 *   userName = "<Username>" // username for self-hosted, email for Jira Cloud
 *   password = "credentialsJSON:*****" // password for self-hosted, access token for Jira Cloud
 *   projectKeys = "<Project keys>" // space-separated list of project keys
 *   useAutomaticKeys = true // optional, if set to true TeamCity will detect all accessible Jira projects
 *   // The following credentials are only used by Jira Cloud Integration build feature
 *   cloudClientID = "<Client ID>" // optional, Jira Cloud client ID
 *   cloudSecret = "credentialsJSON:*****" // optional, Jira Cloud client secret
 * }
 * ```
 *
 *
 * @see jira
 */
open class JiraIssueTracker() : ProjectFeature() {

    init {
        type = "IssueTracker"
        param("type", "jira")
    }

    constructor(init: JiraIssueTracker.() -> Unit): this() {
        init()
    }

    /**
     * Issue tracker integration display name
     */
    var displayName by stringParameter("name")

    /**
     * JIRA server URL
     */
    var host by stringParameter()

    /**
     * A username for JIRA connection
     */
    var userName by stringParameter("username")

    /**
     * A password for JIRA connection
     */
    var password by stringParameter("secure:password")

    var projectKeys by stringParameter("idPrefix")

    /**
     * Automatically populate JIRA Project keys
     */
    var useAutomaticKeys by booleanParameter("autoSync", trueValue = "true", falseValue = "")

    /**
     * A Client ID for JIRA Cloud integration via its Build and Deployment APIs
     */
    var cloudClientID by stringParameter("jiraCloudClientId")

    /**
     * A Secret for JIRA Cloud integration via its Build and Deployment APIs
     */
    var cloudSecret by stringParameter("secure:jiraCloudServerSecret")

    override fun validate(consumer: ErrorConsumer) {
        super.validate(consumer)
        if (displayName == null && !hasParam("name")) {
            consumer.consumePropertyError("displayName", "mandatory 'displayName' property is not specified")
        }
        if (host == null && !hasParam("host")) {
            consumer.consumePropertyError("host", "mandatory 'host' property is not specified")
        }
        if (projectKeys == null && !hasParam("idPrefix")) {
            consumer.consumePropertyError("projectKeys", "mandatory 'projectKeys' property is not specified")
        }
    }
}


/**
 * Enables integration with [JIRA](https://www.jetbrains.com/help/teamcity/?JIRA) issue tracker
 *
 * **Example.**
 * It is not recommended to store secure values such as tokens or client secrets directly in the DSL code,
 * see [Managing Tokens](https://www.jetbrains.com/help/teamcity/storing-project-settings-in-version-control.html#Managing+Tokens)
 * section of our documentation.
 * ```
 * jira {
 *   id = "< Connection ID>" // arbitrary ID that can be later used to refer to the connection
 *   displayName = "<Connection display name>"
 *   host = "<Jira server URL>"
 *   userName = "<Username>" // username for self-hosted, email for Jira Cloud
 *   password = "credentialsJSON:*****" // password for self-hosted, access token for Jira Cloud
 *   projectKeys = "<Project keys>" // space-separated list of project keys
 *   useAutomaticKeys = true // optional, if set to true TeamCity will detect all accessible Jira projects
 *   // The following credentials are only used by Jira Cloud Integration build feature
 *   cloudClientID = "<Client ID>" // optional, Jira Cloud client ID
 *   cloudSecret = "credentialsJSON:*****" // optional, Jira Cloud client secret
 * }
 * ```
 *
 *
 * @see JiraIssueTracker
 */
fun ProjectFeatures.jira(init: JiraIssueTracker.() -> Unit): JiraIssueTracker {
    val result = JiraIssueTracker(init)
    feature(result)
    return result
}
