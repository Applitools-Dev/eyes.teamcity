package jetbrains.buildServer.configs.kotlin.v10.projectFeatures

import jetbrains.buildServer.configs.kotlin.v10.*

/**
 * Project feature for Azure Devops or VSTS connection settings
 *
 * **Example.**
 * It is not recommended to store secure values such as access tokens directly in the DSL code,
 * see [Managing Tokens](https://www.jetbrains.com/help/teamcity/storing-project-settings-in-version-control.html#Managing+Tokens)
 * section of our documentation.
 * ```
 * azureDevopsConnection {
 *   id = "<Connection id>" // arbitrary ID that can be later used to refer to the connection
 *   displayName = "<Connection display name>"
 *   serverUrl = "<Azure DevOps server URL>"
 *   accessToken = "credentialsJSON:*****"
 * }
 * ```
 *
 *
 * @see azureDevopsConnection
 */
open class AzureDevopsConnection : ProjectFeature {
    constructor(init: AzureDevopsConnection.() -> Unit = {}, base: AzureDevopsConnection? = null): super(base = base as ProjectFeature?) {
        type = "OAuthProvider"
        param("providerType", "tfs")
        param("type", "token")
        init()
    }

    /**
     * Human friendly connection name
     */
    var displayName by stringParameter()

    /**
     * Azure DevOps or VSTS server URL, for example:
     * https://dev.azure.com/<organization> (Azure Devops) or https://<account>.visualstudio.com (VSTS)
     */
    var serverUrl by stringParameter()

    /**
     * Access token
     */
    var accessToken by stringParameter("secure:accessToken")

}


/**
 * Creates an Azure Devops/VSTS connection in the current project
 *
 * **Example.**
 * It is not recommended to store secure values such as access tokens directly in the DSL code,
 * see [Managing Tokens](https://www.jetbrains.com/help/teamcity/storing-project-settings-in-version-control.html#Managing+Tokens)
 * section of our documentation.
 * ```
 * azureDevopsConnection {
 *   id = "<Connection id>" // arbitrary ID that can be later used to refer to the connection
 *   displayName = "<Connection display name>"
 *   serverUrl = "<Azure DevOps server URL>"
 *   accessToken = "credentialsJSON:*****"
 * }
 * ```
 *
 *
 * @see AzureDevopsConnection
 */
fun ProjectFeatures.azureDevopsConnection(base: AzureDevopsConnection? = null, init: AzureDevopsConnection.() -> Unit = {}) {
    feature(AzureDevopsConnection(init, base))
}
