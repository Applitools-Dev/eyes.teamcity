package jetbrains.buildServer.configs.kotlin.v2018_1.projectFeatures

import jetbrains.buildServer.configs.kotlin.v2018_1.*

/**
 * This connection is used for some Perforce adminstration tasks on the TeamCity server.
 * See [cleaning workspaces on Perforce Server](https://www.jetbrains.com/help/teamcity/?P4AdminAccessConnection).
 *
 * **Example.**
 * Configure a Perforce admin connection to perforce:1666 with the given username and password (or ticket).
 * ```
 * project {
 *     ...
 *     features {
 *         perforceAdminAccess {
 *             id = "PerforceAdminAccessConnection"
 *             name = "Perforce Administrator Access"
 *             port = "perforce:1666"
 *             userName = "admin"
 *             password = "credentialsJSON:******"
 *         }
 *     }
 * }
 * ```
 *
 *
 * @see perforceAdminAccess
 */
open class PerforceAdminConnection() : ProjectFeature() {

    init {
        type = "OAuthProvider"
        param("providerType", "PerforceAdmin")
    }

    constructor(init: PerforceAdminConnection.() -> Unit): this() {
        init()
    }

    /**
     * Perforce administrator connection display name
     */
    var name by stringParameter("displayName")

    /**
     * A Perforce server address in the "host:port" format
     */
    var port by stringParameter()

    /**
     * A username for Perforce connection
     */
    var userName by stringParameter("user")

    /**
     * A password for Perforce connection
     */
    var password by stringParameter("secure:passwd")

    override fun validate(consumer: ErrorConsumer) {
        super.validate(consumer)
    }
}


/**
 *
 * **Example.**
 * Configure a Perforce admin connection to perforce:1666 with the given username and password (or ticket).
 * ```
 * project {
 *     ...
 *     features {
 *         perforceAdminAccess {
 *             id = "PerforceAdminAccessConnection"
 *             name = "Perforce Administrator Access"
 *             port = "perforce:1666"
 *             userName = "admin"
 *             password = "credentialsJSON:******"
 *         }
 *     }
 * }
 * ```
 *
 *
 * @see PerforceAdminConnection
 */
fun ProjectFeatures.perforceAdminAccess(init: PerforceAdminConnection.() -> Unit): PerforceAdminConnection {
    val result = PerforceAdminConnection(init)
    feature(result)
    return result
}
