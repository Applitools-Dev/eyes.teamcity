package jetbrains.buildServer.configs.kotlin.v10.projectFeatures

import jetbrains.buildServer.configs.kotlin.v10.*

/**
 * Project feature enabling [TeamCity NuGet feed](https://confluence.jetbrains.com/display/TCDL/NuGet)
 *
 * **Example.**
 * Defines a NuGet feed in the project. Watches for any NuGet packages published as artifacts of the builds of this project and adds them to the feed.
 * ```
 * nuGetFeed {
 *   id = "repository-nuget-MyProjectFeed"
 *   name = "MyProjectFeed"
 *   description = "A feed for all NuGet packages published as artifacts in my project"
 *   indexPackages = true
 * }
 * ```
 *
 *
 * @see nuGetFeed
 */
open class NuGetFeed : ProjectFeature {
    constructor(init: NuGetFeed.() -> Unit = {}, base: NuGetFeed? = null): super(base = base as ProjectFeature?) {
        type = "PackageRepository"
        param("type", "nuget")
        init()
    }

    /**
     * The feed name
     */
    var name by stringParameter()

    /**
     * The feed description
     */
    var description by stringParameter()

    /**
     * Enables indexing NuGet packages into feed produced by builds in this project and all subprojects
     */
    var indexPackages by booleanParameter(trueValue = "true", falseValue = "")

}


/**
 * Enables [TeamCity NuGet feed](https://confluence.jetbrains.com/display/TCDL/NuGet)
 *
 * **Example.**
 * Defines a NuGet feed in the project. Watches for any NuGet packages published as artifacts of the builds of this project and adds them to the feed.
 * ```
 * nuGetFeed {
 *   id = "repository-nuget-MyProjectFeed"
 *   name = "MyProjectFeed"
 *   description = "A feed for all NuGet packages published as artifacts in my project"
 *   indexPackages = true
 * }
 * ```
 *
 *
 * @see NuGetFeed
 */
fun ProjectFeatures.nuGetFeed(base: NuGetFeed? = null, init: NuGetFeed.() -> Unit = {}) {
    feature(NuGetFeed(init, base))
}
