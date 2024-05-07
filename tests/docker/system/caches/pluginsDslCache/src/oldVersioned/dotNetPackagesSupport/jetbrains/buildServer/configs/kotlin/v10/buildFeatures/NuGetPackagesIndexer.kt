package jetbrains.buildServer.configs.kotlin.v10.buildFeatures

import jetbrains.buildServer.configs.kotlin.v10.*

/**
 * A [build feature](https://confluence.jetbrains.com/display/TCDL/NuGet) to index *.nupkg files in
 * build artifacts into TeamCity NuGet Feed.
 *
 * **Example.**
 * Enables [NuGet packages indexing](https://confluence.jetbrains.com/display/TCDL/NuGet) for feed NugetFeed/MyFeed
 * ```
 * nuGetPackagesIndexer {
 *     feed = "NugetFeed/MyFeed"
 * }
 * ```
 *
 *
 * @see nuGetPackagesIndexer
 */
open class NuGetPackagesIndexer : BuildFeature {
    constructor(init: NuGetPackagesIndexer.() -> Unit = {}, base: NuGetPackagesIndexer? = null): super(base = base as BuildFeature?) {
        type = "NuGetPackagesIndexer"
        init()
    }

    /**
     * Specifies target TeamCity NuGet feed to add indexed packages in the following format:
     * %externalProjectId%/%feedName%
     */
    var feed by stringParameter()

}


/**
 * Enables [NuGet packages indexing](https://confluence.jetbrains.com/display/TCDL/NuGet) into feed
 *
 * **Example.**
 * Enables [NuGet packages indexing](https://confluence.jetbrains.com/display/TCDL/NuGet) for feed NugetFeed/MyFeed
 * ```
 * nuGetPackagesIndexer {
 *     feed = "NugetFeed/MyFeed"
 * }
 * ```
 *
 *
 * @see NuGetPackagesIndexer
 */
fun BuildFeatures.nuGetPackagesIndexer(base: NuGetPackagesIndexer? = null, init: NuGetPackagesIndexer.() -> Unit = {}) {
    feature(NuGetPackagesIndexer(init, base))
}
