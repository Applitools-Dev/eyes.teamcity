package jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures

import jetbrains.buildServer.configs.kotlin.v2019_2.*

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
open class NuGetPackagesIndexer() : BuildFeature() {

    init {
        type = "NuGetPackagesIndexer"
    }

    constructor(init: NuGetPackagesIndexer.() -> Unit): this() {
        init()
    }

    /**
     * Specifies target TeamCity NuGet feed to add indexed packages in the following format:
     * %externalProjectId%/%feedName%
     */
    var feed by stringParameter()

    override fun validate(consumer: ErrorConsumer) {
        super.validate(consumer)
    }
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
fun BuildFeatures.nuGetPackagesIndexer(init: NuGetPackagesIndexer.() -> Unit): NuGetPackagesIndexer {
    val result = NuGetPackagesIndexer(init)
    feature(result)
    return result
}
