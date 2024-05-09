package jetbrains.buildServer.configs.kotlin.v10.projectFeatures

import jetbrains.buildServer.configs.kotlin.v10.*

/**
 * Stores information and configuration for the access to a HashiCorp Vault.
 *
 * **Example.**
 * Adds a new Connection that allows TeamCity to connect to a HashiCorpVault using AppRole
 * ```
 * hashiCorpVaultConnection {
 *     id = "PROJECT_ID"
 *     name = "HashiCorp Vault"
 *     authMethod = appRole {
 *       endpointPath = "approle"
 *       roleId = "id"
 *       secretId = "credentialsJSON:ee2362ab-33fb-40bd-b49b-e77c4a45e978"
 *   }
 * }
 * ```
 *
 * **Example.**
 * Adds a new Connection that allows TeamCity to connect to a HashiCorpVault using AppRole. It defines it own parameter namespace and a respective vault namespace.
 * ```
 * hashiCorpVaultConnection {
 *     id = "PROJECT_ID"
 *     name = "HashiCorp Vault"
 *     vaultId = "other-namespace"
 *     vaultNamespace = "other"
 *     authMethod = appRole {
 *       endpointPath = "approle"
 *       roleId = "id"
 *       secretId = "credentialsJSON:ee2362ab-33fb-40bd-b49b-e77c4a45e978"
 *   }
 * }
 * ```
 *
 * **Example.**
 * Adds a new Connection that allows TeamCity to connect to a HashiCorpVault using LDAP
 * ```
 * hashiCorpVaultConnection {
 *     id = "PROJECT_ID"
 *     name = "HashiCorp Vault"
 *     authMethod = ldap {
 *       path = "path"
 *       username = "username"
 *       password = "credentialsJSON:636e34b4-d02e-4f27-9d12-141f75e8832b"
 *     }
 * }
 * ```
 *
 *
 * @see hashiCorpVaultConnection
 */
open class HashiCorpVaultConnection : ProjectFeature {
    constructor(init: HashiCorpVaultConnection.() -> Unit = {}, base: HashiCorpVaultConnection? = null): super(base = base as ProjectFeature?) {
        type = "OAuthProvider"
        param("providerType", "teamcity-vault")
        init()
    }

    /**
     * HashiCorp Vault connection display name
     */
    var name by stringParameter("displayName")

    /**
     * Vault ID. Should be provided in case of multiple vault connections
     */
    var vaultId by stringParameter("namespace")

    /**
     * Vault ID. Should be provided in case of multiple vault connections
     */
    @Deprecated("Use `id` property instead", replaceWith = ReplaceWith("vaultId"))
    var namespace by stringParameter()

    /**
     * HashiCorp Vault namespace that the auth method and secrets engines are housed under.
     */
    var vaultNamespace by stringParameter("vault-namespace")

    /**
     * Vault URL
     */
    var url by stringParameter()

    /**
     * The way how to obtain credentials (just provide the keys, assume IAM role or other)
     */
    var authMethod by compoundParameter<AuthMethod>("auth-method")

    sealed class AuthMethod(value: String? = null): CompoundParam(value) {
        class AppRole() : AuthMethod("approle") {

            /**
             * AppRole auth endpoint path
             */
            var endpointPath by stringParameter("endpoint")

            /**
             * AppRole Role ID
             */
            var roleId by stringParameter("role-id")

            /**
             * AppRole Secret ID
             */
            var secretId by stringParameter("secure:secret-id")

        }

        class Ldap() : AuthMethod("ldap") {

            /**
             * Path of the ldap authentication backend mount
             */
            var path by stringParameter()

            /**
             * LDAP Username
             */
            var username by stringParameter()

            /**
             * LDAP Password
             */
            var password by stringParameter("secure:password")

        }
    }

    fun appRole(init: AuthMethod.AppRole.() -> Unit = {}) : AuthMethod.AppRole {
        val result = AuthMethod.AppRole()
        result.init()
        return result
    }

    fun ldap(init: AuthMethod.Ldap.() -> Unit = {}) : AuthMethod.Ldap {
        val result = AuthMethod.Ldap()
        result.init()
        return result
    }

    /**
     * Whether to fail builds in case of parameter resolving error
     */
    var failOnError by booleanParameter("fail-on-error")

}


/**
 *
 * **Example.**
 * Adds a new Connection that allows TeamCity to connect to a HashiCorpVault using AppRole
 * ```
 * hashiCorpVaultConnection {
 *     id = "PROJECT_ID"
 *     name = "HashiCorp Vault"
 *     authMethod = appRole {
 *       endpointPath = "approle"
 *       roleId = "id"
 *       secretId = "credentialsJSON:ee2362ab-33fb-40bd-b49b-e77c4a45e978"
 *   }
 * }
 * ```
 *
 * **Example.**
 * Adds a new Connection that allows TeamCity to connect to a HashiCorpVault using AppRole. It defines it own parameter namespace and a respective vault namespace.
 * ```
 * hashiCorpVaultConnection {
 *     id = "PROJECT_ID"
 *     name = "HashiCorp Vault"
 *     vaultId = "other-namespace"
 *     vaultNamespace = "other"
 *     authMethod = appRole {
 *       endpointPath = "approle"
 *       roleId = "id"
 *       secretId = "credentialsJSON:ee2362ab-33fb-40bd-b49b-e77c4a45e978"
 *   }
 * }
 * ```
 *
 * **Example.**
 * Adds a new Connection that allows TeamCity to connect to a HashiCorpVault using LDAP
 * ```
 * hashiCorpVaultConnection {
 *     id = "PROJECT_ID"
 *     name = "HashiCorp Vault"
 *     authMethod = ldap {
 *       path = "path"
 *       username = "username"
 *       password = "credentialsJSON:636e34b4-d02e-4f27-9d12-141f75e8832b"
 *     }
 * }
 * ```
 *
 *
 * @see HashiCorpVaultConnection
 */
fun ProjectFeatures.hashiCorpVaultConnection(base: HashiCorpVaultConnection? = null, init: HashiCorpVaultConnection.() -> Unit = {}) {
    feature(HashiCorpVaultConnection(init, base))
}
