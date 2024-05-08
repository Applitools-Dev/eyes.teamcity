package jetbrains.buildServer.configs.kotlin.v2017_2.buildFeatures

import jetbrains.buildServer.configs.kotlin.v2017_2.*

/**
 * A [build feature](https://www.jetbrains.com/help/teamcity/?Pull+Requests) that introduces GitHub pull requests integration
 *
 * **Example.**
 * Enables watching for JetBrains Space merge requests.
 * The target branch filter applies patterns similar to branch specifications to target branches limiting which merge requests are watched.
 * ```
 * pullRequests {
 *     vcsRootExtId = "${<VCS root object>.id}" // optional, if omitted uses the first attached git VCS root
 *     provider = jetbrainsSpace {
 *         authType = connection {
 *             connectionId = "<JetBrains Space connection id>"
 *         }
 *         filterTargetBranch = "<target branch filter>" // optional
 *     }
 * }
 * ```
 *
 * **Example.**
 * Enables watching for GitHub.com and GitHub Enterprise pull requests.
 * The source and target branch filters apply patterns similar to branch specifications to source and target branches respectively limiting which pull requests are watched.
 * The author role filter filters pull requests by the role of the author in respect to the GitHub organisation where the repository is hosted.
 * The `ignoreDrafts` setting enables optionally ignoring pull requests marked as draft.
 * It is not recommended to store secure values directly in the DSL code,
 * see [Managing Tokens](https://www.jetbrains.com/help/teamcity/storing-project-settings-in-version-control.html#Managing+Tokens)
 * section of our documentation.
 * ```
 * pullRequests {
 *     vcsRootExtId = "${<VCS root object>.id}" // optional, if omitted uses the first attached git VCS root
 *     provider = github {
 *         serverUrl = "<GitHub server URL>" // optional, if omitted will be guessed by the VCS root fetch URL
 *         authType = token {  // authType = vcsRoot() to take credentials from the VCS root
 *             token = "credentialsJSON:*****"
 *         }
 *         filterSourceBranch = "<source branch filter>" // optional
 *         filterTargetBranch = "<target branch filter>" // optional
 *         filterAuthorRole = PullRequests.GitHubRoleFilter.MEMBER // optional
 *         ignoreDrafts = false // optional
 *     }
 * }
 * ```
 *
 * **Example.**
 * Enables watching for GitHub.com and GitHub Enterprise pull requests.
 * Uses the credentials of the VCS root.
 * ```
 * pullRequests {
 *     vcsRootExtId = "${<VCS root object>.id}" // optional, if omitted uses the first attached git VCS root
 *     provider = github {
 *         serverUrl = "<GitHub server URL>" // optional, if omitted will be guessed by the VCS root fetch URL
 *         authType = vcsRoot()
 *         filterSourceBranch = "<source branch filter>" // optional
 *         filterTargetBranch = "<target branch filter>" // optional
 *         filterAuthorRole = PullRequests.GitHubRoleFilter.MEMBER // optional
 *         ignoreDrafts = false // optional
 *     }
 * }
 * ```
 *
 * **Example.**
 * Enables watching for GitHub.com and GitHub Enterprise pull requests.
 * Uses a GitHub App installation token stored in TeamCity.
 * ```
 * pullRequests {
 *     vcsRootExtId = "${<VCS root object>.id}" // A VCS root must be specified to use this authentication type
 *     provider = github {
 *         serverUrl = "<GitHub server URL>" // optional, if omitted will be guessed by the VCS root fetch URL
 *         authType = storedToken {
 *             tokenId = "tc_token_id:*****"
 *         }
 *         filterSourceBranch = "<source branch filter>" // optional
 *         filterTargetBranch = "<target branch filter>" // optional
 *         filterAuthorRole = PullRequests.GitHubRoleFilter.MEMBER // optional
 *         ignoreDrafts = false // optional
 *     }
 * }
 * ```
 *
 * **Example.**
 * Enables watching for GitLab.com or GitLab CE/EE merge requests.
 * The source and target branch filters apply patterns similar to branch specifications to source and target branches respectively limiting which merge requests are watched.
 * It is not recommended to store secure values directly in the DSL code,
 * see [Managing Tokens](https://www.jetbrains.com/help/teamcity/storing-project-settings-in-version-control.html#Managing+Tokens)
 * section of our documentation.
 * ```
 * pullRequests {
 *     vcsRootExtId = "${<VCS root object>.id}" // optional, if omitted selects the first attached git VCS root as a source of merge requests
 *     provider = gitlab {
 *         serverUrl = "<GitLab server URL>" // optional, if omitted will be guessed by the VCS root fetch URL
 *         authType = token {                // authType = vcsRoot() to take credentials from the VCS root
 *             token = "credentialsJSON:*****"
 *         }
 *         filterSourceBranch = "<source branch filter>" // optional
 *         filterTargetBranch = "<target branch filter>" // optional
 *         ignoreDrafts = true // optional
 *     }
 * }
 * ```
 *
 * **Example.**
 * Enables watching for GitLab.com or GitLab CE/EE merge requests.
 * Instead of personal token authentication a refreshable access token stored in TeamCity is used.
 * ```
 * pullRequests {
 *     vcsRootExtId = "${<VCS root object>.id}" // optional, if omitted selects the first attached git VCS root as a source of merge requests
 *     provider = gitlab {
 *         serverUrl = "<GitLab server URL>" // optional, if omitted will be guessed by the VCS root fetch URL
 *         authType = storedToken {                // authType = vcsRoot() to take credentials from the VCS root
 *             tokenId = "tc_token_id:*****"
 *         }
 *         filterSourceBranch = "<source branch filter>" // optional
 *         filterTargetBranch = "<target branch filter>" // optional
 *     }
 * }
 * ```
 *
 * **Example.**
 * Enables watching for Bitbucket Cloud pull requests.
 * The target branch filters applies patterns similar to branch specifications to target branches limiting which pull requests are watched.
 * It is not recommended to store secure values directly in the DSL code,
 * see [Managing Tokens](https://www.jetbrains.com/help/teamcity/storing-project-settings-in-version-control.html#Managing+Tokens)
 * section of our documentation.
 * ```
 * pullRequests {
 *     vcsRootExtId = "${<VCS root object>.id}" // optional, if omitted uses the first attached git VCS root
 *     provider = bitbucketCloud {
 *         authType = password { // authType = vcsRoot() to take credentials from the VCS root
 *             username = "<username>"
 *             password = "credentialsJSON:*****"
 *         }
 *         filterTargetBranch = "<target branch filter>" // optional
 *     }
 * }
 * ```
 *
 * **Example.**
 * Enables watching for Bitbucket Cloud pull requests.
 * You can also use Repository/Project/Workspace access token authentication.
 * ```
 * pullRequests {
 *     vcsRootExtId = "${<VCS root object>.id}" // optional, if omitted uses the first attached git VCS root
 *         provider = bitbucketCloud {
 *         authType = token {
 *             token = "credentialsJSON:*****"
 *         }
 *         filterTargetBranch = "<target branch filter>" // optional
 *     }
 * }
 * ```
 *
 * **Example.**
 * Enables watching for Bitbucket Cloud pull requests.
 * Instead of permanent token authentication a refreshable access token stored in TeamCity is used.
 * ```
 * pullRequests {
 *     vcsRootExtId = "${<VCS root object>.id}" // optional, if omitted uses the first attached git VCS root
 *     provider = bitbucketCloud {
 *         authType = storedToken {
 *             tokenId = "tc_token_id:*****"
 *         }
 *         filterTargetBranch = "<target branch filter>" // optional
 *     }
 * }
 * ```
 *
 * **Example.**
 * Enables watching for Bitbucket Server / Data Center pull requests.
 * The source and target branch filters apply patterns similar to branch specifications to source and target branches respectively limiting which pull requests are watched.
 * If usePullRequestBranches is set to true it makes the build feature watch pull-request specific numerical branches officially unsupported by Bitbucket Server.
 * By default the build feature watches source branches in Bitbucket Server / Data Center.
 * It is not recommended to store secure values directly in the DSL code,
 * see [Managing Tokens](https://www.jetbrains.com/help/teamcity/storing-project-settings-in-version-control.html#Managing+Tokens)
 * section of our documentation.
 * ```
 * pullRequests {
 *     vcsRootExtId = "${<VCS root object>.id}" // optional, if omitted uses the first attached git VCS root
 *     provider = bitbucketServer {
 *         serverUrl = "<Bitbucket Server / Data Center URL>" // optional, if omitted, the URL will be composed based on the VCS root fetch URL.
 *         authType = token { // authType = vcsRoot() to take credentials from the VCS root, or authType = password { ... } to use username and password
 *             token = "credentialsJSON:*****"
 *         }
 *         filterSourceBranch = "<source branch filter>" // optional
 *         filterTargetBranch = "<target branch filter>" // optional
 *         usePullRequestBranches = true // optional
 *     }
 * }
 * ```
 *
 * **Example.**
 * Enables watching for Bitbucket Server / Data Center pull requests.
 * Instead of permanent token authentication a refreshable access token stored in TeamCity is used.
 * ```
 * pullRequests {
 *     provider = bitbucketServer {
 *         authType = storedToken {
 *             tokenId = "tc_token_id:*****"
 *         }
 *     }
 * }
 * ```
 *
 * **Example.**
 * Enables watching for Azure DevOps pull requests.
 * The source and target branch filters apply patterns similar to branch specifications to source and target branches respectively limiting which pull requests are watched.
 * It is not recommended to store secure values directly in the DSL code,
 * see [Managing Tokens](https://www.jetbrains.com/help/teamcity/storing-project-settings-in-version-control.html#Managing+Tokens)
 * section of our documentation.
 * ```
 * pullRequests {
 *     vcsRootExtId = "${<VCS root object>.id}" // optional, if omitted uses the first attached git VCS root
 *     provider = azureDevOps {
 *         projectUrl = "<Azure DevOps project URL>"
 *         authType = token {
 *             token = "credentialsJSON:*****"
 *         }
 *         filterSourceBranch = "<source branch filter>" // optional
 *         filterTargetBranch = "<target branch filter>" // optional
 *     }
 * }
 * ```
 *
 * **Example.**
 * Enables watching for Azure DevOps pull requests.
 * Uses Azure DevOps OAuth 2.0 token stored in TeamCity.
 * ```
 * pullRequests {
 *     vcsRootExtId = "${<VCS root object>.id}" // optional, if omitted uses the first attached git VCS root
 *     provider = azureDevOps {
 *         projectUrl = "<Azure DevOps project URL>"
 *         authType = storedToken {
 *             tokenId = "tc_token_id:*****"
 *         }
 *         filterSourceBranch = "<source branch filter>" // optional
 *         filterTargetBranch = "<target branch filter>" // optional
 *     }
 * }
 * ```
 *
 *
 * @see pullRequests
 */
open class PullRequests() : BuildFeature() {

    init {
        type = "pullRequests"
    }

    constructor(init: PullRequests.() -> Unit): this() {
        init()
    }

    /**
     * Id of the VCS root to extract pull request information from.
     * Set to an empty string to extract pull request information from the first VCS root attached to a build configuration.
     */
    var vcsRootExtId by stringParameter("vcsRootId")

    var provider by compoundParameter<Provider>("providerType")

    sealed class Provider(value: String? = null): CompoundParam<Provider>(value) {
        abstract fun validate(consumer: ErrorConsumer)

        class Github() : Provider("github") {

            /**
             * GitHub/GHE server URL
             */
            var serverUrl by stringParameter()

            /**
             * Type of authentication
             */
            var authType by compoundParameter<AuthType>("authenticationType")

            sealed class AuthType(value: String? = null): CompoundParam<AuthType>(value) {
                abstract fun validate(consumer: ErrorConsumer)

                class VcsRoot() : AuthType("vcsRoot") {

                    override fun validate(consumer: ErrorConsumer) {
                    }
                }

                class Token() : AuthType("token") {

                    /**
                     * Access token to use
                     */
                    var token by stringParameter("secure:accessToken")

                    override fun validate(consumer: ErrorConsumer) {
                        if (token == null && !hasParam("secure:accessToken")) {
                            consumer.consumePropertyError("provider.authType.token", "mandatory 'provider.authType.token' property is not specified")
                        }
                    }
                }

                class StoredToken() : AuthType("storedToken") {

                    /**
                     * Internal ID of a token in TeamCity token storage
                     */
                    var tokenId by stringParameter()

                    override fun validate(consumer: ErrorConsumer) {
                        if (tokenId == null && !hasParam("tokenId")) {
                            consumer.consumePropertyError("provider.authType.tokenId", "mandatory 'provider.authType.tokenId' property is not specified")
                        }
                    }
                }
            }

            /**
             * Use VCS root credentials
             */
            fun vcsRoot() = AuthType.VcsRoot()

            /**
             * Authentication using access token
             */
            fun token(init: AuthType.Token.() -> Unit = {}) : AuthType.Token {
                val result = AuthType.Token()
                result.init()
                return result
            }

            /**
             * Use GitHub App credentials
             */
            fun storedToken(init: AuthType.StoredToken.() -> Unit = {}) : AuthType.StoredToken {
                val result = AuthType.StoredToken()
                result.init()
                return result
            }

            /**
             * Filter by source branch
             */
            var filterSourceBranch by stringParameter()

            /**
             * Filter by target branch
             */
            var filterTargetBranch by stringParameter()

            /**
             * Filter by the role of pull request contributors
             */
            var filterAuthorRole by enumParameter<GitHubRoleFilter>()

            /**
             * Whether to ignore draft pull requests
             */
            var ignoreDrafts by booleanParameter()

            override fun validate(consumer: ErrorConsumer) {
                authType?.validate(consumer)
            }
        }

        class Gitlab() : Provider("gitlab") {

            /**
             * GitLab server URL
             */
            var serverUrl by stringParameter()

            /**
             * Type of authentication
             */
            var authType by compoundParameter<AuthType>("authenticationType")

            sealed class AuthType(value: String? = null): CompoundParam<AuthType>(value) {
                abstract fun validate(consumer: ErrorConsumer)

                class VcsRoot() : AuthType("vcsRoot") {

                    override fun validate(consumer: ErrorConsumer) {
                    }
                }

                class Token() : AuthType("token") {

                    /**
                     * Access token to use
                     */
                    var token by stringParameter("secure:accessToken")

                    override fun validate(consumer: ErrorConsumer) {
                        if (token == null && !hasParam("secure:accessToken")) {
                            consumer.consumePropertyError("provider.authType.token", "mandatory 'provider.authType.token' property is not specified")
                        }
                    }
                }

                class StoredToken() : AuthType("storedToken") {

                    /**
                     * Internal ID of a token in TeamCity token storage
                     */
                    var tokenId by stringParameter()

                    override fun validate(consumer: ErrorConsumer) {
                        if (tokenId == null && !hasParam("tokenId")) {
                            consumer.consumePropertyError("provider.authType.tokenId", "mandatory 'provider.authType.tokenId' property is not specified")
                        }
                    }
                }
            }

            /**
             * Use VCS root credentials
             */
            fun vcsRoot() = AuthType.VcsRoot()

            /**
             * Authentication using access token
             */
            fun token(init: AuthType.Token.() -> Unit = {}) : AuthType.Token {
                val result = AuthType.Token()
                result.init()
                return result
            }

            /**
             * Use GitLab OAuth credentials
             */
            fun storedToken(init: AuthType.StoredToken.() -> Unit = {}) : AuthType.StoredToken {
                val result = AuthType.StoredToken()
                result.init()
                return result
            }

            /**
             * Filter by source branch
             */
            var filterSourceBranch by stringParameter()

            /**
             * Filter by target branch
             */
            var filterTargetBranch by stringParameter()

            /**
             * Whether to ignore draft merge requests
             */
            var ignoreDrafts by booleanParameter()

            override fun validate(consumer: ErrorConsumer) {
                authType?.validate(consumer)
            }
        }

        class BitbucketServer() : Provider("bitbucketServer") {

            /**
             * Bitbucket Server / Data Center server URL
             */
            var serverUrl by stringParameter()

            /**
             * Type of authentication
             */
            var authType by compoundParameter<AuthType>("authenticationType")

            sealed class AuthType(value: String? = null): CompoundParam<AuthType>(value) {
                abstract fun validate(consumer: ErrorConsumer)

                class VcsRoot() : AuthType("vcsRoot") {

                    override fun validate(consumer: ErrorConsumer) {
                    }
                }

                class Password() : AuthType("password") {

                    /**
                     * Username
                     */
                    var username by stringParameter()

                    /**
                     * Password
                     */
                    var password by stringParameter("secure:password")

                    override fun validate(consumer: ErrorConsumer) {
                        if (username == null && !hasParam("username")) {
                            consumer.consumePropertyError("provider.authType.username", "mandatory 'provider.authType.username' property is not specified")
                        }
                        if (password == null && !hasParam("secure:password")) {
                            consumer.consumePropertyError("provider.authType.password", "mandatory 'provider.authType.password' property is not specified")
                        }
                    }
                }

                class Token() : AuthType("token") {

                    /**
                     * Access token to use
                     */
                    var token by stringParameter("secure:accessToken")

                    override fun validate(consumer: ErrorConsumer) {
                        if (token == null && !hasParam("secure:accessToken")) {
                            consumer.consumePropertyError("provider.authType.token", "mandatory 'provider.authType.token' property is not specified")
                        }
                    }
                }

                class StoredToken() : AuthType("storedToken") {

                    /**
                     * Internal ID of a token in TeamCity token storage
                     */
                    var tokenId by stringParameter()

                    override fun validate(consumer: ErrorConsumer) {
                        if (tokenId == null && !hasParam("tokenId")) {
                            consumer.consumePropertyError("provider.authType.tokenId", "mandatory 'provider.authType.tokenId' property is not specified")
                        }
                    }
                }
            }

            /**
             * Use VCS root credentials
             */
            fun vcsRoot() = AuthType.VcsRoot()

            /**
             * Username and password
             */
            fun password(init: AuthType.Password.() -> Unit = {}) : AuthType.Password {
                val result = AuthType.Password()
                result.init()
                return result
            }

            /**
             * Authentication using access token
             */
            fun token(init: AuthType.Token.() -> Unit = {}) : AuthType.Token {
                val result = AuthType.Token()
                result.init()
                return result
            }

            /**
             * Use token credentials stored in TeamCity
             */
            fun storedToken(init: AuthType.StoredToken.() -> Unit = {}) : AuthType.StoredToken {
                val result = AuthType.StoredToken()
                result.init()
                return result
            }

            /**
             * Filter by source branch
             */
            var filterSourceBranch by stringParameter()

            /**
             * Filter by target branch
             */
            var filterTargetBranch by stringParameter()

            /**
             * Enables detection of officially unsupported pull request branches (pull-requests/N) instead of source branches.
             * This option is intended for backward compatibility only. Try to avoid using it.
             * Be careful: new builds might be triggered for changes committed within the last hour after switching.
             */
            var usePullRequestBranches by booleanParameter("useRequestBranches")

            override fun validate(consumer: ErrorConsumer) {
                authType?.validate(consumer)
            }
        }

        class BitbucketCloud() : Provider("bitbucketCloud") {

            /**
             * Type of authentication
             */
            var authType by compoundParameter<AuthType>("authenticationType")

            sealed class AuthType(value: String? = null): CompoundParam<AuthType>(value) {
                abstract fun validate(consumer: ErrorConsumer)

                class VcsRoot() : AuthType("vcsRoot") {

                    override fun validate(consumer: ErrorConsumer) {
                    }
                }

                class Password() : AuthType("password") {

                    /**
                     * Username
                     */
                    var username by stringParameter()

                    /**
                     * Password
                     */
                    var password by stringParameter("secure:password")

                    override fun validate(consumer: ErrorConsumer) {
                        if (username == null && !hasParam("username")) {
                            consumer.consumePropertyError("provider.authType.username", "mandatory 'provider.authType.username' property is not specified")
                        }
                        if (password == null && !hasParam("secure:password")) {
                            consumer.consumePropertyError("provider.authType.password", "mandatory 'provider.authType.password' property is not specified")
                        }
                    }
                }

                class StoredToken() : AuthType("storedToken") {

                    /**
                     * Internal ID of a token in TeamCity token storage
                     */
                    var tokenId by stringParameter()

                    override fun validate(consumer: ErrorConsumer) {
                        if (tokenId == null && !hasParam("tokenId")) {
                            consumer.consumePropertyError("provider.authType.tokenId", "mandatory 'provider.authType.tokenId' property is not specified")
                        }
                    }
                }

                class Token() : AuthType("token") {

                    /**
                     * Access token to use
                     */
                    var token by stringParameter("secure:accessToken")

                    override fun validate(consumer: ErrorConsumer) {
                        if (token == null && !hasParam("secure:accessToken")) {
                            consumer.consumePropertyError("provider.authType.token", "mandatory 'provider.authType.token' property is not specified")
                        }
                    }
                }
            }

            /**
             * Use VCS root credentials
             */
            fun vcsRoot() = AuthType.VcsRoot()

            /**
             * Username and password
             */
            fun password(init: AuthType.Password.() -> Unit = {}) : AuthType.Password {
                val result = AuthType.Password()
                result.init()
                return result
            }

            /**
             * Use token credentials stored in TeamCity
             */
            fun storedToken(init: AuthType.StoredToken.() -> Unit = {}) : AuthType.StoredToken {
                val result = AuthType.StoredToken()
                result.init()
                return result
            }

            /**
             * Authentication using access token
             */
            fun token(init: AuthType.Token.() -> Unit = {}) : AuthType.Token {
                val result = AuthType.Token()
                result.init()
                return result
            }

            /**
             * Filter by target branch
             */
            var filterTargetBranch by stringParameter()

            override fun validate(consumer: ErrorConsumer) {
                authType?.validate(consumer)
            }
        }

        class AzureDevOps() : Provider("azureDevOps") {

            /**
             * Azure DevOps project page URL
             */
            var projectUrl by stringParameter()

            /**
             * Type of authentication
             */
            var authType by compoundParameter<AuthType>("authenticationType")

            sealed class AuthType(value: String? = null): CompoundParam<AuthType>(value) {
                abstract fun validate(consumer: ErrorConsumer)

                class Token() : AuthType("token") {

                    /**
                     * Access token to use
                     */
                    var token by stringParameter("secure:accessToken")

                    override fun validate(consumer: ErrorConsumer) {
                        if (token == null && !hasParam("secure:accessToken")) {
                            consumer.consumePropertyError("provider.authType.token", "mandatory 'provider.authType.token' property is not specified")
                        }
                    }
                }

                class StoredToken() : AuthType("storedToken") {

                    /**
                     * Internal ID of a token in TeamCity token storage
                     */
                    var tokenId by stringParameter()

                    override fun validate(consumer: ErrorConsumer) {
                        if (tokenId == null && !hasParam("tokenId")) {
                            consumer.consumePropertyError("provider.authType.tokenId", "mandatory 'provider.authType.tokenId' property is not specified")
                        }
                    }
                }
            }

            /**
             * Authentication using access token
             */
            fun token(init: AuthType.Token.() -> Unit = {}) : AuthType.Token {
                val result = AuthType.Token()
                result.init()
                return result
            }

            /**
             * Use Azure DevOps OAuth 2.0 credentials
             */
            fun storedToken(init: AuthType.StoredToken.() -> Unit = {}) : AuthType.StoredToken {
                val result = AuthType.StoredToken()
                result.init()
                return result
            }

            /**
             * Filter by source branch
             */
            var filterSourceBranch by stringParameter()

            /**
             * Filter by target branch
             */
            var filterTargetBranch by stringParameter()

            override fun validate(consumer: ErrorConsumer) {
                authType?.validate(consumer)
            }
        }

        class JetbrainsSpace() : Provider("jetbrainsSpace") {

            /**
             * Filter by target branch
             */
            var filterTargetBranch by stringParameter()

            /**
             * Type of authentication
             */
            var authType by compoundParameter<AuthType>("spaceCredentialsType")

            sealed class AuthType(value: String? = null): CompoundParam<AuthType>(value) {
                abstract fun validate(consumer: ErrorConsumer)

                class Connection() : AuthType("spaceCredentialsConnection") {

                    /**
                     * JetBrains Space Connection project feature ID
                     */
                    var connectionId by stringParameter("spaceConnectionId")

                    override fun validate(consumer: ErrorConsumer) {
                        if (connectionId == null && !hasParam("spaceConnectionId")) {
                            consumer.consumePropertyError("provider.authType.connectionId", "mandatory 'provider.authType.connectionId' property is not specified")
                        }
                    }
                }
            }

            /**
             * Authentication using JetBrains Space Connection
             */
            fun connection(init: AuthType.Connection.() -> Unit = {}) : AuthType.Connection {
                val result = AuthType.Connection()
                result.init()
                return result
            }

            override fun validate(consumer: ErrorConsumer) {
                authType?.validate(consumer)
            }
        }
    }

    /**
     * GitHub or GitHub Enterprise
     */
    fun github(init: Provider.Github.() -> Unit = {}) : Provider.Github {
        val result = Provider.Github()
        result.init()
        return result
    }

    /**
     * GitLab.com or GitLab CE/EE
     */
    fun gitlab(init: Provider.Gitlab.() -> Unit = {}) : Provider.Gitlab {
        val result = Provider.Gitlab()
        result.init()
        return result
    }

    /**
     * Bitbucket Server / Data Center
     */
    fun bitbucketServer(init: Provider.BitbucketServer.() -> Unit = {}) : Provider.BitbucketServer {
        val result = Provider.BitbucketServer()
        result.init()
        return result
    }

    /**
     * Bitbucket Cloud
     */
    fun bitbucketCloud(init: Provider.BitbucketCloud.() -> Unit = {}) : Provider.BitbucketCloud {
        val result = Provider.BitbucketCloud()
        result.init()
        return result
    }

    /**
     * Azure DevOps Services/Server
     */
    fun azureDevOps(init: Provider.AzureDevOps.() -> Unit = {}) : Provider.AzureDevOps {
        val result = Provider.AzureDevOps()
        result.init()
        return result
    }

    /**
     * JetBrains Space
     */
    fun jetbrainsSpace(init: Provider.JetbrainsSpace.() -> Unit = {}) : Provider.JetbrainsSpace {
        val result = Provider.JetbrainsSpace()
        result.init()
        return result
    }

    /**
     * Pull request contributor role filter options
     */
    enum class GitHubRoleFilter {
        MEMBER,
        MEMBER_OR_COLLABORATOR,
        EVERYBODY;

    }
    override fun validate(consumer: ErrorConsumer) {
        super.validate(consumer)
        if (provider == null && !hasParam("providerType")) {
            consumer.consumePropertyError("provider", "mandatory 'provider' property is not specified")
        }
        provider?.validate(consumer)
    }
}


/**
 * Enables [pull requests integration](https://www.jetbrains.com/help/teamcity/?Pull+Requests)
 *
 * **Example.**
 * Enables watching for JetBrains Space merge requests.
 * The target branch filter applies patterns similar to branch specifications to target branches limiting which merge requests are watched.
 * ```
 * pullRequests {
 *     vcsRootExtId = "${<VCS root object>.id}" // optional, if omitted uses the first attached git VCS root
 *     provider = jetbrainsSpace {
 *         authType = connection {
 *             connectionId = "<JetBrains Space connection id>"
 *         }
 *         filterTargetBranch = "<target branch filter>" // optional
 *     }
 * }
 * ```
 *
 * **Example.**
 * Enables watching for GitHub.com and GitHub Enterprise pull requests.
 * The source and target branch filters apply patterns similar to branch specifications to source and target branches respectively limiting which pull requests are watched.
 * The author role filter filters pull requests by the role of the author in respect to the GitHub organisation where the repository is hosted.
 * The `ignoreDrafts` setting enables optionally ignoring pull requests marked as draft.
 * It is not recommended to store secure values directly in the DSL code,
 * see [Managing Tokens](https://www.jetbrains.com/help/teamcity/storing-project-settings-in-version-control.html#Managing+Tokens)
 * section of our documentation.
 * ```
 * pullRequests {
 *     vcsRootExtId = "${<VCS root object>.id}" // optional, if omitted uses the first attached git VCS root
 *     provider = github {
 *         serverUrl = "<GitHub server URL>" // optional, if omitted will be guessed by the VCS root fetch URL
 *         authType = token {  // authType = vcsRoot() to take credentials from the VCS root
 *             token = "credentialsJSON:*****"
 *         }
 *         filterSourceBranch = "<source branch filter>" // optional
 *         filterTargetBranch = "<target branch filter>" // optional
 *         filterAuthorRole = PullRequests.GitHubRoleFilter.MEMBER // optional
 *         ignoreDrafts = false // optional
 *     }
 * }
 * ```
 *
 * **Example.**
 * Enables watching for GitHub.com and GitHub Enterprise pull requests.
 * Uses the credentials of the VCS root.
 * ```
 * pullRequests {
 *     vcsRootExtId = "${<VCS root object>.id}" // optional, if omitted uses the first attached git VCS root
 *     provider = github {
 *         serverUrl = "<GitHub server URL>" // optional, if omitted will be guessed by the VCS root fetch URL
 *         authType = vcsRoot()
 *         filterSourceBranch = "<source branch filter>" // optional
 *         filterTargetBranch = "<target branch filter>" // optional
 *         filterAuthorRole = PullRequests.GitHubRoleFilter.MEMBER // optional
 *         ignoreDrafts = false // optional
 *     }
 * }
 * ```
 *
 * **Example.**
 * Enables watching for GitHub.com and GitHub Enterprise pull requests.
 * Uses a GitHub App installation token stored in TeamCity.
 * ```
 * pullRequests {
 *     vcsRootExtId = "${<VCS root object>.id}" // A VCS root must be specified to use this authentication type
 *     provider = github {
 *         serverUrl = "<GitHub server URL>" // optional, if omitted will be guessed by the VCS root fetch URL
 *         authType = storedToken {
 *             tokenId = "tc_token_id:*****"
 *         }
 *         filterSourceBranch = "<source branch filter>" // optional
 *         filterTargetBranch = "<target branch filter>" // optional
 *         filterAuthorRole = PullRequests.GitHubRoleFilter.MEMBER // optional
 *         ignoreDrafts = false // optional
 *     }
 * }
 * ```
 *
 * **Example.**
 * Enables watching for GitLab.com or GitLab CE/EE merge requests.
 * The source and target branch filters apply patterns similar to branch specifications to source and target branches respectively limiting which merge requests are watched.
 * It is not recommended to store secure values directly in the DSL code,
 * see [Managing Tokens](https://www.jetbrains.com/help/teamcity/storing-project-settings-in-version-control.html#Managing+Tokens)
 * section of our documentation.
 * ```
 * pullRequests {
 *     vcsRootExtId = "${<VCS root object>.id}" // optional, if omitted selects the first attached git VCS root as a source of merge requests
 *     provider = gitlab {
 *         serverUrl = "<GitLab server URL>" // optional, if omitted will be guessed by the VCS root fetch URL
 *         authType = token {                // authType = vcsRoot() to take credentials from the VCS root
 *             token = "credentialsJSON:*****"
 *         }
 *         filterSourceBranch = "<source branch filter>" // optional
 *         filterTargetBranch = "<target branch filter>" // optional
 *         ignoreDrafts = true // optional
 *     }
 * }
 * ```
 *
 * **Example.**
 * Enables watching for GitLab.com or GitLab CE/EE merge requests.
 * Instead of personal token authentication a refreshable access token stored in TeamCity is used.
 * ```
 * pullRequests {
 *     vcsRootExtId = "${<VCS root object>.id}" // optional, if omitted selects the first attached git VCS root as a source of merge requests
 *     provider = gitlab {
 *         serverUrl = "<GitLab server URL>" // optional, if omitted will be guessed by the VCS root fetch URL
 *         authType = storedToken {                // authType = vcsRoot() to take credentials from the VCS root
 *             tokenId = "tc_token_id:*****"
 *         }
 *         filterSourceBranch = "<source branch filter>" // optional
 *         filterTargetBranch = "<target branch filter>" // optional
 *     }
 * }
 * ```
 *
 * **Example.**
 * Enables watching for Bitbucket Cloud pull requests.
 * The target branch filters applies patterns similar to branch specifications to target branches limiting which pull requests are watched.
 * It is not recommended to store secure values directly in the DSL code,
 * see [Managing Tokens](https://www.jetbrains.com/help/teamcity/storing-project-settings-in-version-control.html#Managing+Tokens)
 * section of our documentation.
 * ```
 * pullRequests {
 *     vcsRootExtId = "${<VCS root object>.id}" // optional, if omitted uses the first attached git VCS root
 *     provider = bitbucketCloud {
 *         authType = password { // authType = vcsRoot() to take credentials from the VCS root
 *             username = "<username>"
 *             password = "credentialsJSON:*****"
 *         }
 *         filterTargetBranch = "<target branch filter>" // optional
 *     }
 * }
 * ```
 *
 * **Example.**
 * Enables watching for Bitbucket Cloud pull requests.
 * You can also use Repository/Project/Workspace access token authentication.
 * ```
 * pullRequests {
 *     vcsRootExtId = "${<VCS root object>.id}" // optional, if omitted uses the first attached git VCS root
 *         provider = bitbucketCloud {
 *         authType = token {
 *             token = "credentialsJSON:*****"
 *         }
 *         filterTargetBranch = "<target branch filter>" // optional
 *     }
 * }
 * ```
 *
 * **Example.**
 * Enables watching for Bitbucket Cloud pull requests.
 * Instead of permanent token authentication a refreshable access token stored in TeamCity is used.
 * ```
 * pullRequests {
 *     vcsRootExtId = "${<VCS root object>.id}" // optional, if omitted uses the first attached git VCS root
 *     provider = bitbucketCloud {
 *         authType = storedToken {
 *             tokenId = "tc_token_id:*****"
 *         }
 *         filterTargetBranch = "<target branch filter>" // optional
 *     }
 * }
 * ```
 *
 * **Example.**
 * Enables watching for Bitbucket Server / Data Center pull requests.
 * The source and target branch filters apply patterns similar to branch specifications to source and target branches respectively limiting which pull requests are watched.
 * If usePullRequestBranches is set to true it makes the build feature watch pull-request specific numerical branches officially unsupported by Bitbucket Server.
 * By default the build feature watches source branches in Bitbucket Server / Data Center.
 * It is not recommended to store secure values directly in the DSL code,
 * see [Managing Tokens](https://www.jetbrains.com/help/teamcity/storing-project-settings-in-version-control.html#Managing+Tokens)
 * section of our documentation.
 * ```
 * pullRequests {
 *     vcsRootExtId = "${<VCS root object>.id}" // optional, if omitted uses the first attached git VCS root
 *     provider = bitbucketServer {
 *         serverUrl = "<Bitbucket Server / Data Center URL>" // optional, if omitted, the URL will be composed based on the VCS root fetch URL.
 *         authType = token { // authType = vcsRoot() to take credentials from the VCS root, or authType = password { ... } to use username and password
 *             token = "credentialsJSON:*****"
 *         }
 *         filterSourceBranch = "<source branch filter>" // optional
 *         filterTargetBranch = "<target branch filter>" // optional
 *         usePullRequestBranches = true // optional
 *     }
 * }
 * ```
 *
 * **Example.**
 * Enables watching for Bitbucket Server / Data Center pull requests.
 * Instead of permanent token authentication a refreshable access token stored in TeamCity is used.
 * ```
 * pullRequests {
 *     provider = bitbucketServer {
 *         authType = storedToken {
 *             tokenId = "tc_token_id:*****"
 *         }
 *     }
 * }
 * ```
 *
 * **Example.**
 * Enables watching for Azure DevOps pull requests.
 * The source and target branch filters apply patterns similar to branch specifications to source and target branches respectively limiting which pull requests are watched.
 * It is not recommended to store secure values directly in the DSL code,
 * see [Managing Tokens](https://www.jetbrains.com/help/teamcity/storing-project-settings-in-version-control.html#Managing+Tokens)
 * section of our documentation.
 * ```
 * pullRequests {
 *     vcsRootExtId = "${<VCS root object>.id}" // optional, if omitted uses the first attached git VCS root
 *     provider = azureDevOps {
 *         projectUrl = "<Azure DevOps project URL>"
 *         authType = token {
 *             token = "credentialsJSON:*****"
 *         }
 *         filterSourceBranch = "<source branch filter>" // optional
 *         filterTargetBranch = "<target branch filter>" // optional
 *     }
 * }
 * ```
 *
 * **Example.**
 * Enables watching for Azure DevOps pull requests.
 * Uses Azure DevOps OAuth 2.0 token stored in TeamCity.
 * ```
 * pullRequests {
 *     vcsRootExtId = "${<VCS root object>.id}" // optional, if omitted uses the first attached git VCS root
 *     provider = azureDevOps {
 *         projectUrl = "<Azure DevOps project URL>"
 *         authType = storedToken {
 *             tokenId = "tc_token_id:*****"
 *         }
 *         filterSourceBranch = "<source branch filter>" // optional
 *         filterTargetBranch = "<target branch filter>" // optional
 *     }
 * }
 * ```
 *
 *
 * @see PullRequests
 */
fun BuildFeatures.pullRequests(init: PullRequests.() -> Unit): PullRequests {
    val result = PullRequests(init)
    feature(result)
    return result
}
