package jetbrains.buildServer.configs.kotlin.v10.buildFeatures

import jetbrains.buildServer.configs.kotlin.v10.*

/**
 * A [build feature](https://www.jetbrains.com/help/teamcity/?SSH+Agent) which runs
 * SSH agent during a build with the specified SSH key loaded
 *
 * **Example.**
 * Adds an [SSH Agent](https://www.jetbrains.com/help/teamcity/?SSH+Agent) build feature
 * with specified uploaded ssh key ID and passphrase (optional parameter).
 * ```
 * sshAgent {
 *     teamcitySshKey = "id"
 *     passphrase = "credentialsJSON:******"
 * }
 * ```
 *
 *
 * @see sshAgent
 */
open class SshAgent : BuildFeature {
    constructor(init: SshAgent.() -> Unit = {}, base: SshAgent? = null): super(base = base as BuildFeature?) {
        type = "ssh-agent-build-feature"
        init()
    }

    /**
     * [Uploaded SSH key](https://www.jetbrains.com/help/teamcity/?SSH+Keys+Management) name to load into an SSH agent
     */
    var teamcitySshKey by stringParameter()

    /**
     * SSH key passphrase
     */
    var passphrase by stringParameter("secure:passphrase")

}


/**
 * Adds a [build feature](https://www.jetbrains.com/help/teamcity/?SSH+Agent) which runs
 * SSH agent during a build with the specified SSH key loaded
 *
 * **Example.**
 * Adds an [SSH Agent](https://www.jetbrains.com/help/teamcity/?SSH+Agent) build feature
 * with specified uploaded ssh key ID and passphrase (optional parameter).
 * ```
 * sshAgent {
 *     teamcitySshKey = "id"
 *     passphrase = "credentialsJSON:******"
 * }
 * ```
 *
 *
 * @see SshAgent
 */
fun BuildFeatures.sshAgent(base: SshAgent? = null, init: SshAgent.() -> Unit = {}) {
    feature(SshAgent(init, base))
}
