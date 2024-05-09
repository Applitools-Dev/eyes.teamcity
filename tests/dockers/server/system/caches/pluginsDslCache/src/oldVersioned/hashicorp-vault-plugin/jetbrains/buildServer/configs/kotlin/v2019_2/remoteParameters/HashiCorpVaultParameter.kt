package jetbrains.buildServer.configs.kotlin.v2019_2.remoteParameters

import jetbrains.buildServer.configs.kotlin.v2019_2.*

/**
 * HashiCorp vault remote parameter
 *
 * **Example.**
 * Adds a new vault parameter with the name `test` that will fetch a value from the KV 1.0 vault named `secret` from the path `path/to`, with the key `key`.
 * The connection namespace to use it is `connection-1`
 * ```
 * hashiCorpVaultParameter {
 *   name = "test"
 *   query = "secret/path/to!/key"
 *   vaultId = "connection-1"
 * }
 * ```
 *
 * **Example.**
 * Adds a new vault parameter with the name `test` that will fetch a value from the KV 2.0 vault named `secret` from the path `path/to`, with the key `key`.
 * The connection namespace to use it is `connection-1`
 * ```
 * hashiCorpVaultParameter {
 *   name = "test"
 *   query = "secret/data/path/to!/key"
 *   vaultId = "connection-1"
 * }
 * ```
 *
 *
 * @see hashiCorpVaultParameter
 */
open class HashiCorpVaultParameter() : RemoteParameter() {

    init {
        type = "hashicorp-vault"
    }

    constructor(init: HashiCorpVaultParameter.() -> Unit): this() {
        init()
    }

    /**
     * The query to access the KV vault. It should be written in the `vaultName/path/to!/key` format.
     * Should your vault be a KV 2.0, the query should be in the `vaultName/data/path/to!/key` format.
     */
    var query by stringParameter("teamcity_hashicorp_vault_vaultQuery")

    /**
     * The vault connection namespace id that should be used to access this secret
     */
    @Deprecated("Use vaultId property instead", replaceWith = ReplaceWith("vaultId"))
    var namespace by stringParameter("teamcity_hashicorp_vault_namespace")

    /**
     * The vault connection id that should be used to access this secret
     */
    var vaultId by stringParameter("teamcity_hashicorp_vault_namespace")

    override fun validate(consumer: ErrorConsumer) {
        super.validate(consumer)
        if (query == null && !hasParam("teamcity_hashicorp_vault_vaultQuery")) {
            consumer.consumePropertyError("query", "mandatory 'query' property is not specified")
        }
    }
}


/**
 *
 * **Example.**
 * Adds a new vault parameter with the name `test` that will fetch a value from the KV 1.0 vault named `secret` from the path `path/to`, with the key `key`.
 * The connection namespace to use it is `connection-1`
 * ```
 * hashiCorpVaultParameter {
 *   name = "test"
 *   query = "secret/path/to!/key"
 *   vaultId = "connection-1"
 * }
 * ```
 *
 * **Example.**
 * Adds a new vault parameter with the name `test` that will fetch a value from the KV 2.0 vault named `secret` from the path `path/to`, with the key `key`.
 * The connection namespace to use it is `connection-1`
 * ```
 * hashiCorpVaultParameter {
 *   name = "test"
 *   query = "secret/data/path/to!/key"
 *   vaultId = "connection-1"
 * }
 * ```
 *
 *
 * @see HashiCorpVaultParameter
 */
fun ParametrizedWithType.hashiCorpVaultParameter(init: HashiCorpVaultParameter.() -> Unit): HashiCorpVaultParameter {
    val result = HashiCorpVaultParameter(init)
    remote(result)
    return result
}
