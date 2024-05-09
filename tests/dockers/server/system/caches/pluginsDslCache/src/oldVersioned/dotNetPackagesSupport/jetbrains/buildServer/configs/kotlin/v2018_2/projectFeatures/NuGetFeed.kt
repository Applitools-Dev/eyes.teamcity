package jetbrains.buildServer.configs.kotlin.v2018_2.projectFeatures

import jetbrains.buildServer.configs.kotlin.v2018_2.*

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
open class NuGetFeed() : ProjectFeature() {

    init {
        type = "PackageRepository"
        param("type", "nuget")
    }

    constructor(init: NuGetFeed.() -> Unit): this() {
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

    override fun validate(consumer: ErrorConsumer) {
        super.validate(consumer)
    }
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
fun ProjectFeatures.nuGetFeed(init: NuGetFeed.() -> Unit): NuGetFeed {
    val result = NuGetFeed(init)
    feature(result)
    return result
}
