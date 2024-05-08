package jetbrains.buildServer.configs.kotlin.v2019_2.triggers

import jetbrains.buildServer.configs.kotlin.v2019_2.*

/**
 * [The NuGet Dependency Trigger](https://www.jetbrains.com/help/teamcity/?NuGet+Dependency+Trigger)
 * allows starting a new build if a NuGet packages update is detected in the NuGet repository.
 * Note that if a custom NuGet executable is used, it must be explicitly allowed on this server.
 *
 * **Example.**
 * Watches for a change of the package with specified id in the specified NuGet feed.
 * Uses the specified credentials to access the feed and the default NuGet executable installed on the TeamCity server.
 * Note: instead of an actual password a [token](https://www.jetbrains.com/help/teamcity/storing-project-settings-in-version-control.html#Storing+Secure+Settings) should be provided.
 * ```
 * nuGetDependency {
 *   nugetPath = "%teamcity.tool.NuGet.CommandLine.DEFAULT%"
 *   feedURL = "<NuGet feed URL>"
 *   packageId = "<NuGet package id>"
 *   username = "user"
 *   password = "<a token representing a password>"
 * }
 * ```
 *
 *
 * @see nuGetDependency
 */
open class NuGetDependency() : Trigger() {

    init {
        type = "nuget.simple"
    }

    constructor(init: NuGetDependency.() -> Unit): this() {
        init()
    }

    /**
     * A custom path to NuGet.exe. Absolute paths are supported.
     * It can reference to Nuget.exe Tool installed via Administration | Tools.
     */
    var nugetPath by stringParameter("nuget.exe")

    /**
     * The NuGet packages feed URL to monitor packages changes.
     * Leave blank to use default NuGet feed.
     */
    var feedURL by stringParameter("nuget.source")

    /**
     * A username to access NuGet feed, leave blank if no authentication is required.
     */
    var username by stringParameter("nuget.username")

    /**
     * A password to access NuGet feed, leave blank if no authentication is required.
     */
    var password by stringParameter("secure:nuget.password")

    /**
     * A Package Id to check for updates.
     */
    var packageId by stringParameter("nuget.package")

    /**
     * Specify package version to check. Leave empty to check for latest version.
     */
    var packageVersion by stringParameter("nuget.version")

    /**
     * Trigger build if pre-release package version is detected.
     */
    var includePrerelease by booleanParameter("nuget.include.prerelease", trueValue = "true", falseValue = "")

    override fun validate(consumer: ErrorConsumer) {
        super.validate(consumer)
        if (nugetPath == null && !hasParam("nuget.exe")) {
            consumer.consumePropertyError("nugetPath", "mandatory 'nugetPath' property is not specified")
        }
        if (packageId == null && !hasParam("nuget.package")) {
            consumer.consumePropertyError("packageId", "mandatory 'packageId' property is not specified")
        }
    }
}


/**
 * Adds [The NuGet Dependency Trigger](https://www.jetbrains.com/help/teamcity/?NuGet+Dependency+Trigger)
 *
 * **Example.**
 * Watches for a change of the package with specified id in the specified NuGet feed.
 * Uses the specified credentials to access the feed and the default NuGet executable installed on the TeamCity server.
 * Note: instead of an actual password a [token](https://www.jetbrains.com/help/teamcity/storing-project-settings-in-version-control.html#Storing+Secure+Settings) should be provided.
 * ```
 * nuGetDependency {
 *   nugetPath = "%teamcity.tool.NuGet.CommandLine.DEFAULT%"
 *   feedURL = "<NuGet feed URL>"
 *   packageId = "<NuGet package id>"
 *   username = "user"
 *   password = "<a token representing a password>"
 * }
 * ```
 *
 *
 * @see NuGetDependency
 */
fun Triggers.nuGetDependency(init: NuGetDependency.() -> Unit): NuGetDependency {
    val result = NuGetDependency(init)
    trigger(result)
    return result
}
