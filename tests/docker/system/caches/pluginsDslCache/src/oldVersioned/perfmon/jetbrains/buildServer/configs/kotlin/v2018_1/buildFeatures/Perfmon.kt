package jetbrains.buildServer.configs.kotlin.v2018_1.buildFeatures

import jetbrains.buildServer.configs.kotlin.v2018_1.*

/**
 * A [build feature](https://www.jetbrains.com/help/teamcity/?Performance+Monitor) allows you to get the statistics on the CPU, disk and memory usage during a build run on a build agent.
 *
 * **Example.**
 * Adds a [Performance Monitor](https://www.jetbrains.com/help/teamcity/?Performance+Monitor)
 * build feature. It has no additional parameters.
 * ```
 * perfmon { }
 * ```
 *
 *
 * @see perfmon
 */
open class Perfmon() : BuildFeature() {

    init {
        type = "perfmon"
    }

    constructor(init: Perfmon.() -> Unit): this() {
        init()
    }

    override fun validate(consumer: ErrorConsumer) {
        super.validate(consumer)
    }
}


/**
 * Adds a [build feature](https://www.jetbrains.com/help/teamcity/?Performance+Monitor) build feature
 *
 * **Example.**
 * Adds a [Performance Monitor](https://www.jetbrains.com/help/teamcity/?Performance+Monitor)
 * build feature. It has no additional parameters.
 * ```
 * perfmon { }
 * ```
 *
 *
 * @see Perfmon
 */
fun BuildFeatures.perfmon(init: Perfmon.() -> Unit): Perfmon {
    val result = Perfmon(init)
    feature(result)
    return result
}
