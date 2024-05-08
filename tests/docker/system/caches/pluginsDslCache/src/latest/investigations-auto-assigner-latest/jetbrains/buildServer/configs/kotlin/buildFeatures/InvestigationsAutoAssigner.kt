package jetbrains.buildServer.configs.kotlin.buildFeatures

import jetbrains.buildServer.configs.kotlin.*

/**
 * A [build feature](https://www.jetbrains.com/help/teamcity/?Investigations+Auto+Assigner) assigning
 * investigations of build failures automatically based on heuristics.
 *
 * **Example.**
 * Adds a simple [Investigations Auto Assigner](https://www.jetbrains.com/help/teamcity/?Investigations+Auto+Assigner)
 * build feature with specified default assignee
 * (username of a user to assign the investigation to if no other assignee can be found).
 * This build feature will assign investigations on the first build failure, after a short time-out.
 * ```
 * investigationsAutoAssigner {
 *     defaultAssignee = "username.default"
 * }
 * ```
 *
 * **Example.**
 * Adds an [Investigations Auto Assigner](https://www.jetbrains.com/help/teamcity/?Investigations+Auto+Assigner)
 * with specified default assignee, users to ignore (newline-separated),
 * and custom types of build problems to ignore in investigations auto-assignment.
 * Additional option delays assignment of investigations until the failure repeats in two builds in a row.
 * Use to prevent wrong assignments in projects with many flaky tests.
 * ```
 * investigationsAutoAssigner {
 *     defaultAssignee = "username.default"
 *     excludeUsers = """
 *         username.admin
 *         username.bot
 *     """.trimIndent()
 *     ignoreCompilationProblems = "true"
 *     ignoreExitCodeProblems = "true"
 *     assignOnSecondFailure = true
 * }
 * ```
 *
 *
 * @see investigationsAutoAssigner
 */
open class InvestigationsAutoAssigner() : BuildFeature() {

    init {
        type = "InvestigationsAutoAssigner"
    }

    constructor(init: InvestigationsAutoAssigner.() -> Unit): this() {
        init()
    }

    /**
     * Username of a user to whom an investigation is assigned if no other possible investigator is found.
     */
    var defaultAssignee by stringParameter("defaultAssignee.username")

    /**
     * The newline-separated list of usernames to exclude from investigation auto-assignment.
     */
    var excludeUsers by stringParameter("excludeAssignees.usernames")

    /**
     * When 'true', compilation build problems are ignored.
     */
    var ignoreCompilationProblems by stringParameter("ignoreBuildProblems.compilation")

    /**
     * When 'true', exit code build problems are ignored.
     */
    var ignoreExitCodeProblems by stringParameter("ignoreBuildProblems.exitCode")

    /**
     * Whether investigations auto-assigner should use "on second failure" strategy.
     */
    var assignOnSecondFailure by booleanParameter(trueValue = "true", falseValue = "")

    override fun validate(consumer: ErrorConsumer) {
        super.validate(consumer)
    }
}


/**
 * Configures Investigations Auto Assigner behaviour.
 *
 * **Example.**
 * Adds a simple [Investigations Auto Assigner](https://www.jetbrains.com/help/teamcity/?Investigations+Auto+Assigner)
 * build feature with specified default assignee
 * (username of a user to assign the investigation to if no other assignee can be found).
 * This build feature will assign investigations on the first build failure, after a short time-out.
 * ```
 * investigationsAutoAssigner {
 *     defaultAssignee = "username.default"
 * }
 * ```
 *
 * **Example.**
 * Adds an [Investigations Auto Assigner](https://www.jetbrains.com/help/teamcity/?Investigations+Auto+Assigner)
 * with specified default assignee, users to ignore (newline-separated),
 * and custom types of build problems to ignore in investigations auto-assignment.
 * Additional option delays assignment of investigations until the failure repeats in two builds in a row.
 * Use to prevent wrong assignments in projects with many flaky tests.
 * ```
 * investigationsAutoAssigner {
 *     defaultAssignee = "username.default"
 *     excludeUsers = """
 *         username.admin
 *         username.bot
 *     """.trimIndent()
 *     ignoreCompilationProblems = "true"
 *     ignoreExitCodeProblems = "true"
 *     assignOnSecondFailure = true
 * }
 * ```
 *
 *
 * @see InvestigationsAutoAssigner
 */
fun BuildFeatures.investigationsAutoAssigner(init: InvestigationsAutoAssigner.() -> Unit): InvestigationsAutoAssigner {
    val result = InvestigationsAutoAssigner(init)
    feature(result)
    return result
}
