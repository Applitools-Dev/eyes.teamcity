package jetbrains.buildServer.configs.kotlin.v2017_2.buildFeatures

import jetbrains.buildServer.configs.kotlin.v2017_2.*

/**
 * Provides build cache functionality
 *
 * **Example.**
 * Publish local .m2 directory with downloaded dependencies to the cache with name "maven-cache"
 * ```
 * buildCache {
 *   name = "maven-cache"
 *   publish = true
 *   rules = ".m2"
 * }
 * ```
 *
 * **Example.**
 * Publish a set of files to the cache
 * ```
 * buildCache {
 *   name = "my-cache"
 *   publish = true
 *   rules = """
 *       target/artifact.jar
 *       settings.txt
 *   """.trimIndent()
 * }
 * ```
 *
 * **Example.**
 * Use the given cache
 * ```
 * buildCache {
 *   name = "my-cache"
 *   publish = false
 * }
 * ```
 *
 * **Example.**
 * Publish and use the given cache in the same build. Publish cache only it was changed during the build.
 * ```
 * buildCache {
 *   name = "my-cache"
 *   publish = true
 *   rules = """
 *     target/artifact.jar
 *     settings.txt
 *   """.trimIndent()
 *   publishOnlyChanged = true
 *   use = true
 * }
 * ```
 *
 *
 * @see buildCache
 */
open class BuildCacheFeature() : BuildFeature() {

    init {
        type = "jetbrains.buildserver.feature.build.cache"
    }

    constructor(init: BuildCacheFeature.() -> Unit): this() {
        init()
    }

    /**
     * Name of the cache
     */
    var name by stringParameter("cache-name")

    /**
     * If true, downloads cached files to the agent before the build steps are executed.
     */
    var use by booleanParameter("use-cache", trueValue = "true", falseValue = "")

    /**
     * If true, publishes the files matched by rules to cache.
     */
    var publish by booleanParameter("publish-cache", trueValue = "true", falseValue = "")

    /**
     * If enabled, cache will be published only if it was used and its files were changed during the build.
     */
    var publishOnlyChanged by booleanParameter("publish-only-changed", trueValue = "true", falseValue = "")

    /**
     * Newline-separated list of paths relative to the checkout directory of the files or directories that should be cached. Wildcards are not supported.
     */
    var rules by stringParameter("publish-cache-rules")

    override fun validate(consumer: ErrorConsumer) {
        super.validate(consumer)
        if (name == null && !hasParam("cache-name")) {
            consumer.consumePropertyError("name", "mandatory 'name' property is not specified")
        }
    }
}


/**
 * Adds a build cache to the build
 *
 * **Example.**
 * Publish local .m2 directory with downloaded dependencies to the cache with name "maven-cache"
 * ```
 * buildCache {
 *   name = "maven-cache"
 *   publish = true
 *   rules = ".m2"
 * }
 * ```
 *
 * **Example.**
 * Publish a set of files to the cache
 * ```
 * buildCache {
 *   name = "my-cache"
 *   publish = true
 *   rules = """
 *       target/artifact.jar
 *       settings.txt
 *   """.trimIndent()
 * }
 * ```
 *
 * **Example.**
 * Use the given cache
 * ```
 * buildCache {
 *   name = "my-cache"
 *   publish = false
 * }
 * ```
 *
 * **Example.**
 * Publish and use the given cache in the same build. Publish cache only it was changed during the build.
 * ```
 * buildCache {
 *   name = "my-cache"
 *   publish = true
 *   rules = """
 *     target/artifact.jar
 *     settings.txt
 *   """.trimIndent()
 *   publishOnlyChanged = true
 *   use = true
 * }
 * ```
 *
 *
 * @see BuildCacheFeature
 */
fun BuildFeatures.buildCache(init: BuildCacheFeature.() -> Unit): BuildCacheFeature {
    val result = BuildCacheFeature(init)
    feature(result)
    return result
}
