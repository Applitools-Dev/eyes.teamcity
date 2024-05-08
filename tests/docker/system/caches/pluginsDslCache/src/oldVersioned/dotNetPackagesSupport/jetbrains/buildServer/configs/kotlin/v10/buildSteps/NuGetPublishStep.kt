package jetbrains.buildServer.configs.kotlin.v10.buildSteps

import jetbrains.buildServer.configs.kotlin.v10.*

/**
 * A [NuGet publish step](https://confluence.jetbrains.com/display/TCDL/NuGet+Publish) to run nuget push command
 *
 * **Example.**
 * Publishes "target/app.nupkg" NuGet package to "https://api.nuget.org/v3/index.json" NuGet feed.
 * Uses the specified API key for the publishing.
 * Note: instead of an actual API key a [token](https://www.jetbrains.com/help/teamcity/storing-project-settings-in-version-control.html#Storing+Secure+Settings) should be provided.
 * ```
 * nuGetPublish {
 *   toolPath = "%teamcity.tool.NuGet.CommandLine.DEFAULT%"
 *   packages = "target/app.nupkg"
 *   serverUrl = "https://api.nuget.org/v3/index.json"
 *   apiKey = "<a token for an API key>"
 * }
 * ```
 *
 *
 * @see nuGetPublish
 */
open class NuGetPublishStep : BuildStep {
    constructor(init: NuGetPublishStep.() -> Unit = {}, base: NuGetPublishStep? = null): super(base = base as BuildStep?) {
        type = "jb.nuget.publish"
        init()
    }

    /**
     * Specify path to NuGet.exe.
     */
    var toolPath by stringParameter("nuget.path")

    /**
     * A newline-separated list of NuGet package files (.nupkg) to push to the NuGet feed.
     */
    var packages by stringParameter("nuget.publish.files")

    /**
     * Specify the NuGet packages feed URL to push packages to.
     */
    var serverUrl by stringParameter("nuget.publish.source")

    /**
     * Specify the API key to access a NuGet packages feed.
     */
    var apiKey by stringParameter("secure:nuget.api.key")

    /**
     * Enter additional parameters to use when calling nuget push command.
     */
    var args by stringParameter("nuget.push.commandline")

}


/**
 * Adds a [NuGet publish step](https://confluence.jetbrains.com/display/TCDL/NuGet+Publish) to run nuget push command
 *
 * **Example.**
 * Publishes "target/app.nupkg" NuGet package to "https://api.nuget.org/v3/index.json" NuGet feed.
 * Uses the specified API key for the publishing.
 * Note: instead of an actual API key a [token](https://www.jetbrains.com/help/teamcity/storing-project-settings-in-version-control.html#Storing+Secure+Settings) should be provided.
 * ```
 * nuGetPublish {
 *   toolPath = "%teamcity.tool.NuGet.CommandLine.DEFAULT%"
 *   packages = "target/app.nupkg"
 *   serverUrl = "https://api.nuget.org/v3/index.json"
 *   apiKey = "<a token for an API key>"
 * }
 * ```
 *
 *
 * @see NuGetPublishStep
 */
fun BuildSteps.nuGetPublish(base: NuGetPublishStep? = null, init: NuGetPublishStep.() -> Unit = {}) {
    step(NuGetPublishStep(init, base))
}
