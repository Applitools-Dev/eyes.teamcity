package jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps

import jetbrains.buildServer.configs.kotlin.v2019_2.*

/**
 * An SSH Exec build step.
 *
 * **Example.**
 * Adds an SSH Exec build step with custom port
 * and [Uploaded key authentication method](https://www.jetbrains.com/help/teamcity/?SSH+Exec).
 * ```
 * sshExec {
 *     name = "My SSH Exec build step"
 *     commands = """
 *         command1
 *         command2
 *     """.trimIndent()
 *     targetUrl = "8.8.8.8"
 *     port = 2222
 *     authMethod = uploadedKey {
 *         username = "username"
 *         passphrase = "credentialsJSON:******"
 *         key = "id_rsa"
 *     }
 * }
 * ```
 *
 * **Example.**
 * Adds an SSH Exec build step with
 * [Default private key authentication method](https://www.jetbrains.com/help/teamcity/?SSH+Exec).
 * ```
 * sshExec {
 *     name = "My SSH Exec build step"
 *     commands = """
 *         command1
 *         command2
 *     """.trimIndent()
 *     targetUrl = "8.8.8.8"
 *     authMethod = defaultPrivateKey {
 *         username = "username"
 *         passphrase = "credentialsJSON:******"
 *     }
 * }
 * ```
 *
 * **Example.**
 * Adds an SSH Exec build step with pty enabled
 * and [Custom private key authentication method](https://www.jetbrains.com/help/teamcity/?SSH+Exec).
 * ```
 * sshExec {
 *     name = "My SSH Exec build step"
 *     pty = "vt100"
 *     commands = """
 *         command1
 *         command2
 *     """.trimIndent()
 *     targetUrl = "8.8.8.8"
 *     authMethod = customPrivateKey {
 *         keyFile = "path/to/key/file"
 *         username = "username"
 *         passphrase = "credentialsJSON:******"
 *     }
 * }
 * ```
 *
 * **Example.**
 * Adds an SSH Exec build step with
 * [Password authentication method](https://www.jetbrains.com/help/teamcity/?SSH+Exec).
 * ```
 * sshExec {
 *     name = "My SSH Exec build step"
 *     commands = """
 *         command1
 *         command2
 *     """.trimIndent()
 *     targetUrl = "8.8.8.8"
 *     authMethod = password {
 *         username = "username"
 *         password = "credentialsJSON:******"
 *     }
 * }
 * ```
 *
 * **Example.**
 * Adds an SSH Exec build step with
 * [SSH-Agent authentication method](https://www.jetbrains.com/help/teamcity/?SSH+Exec).
 * This build step will run even if some previous build steps are failed.
 * ```
 * sshExec {
 *     name = "My SSH Exec build step"
 *     executionMode = BuildStep.ExecutionMode.RUN_ON_FAILURE
 *     commands = """
 *         command1
 *         command2
 *     """.trimIndent()
 *     targetUrl = "8.8.8.8"
 *     authMethod = sshAgent {
 *         username = "username"
 *     }
 * }
 * ```
 *
 *
 * @see sshExec
 */
open class SSHExec() : BuildStep() {

    init {
        type = "ssh-exec-runner"
    }

    constructor(init: SSHExec.() -> Unit): this() {
        init()
    }

    /**
     * Optional. Specify the type of the pty terminal. For example, "vt100".
     * If empty, pty will not be allocated (default).
     */
    var pty by stringParameter("jetbrains.buildServer.sshexec.pty")

    /**
     * Specify a new-line delimited set of commands that will be executed in the remote shell.
     * The remote shell will be started in the home directory of an authenticated user.
     * The shell output will be available in the TeamCity build log.
     */
    var commands by stringParameter("jetbrains.buildServer.sshexec.command")

    /**
     * Target url in form {hostname|ip_address}[:path/to/target/folder].
     */
    var targetUrl by stringParameter("jetbrains.buildServer.deployer.targetUrl")

    /**
     * Optional. Default value: 22.
     */
    var port by intParameter("jetbrains.buildServer.sshexec.port")

    /**
     * Optional. Default value (seconds): 60 (0 - disable timeout).
     */
    var timeout by intParameter("jetbrains.buildServer.sshexec.timeout.seconds")

    /**
     * An SSH authentication method.
     */
    var authMethod by compoundParameter<AuthMethod>("jetbrains.buildServer.sshexec.authMethod")

    sealed class AuthMethod(value: String? = null): CompoundParam<AuthMethod>(value) {
        abstract fun validate(consumer: ErrorConsumer)

        class UploadedKey() : AuthMethod("UPLOADED_KEY") {

            var username by stringParameter("jetbrains.buildServer.deployer.username")

            var passphrase by stringParameter("secure:jetbrains.buildServer.deployer.password")

            /**
             * The name of the key uploaded to the project.
             */
            var key by stringParameter("teamcitySshKey")

            override fun validate(consumer: ErrorConsumer) {
                if (username == null && !hasParam("jetbrains.buildServer.deployer.username")) {
                    consumer.consumePropertyError("authMethod.username", "mandatory 'authMethod.username' property is not specified")
                }
                if (key == null && !hasParam("teamcitySshKey")) {
                    consumer.consumePropertyError("authMethod.key", "mandatory 'authMethod.key' property is not specified")
                }
            }
        }

        class DefaultPrivateKey() : AuthMethod("DEFAULT_KEY") {

            /**
             * Username (optional, used with "id_rsa" key).
             */
            var username by stringParameter("jetbrains.buildServer.deployer.username")

            var passphrase by stringParameter("secure:jetbrains.buildServer.deployer.password")

            override fun validate(consumer: ErrorConsumer) {
            }
        }

        class CustomPrivateKey() : AuthMethod("CUSTOM_KEY") {

            /**
             * Path to key file (optional).
             */
            var keyFile by stringParameter("jetbrains.buildServer.sshexec.keyFile")

            var username by stringParameter("jetbrains.buildServer.deployer.username")

            var passphrase by stringParameter("secure:jetbrains.buildServer.deployer.password")

            override fun validate(consumer: ErrorConsumer) {
                if (username == null && !hasParam("jetbrains.buildServer.deployer.username")) {
                    consumer.consumePropertyError("authMethod.username", "mandatory 'authMethod.username' property is not specified")
                }
            }
        }

        class Password() : AuthMethod("PWD") {

            var username by stringParameter("jetbrains.buildServer.deployer.username")

            var password by stringParameter("secure:jetbrains.buildServer.deployer.password")

            override fun validate(consumer: ErrorConsumer) {
                if (username == null && !hasParam("jetbrains.buildServer.deployer.username")) {
                    consumer.consumePropertyError("authMethod.username", "mandatory 'authMethod.username' property is not specified")
                }
            }
        }

        class SshAgent() : AuthMethod("SSH_AGENT") {

            var username by stringParameter("jetbrains.buildServer.deployer.username")

            override fun validate(consumer: ErrorConsumer) {
                if (username == null && !hasParam("jetbrains.buildServer.deployer.username")) {
                    consumer.consumePropertyError("authMethod.username", "mandatory 'authMethod.username' property is not specified")
                }
            }
        }
    }

    /**
     * Uses the key(s) uploaded to the project.
     */
    fun uploadedKey(init: AuthMethod.UploadedKey.() -> Unit = {}) : AuthMethod.UploadedKey {
        val result = AuthMethod.UploadedKey()
        result.init()
        return result
    }

    /**
     * Will try to perform private key authentication using the ~/.ssh/config settings.
     * If no settings file exists, will try to use the ~/.ssh/rsa_pub public key file.
     */
    fun defaultPrivateKey(init: AuthMethod.DefaultPrivateKey.() -> Unit = {}) : AuthMethod.DefaultPrivateKey {
        val result = AuthMethod.DefaultPrivateKey()
        result.init()
        return result
    }

    /**
     * Will try to perform private key authentication
     * using the given public key file with given passphrase.
     */
    fun customPrivateKey(init: AuthMethod.CustomPrivateKey.() -> Unit = {}) : AuthMethod.CustomPrivateKey {
        val result = AuthMethod.CustomPrivateKey()
        result.init()
        return result
    }

    /**
     * Simple password authentication.
     */
    fun password(init: AuthMethod.Password.() -> Unit = {}) : AuthMethod.Password {
        val result = AuthMethod.Password()
        result.init()
        return result
    }

    /**
     * Use ssh-agent for authentication, the SSH-Agent build feature must be enabled.
     */
    fun sshAgent(init: AuthMethod.SshAgent.() -> Unit = {}) : AuthMethod.SshAgent {
        val result = AuthMethod.SshAgent()
        result.init()
        return result
    }

    override fun validate(consumer: ErrorConsumer) {
        super.validate(consumer)
        if (commands == null && !hasParam("jetbrains.buildServer.sshexec.command")) {
            consumer.consumePropertyError("commands", "mandatory 'commands' property is not specified")
        }
        if (targetUrl == null && !hasParam("jetbrains.buildServer.deployer.targetUrl")) {
            consumer.consumePropertyError("targetUrl", "mandatory 'targetUrl' property is not specified")
        }
        if (authMethod == null && !hasParam("jetbrains.buildServer.sshexec.authMethod")) {
            consumer.consumePropertyError("authMethod", "mandatory 'authMethod' property is not specified")
        }
        authMethod?.validate(consumer)
    }
}


/**
 * Adds an SSH Exec build step.
 *
 * **Example.**
 * Adds an SSH Exec build step with custom port
 * and [Uploaded key authentication method](https://www.jetbrains.com/help/teamcity/?SSH+Exec).
 * ```
 * sshExec {
 *     name = "My SSH Exec build step"
 *     commands = """
 *         command1
 *         command2
 *     """.trimIndent()
 *     targetUrl = "8.8.8.8"
 *     port = 2222
 *     authMethod = uploadedKey {
 *         username = "username"
 *         passphrase = "credentialsJSON:******"
 *         key = "id_rsa"
 *     }
 * }
 * ```
 *
 * **Example.**
 * Adds an SSH Exec build step with
 * [Default private key authentication method](https://www.jetbrains.com/help/teamcity/?SSH+Exec).
 * ```
 * sshExec {
 *     name = "My SSH Exec build step"
 *     commands = """
 *         command1
 *         command2
 *     """.trimIndent()
 *     targetUrl = "8.8.8.8"
 *     authMethod = defaultPrivateKey {
 *         username = "username"
 *         passphrase = "credentialsJSON:******"
 *     }
 * }
 * ```
 *
 * **Example.**
 * Adds an SSH Exec build step with pty enabled
 * and [Custom private key authentication method](https://www.jetbrains.com/help/teamcity/?SSH+Exec).
 * ```
 * sshExec {
 *     name = "My SSH Exec build step"
 *     pty = "vt100"
 *     commands = """
 *         command1
 *         command2
 *     """.trimIndent()
 *     targetUrl = "8.8.8.8"
 *     authMethod = customPrivateKey {
 *         keyFile = "path/to/key/file"
 *         username = "username"
 *         passphrase = "credentialsJSON:******"
 *     }
 * }
 * ```
 *
 * **Example.**
 * Adds an SSH Exec build step with
 * [Password authentication method](https://www.jetbrains.com/help/teamcity/?SSH+Exec).
 * ```
 * sshExec {
 *     name = "My SSH Exec build step"
 *     commands = """
 *         command1
 *         command2
 *     """.trimIndent()
 *     targetUrl = "8.8.8.8"
 *     authMethod = password {
 *         username = "username"
 *         password = "credentialsJSON:******"
 *     }
 * }
 * ```
 *
 * **Example.**
 * Adds an SSH Exec build step with
 * [SSH-Agent authentication method](https://www.jetbrains.com/help/teamcity/?SSH+Exec).
 * This build step will run even if some previous build steps are failed.
 * ```
 * sshExec {
 *     name = "My SSH Exec build step"
 *     executionMode = BuildStep.ExecutionMode.RUN_ON_FAILURE
 *     commands = """
 *         command1
 *         command2
 *     """.trimIndent()
 *     targetUrl = "8.8.8.8"
 *     authMethod = sshAgent {
 *         username = "username"
 *     }
 * }
 * ```
 *
 *
 * @see SSHExec
 */
fun BuildSteps.sshExec(init: SSHExec.() -> Unit): SSHExec {
    val result = SSHExec(init)
    step(result)
    return result
}
