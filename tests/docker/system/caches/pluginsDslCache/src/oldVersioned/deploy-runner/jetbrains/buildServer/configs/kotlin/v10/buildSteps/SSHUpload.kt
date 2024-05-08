package jetbrains.buildServer.configs.kotlin.v10.buildSteps

import jetbrains.buildServer.configs.kotlin.v10.*

/**
 * An SSH Upload build step.
 *
 * **Example.**
 * Adds an SSH Upload build step with
 * SCP transport protocol
 * and custom timeout.
 * [Uploaded key authentication method](https://www.jetbrains.com/help/teamcity/?SSH+Upload) is used.
 * ```
 * sshUpload {
 *     name = "My SSH Upload build step"
 *     transportProtocol = SSHUpload.TransportProtocol.SCP
 *     sourcePath = """
 *         dir/**/*.zip
 *         *.zip => winFiles
 *         unix/distro.tgz => linuxFiles
 *     """.trimIndent()
 *     targetUrl = "hostname:path/to/target/folder"
 *     timeout = 30
 *     authMethod = uploadedKey {
 *         username = "username"
 *         passphrase = "credentialsJSON:******"
 *         key = "id_rsa"
 *     }
 * }
 * ```
 *
 * **Example.**
 * Adds an SSH Upload build step with
 * SFTP transport protocol, custom port and disabled timeout.
 * [Default private key authentication method](https://www.jetbrains.com/help/teamcity/?SSH+Upload) is used.
 * ```
 * sshUpload {
 *     name = "My SSH Upload build step"
 *     transportProtocol = SSHUpload.TransportProtocol.SFTP
 *     sourcePath = """
 *         dir/**/*.zip
 *         *.zip => winFiles
 *         unix/distro.tgz => linuxFiles
 *     """.trimIndent()
 *     targetUrl = "hostname:path/to/target/folder"
 *     port = 2222
 *     timeout = 0
 *     authMethod = defaultPrivateKey {
 *         username = "username"
 *         passphrase = "credentialsJSON:******"
 *     }
 * }
 * ```
 *
 * **Example.**
 * Adds an SSH Upload build step with
 * SFTP transport protocol.
 * [Custom private key authentication method](https://www.jetbrains.com/help/teamcity/?SSH+Upload) is used.
 * ```
 * sshUpload {
 *     name = "My SSH Upload build step"
 *     transportProtocol = SSHUpload.TransportProtocol.SFTP
 *     sourcePath = """
 *         dir/**/*.zip
 *         *.zip => winFiles
 *         unix/distro.tgz => linuxFiles
 *     """.trimIndent()
 *     targetUrl = "hostname:path/to/target/folder"
 *     authMethod = customPrivateKey {
 *         keyFile = "path/to/key/file"
 *         username = "username"
 *         passphrase = "credentialsJSON:******"
 *     }
 * }
 * ```
 *
 * **Example.**
 * Adds an SSH Upload build step with
 * SCP transport protocol.
 * [Password authentication method](https://www.jetbrains.com/help/teamcity/?SSH+Upload) is used.
 * ```
 * sshUpload {
 *     name = "My SSH Upload build step"
 *     transportProtocol = SSHUpload.TransportProtocol.SCP
 *     sourcePath = """
 *         dir/**/*.zip
 *         *.zip => winFiles
 *         unix/distro.tgz => linuxFiles
 *     """.trimIndent()
 *     targetUrl = "hostname:path/to/target/folder"
 *     authMethod = password {
 *         username = "username"
 *         password = "credentialsJSON:******"
 *     }
 * }
 * ```
 *
 * **Example.**
 * Adds an SSH Upload build step with
 * SCP transport protocol.
 * [SSH-Agent authentication method](https://www.jetbrains.com/help/teamcity/?SSH+Upload) is used.
 * This build step will run even if some previous build steps are failed and additional condition is met.
 * ```
 * sshUpload {
 *     name = "My SSH Upload build step"
 *
 *     executionMode = BuildStep.ExecutionMode.RUN_ON_FAILURE
 *     conditions {
 *         equals("teamcity.build.branch", "release")
 *     }
 *
 *     transportProtocol = SSHUpload.TransportProtocol.SCP
 *     sourcePath = """
 *         dir/**/*.zip
 *         *.zip => winFiles
 *         unix/distro.tgz => linuxFiles
 *     """.trimIndent()
 *     targetUrl = "hostname:path/to/target/folder"
 *     authMethod = sshAgent {
 *         username = "username"
 *     }
 * }
 * ```
 *
 *
 * @see sshUpload
 */
open class SSHUpload : BuildStep {
    constructor(init: SSHUpload.() -> Unit = {}, base: SSHUpload? = null): super(base = base as BuildStep?) {
        type = "ssh-deploy-runner"
        init()
    }

    /**
     * An SSH transfer protocol to use.
     */
    var transportProtocol by enumParameter<TransportProtocol>("jetbrains.buildServer.deployer.ssh.transport", mapping = TransportProtocol.mapping)

    /**
     * Newline- or comma-separated paths to files/directories to be deployed.
     * Ant-style wildcards like dir/**/*.zip and target directories like
     * *.zip => winFiles,unix/distro.tgz => linuxFiles,
     * where winFiles and linuxFiles are target directories, are supported.
     */
    var sourcePath by stringParameter("jetbrains.buildServer.deployer.sourcePath")

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

    sealed class AuthMethod(value: String? = null): CompoundParam(value) {
        class UploadedKey() : AuthMethod("UPLOADED_KEY") {

            var username by stringParameter("jetbrains.buildServer.deployer.username")

            var passphrase by stringParameter("secure:jetbrains.buildServer.deployer.password")

            /**
             * The name of the key uploaded to the project.
             */
            var key by stringParameter("teamcitySshKey")

        }

        class DefaultPrivateKey() : AuthMethod("DEFAULT_KEY") {

            /**
             * Username (optional, used with "id_rsa" key).
             */
            var username by stringParameter("jetbrains.buildServer.deployer.username")

            var passphrase by stringParameter("secure:jetbrains.buildServer.deployer.password")

        }

        class CustomPrivateKey() : AuthMethod("CUSTOM_KEY") {

            /**
             * Path to key file (optional).
             */
            var keyFile by stringParameter("jetbrains.buildServer.sshexec.keyFile")

            var username by stringParameter("jetbrains.buildServer.deployer.username")

            var passphrase by stringParameter("secure:jetbrains.buildServer.deployer.password")

        }

        class Password() : AuthMethod("PWD") {

            var username by stringParameter("jetbrains.buildServer.deployer.username")

            var password by stringParameter("secure:jetbrains.buildServer.deployer.password")

        }

        class SshAgent() : AuthMethod("SSH_AGENT") {

            var username by stringParameter("jetbrains.buildServer.deployer.username")

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

    enum class TransportProtocol {
        SFTP,
        SCP;

        companion object {
            val mapping = mapOf<TransportProtocol, String>(SFTP to "jetbrains.buildServer.deployer.ssh.transport.sftp", SCP to "jetbrains.buildServer.deployer.ssh.transport.scp")
        }

    }
}


/**
 * Adds an SSH Upload build step.
 *
 * **Example.**
 * Adds an SSH Upload build step with
 * SCP transport protocol
 * and custom timeout.
 * [Uploaded key authentication method](https://www.jetbrains.com/help/teamcity/?SSH+Upload) is used.
 * ```
 * sshUpload {
 *     name = "My SSH Upload build step"
 *     transportProtocol = SSHUpload.TransportProtocol.SCP
 *     sourcePath = """
 *         dir/**/*.zip
 *         *.zip => winFiles
 *         unix/distro.tgz => linuxFiles
 *     """.trimIndent()
 *     targetUrl = "hostname:path/to/target/folder"
 *     timeout = 30
 *     authMethod = uploadedKey {
 *         username = "username"
 *         passphrase = "credentialsJSON:******"
 *         key = "id_rsa"
 *     }
 * }
 * ```
 *
 * **Example.**
 * Adds an SSH Upload build step with
 * SFTP transport protocol, custom port and disabled timeout.
 * [Default private key authentication method](https://www.jetbrains.com/help/teamcity/?SSH+Upload) is used.
 * ```
 * sshUpload {
 *     name = "My SSH Upload build step"
 *     transportProtocol = SSHUpload.TransportProtocol.SFTP
 *     sourcePath = """
 *         dir/**/*.zip
 *         *.zip => winFiles
 *         unix/distro.tgz => linuxFiles
 *     """.trimIndent()
 *     targetUrl = "hostname:path/to/target/folder"
 *     port = 2222
 *     timeout = 0
 *     authMethod = defaultPrivateKey {
 *         username = "username"
 *         passphrase = "credentialsJSON:******"
 *     }
 * }
 * ```
 *
 * **Example.**
 * Adds an SSH Upload build step with
 * SFTP transport protocol.
 * [Custom private key authentication method](https://www.jetbrains.com/help/teamcity/?SSH+Upload) is used.
 * ```
 * sshUpload {
 *     name = "My SSH Upload build step"
 *     transportProtocol = SSHUpload.TransportProtocol.SFTP
 *     sourcePath = """
 *         dir/**/*.zip
 *         *.zip => winFiles
 *         unix/distro.tgz => linuxFiles
 *     """.trimIndent()
 *     targetUrl = "hostname:path/to/target/folder"
 *     authMethod = customPrivateKey {
 *         keyFile = "path/to/key/file"
 *         username = "username"
 *         passphrase = "credentialsJSON:******"
 *     }
 * }
 * ```
 *
 * **Example.**
 * Adds an SSH Upload build step with
 * SCP transport protocol.
 * [Password authentication method](https://www.jetbrains.com/help/teamcity/?SSH+Upload) is used.
 * ```
 * sshUpload {
 *     name = "My SSH Upload build step"
 *     transportProtocol = SSHUpload.TransportProtocol.SCP
 *     sourcePath = """
 *         dir/**/*.zip
 *         *.zip => winFiles
 *         unix/distro.tgz => linuxFiles
 *     """.trimIndent()
 *     targetUrl = "hostname:path/to/target/folder"
 *     authMethod = password {
 *         username = "username"
 *         password = "credentialsJSON:******"
 *     }
 * }
 * ```
 *
 * **Example.**
 * Adds an SSH Upload build step with
 * SCP transport protocol.
 * [SSH-Agent authentication method](https://www.jetbrains.com/help/teamcity/?SSH+Upload) is used.
 * This build step will run even if some previous build steps are failed and additional condition is met.
 * ```
 * sshUpload {
 *     name = "My SSH Upload build step"
 *
 *     executionMode = BuildStep.ExecutionMode.RUN_ON_FAILURE
 *     conditions {
 *         equals("teamcity.build.branch", "release")
 *     }
 *
 *     transportProtocol = SSHUpload.TransportProtocol.SCP
 *     sourcePath = """
 *         dir/**/*.zip
 *         *.zip => winFiles
 *         unix/distro.tgz => linuxFiles
 *     """.trimIndent()
 *     targetUrl = "hostname:path/to/target/folder"
 *     authMethod = sshAgent {
 *         username = "username"
 *     }
 * }
 * ```
 *
 *
 * @see SSHUpload
 */
fun BuildSteps.sshUpload(base: SSHUpload? = null, init: SSHUpload.() -> Unit = {}) {
    step(SSHUpload(init, base))
}
