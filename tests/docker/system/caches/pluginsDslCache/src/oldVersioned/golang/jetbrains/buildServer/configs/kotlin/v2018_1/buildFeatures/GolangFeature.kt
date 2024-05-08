package jetbrains.buildServer.configs.kotlin.v2018_1.buildFeatures

import jetbrains.buildServer.configs.kotlin.v2018_1.*

/**
 * [Golang feature](https://www.jetbrains.com/help/teamcity/?Golang)
 * processing Golang tests.
 * Please note that [additional build step setup](https://www.jetbrains.com/help/teamcity/?Golang)
 * for this build feature is required.
 *
 * **Example.**
 * Adds a [Golang build feature](https://www.jetbrains.com/help/teamcity/?Golang) for test reporting.
 * Note that additional `-json` command line argument is specified for tests run.
 * ```
 * object GoLandBuild : BuildType({
 *     name = "GoLand Build"
 *
 *     steps {
 *         script {
 *             name = "Run Tests"
 *             scriptContent = "go test -json"
 *         }
 *     }
 *
 *     features {
 *         golang {
 *             testFormat = "json"
 *         }
 *     }
 * })
 * ```
 *
 * **Example.**
 * Adds a [Golang build feature](https://www.jetbrains.com/help/teamcity/?Golang) for test reporting.
 * Note that additional [environment variable](https://www.jetbrains.com/help/teamcity/?Using+Build+Parameters)
 * `GOFLAGS` value `-json` is specified for tests run.
 * ```
 * object GoLandBuild : BuildType({
 *     name = "GoLand Build"
 *
 *     params {
 *         param("env.GOFLAGS", "-json")
 *     }
 *
 *     steps {
 *         script {
 *             name = "Run Tests"
 *             scriptContent = "go test"
 *         }
 *     }
 *
 *     features {
 *         golang {
 *             testFormat = "json"
 *         }
 *     }
 * })
 * ```
 *
 *
 * @see golang
 */
open class GolangFeature() : BuildFeature() {

    init {
        type = "golang"
    }

    constructor(init: GolangFeature.() -> Unit): this() {
        init()
    }

    /**
     * The output format of the test Golang to processing
     */
    var testFormat by stringParameter("test.format")

    override fun validate(consumer: ErrorConsumer) {
        super.validate(consumer)
        if (testFormat == null && !hasParam("test.format")) {
            consumer.consumePropertyError("testFormat", "mandatory 'testFormat' property is not specified")
        }
    }
}


/**
 *
 * **Example.**
 * Adds a [Golang build feature](https://www.jetbrains.com/help/teamcity/?Golang) for test reporting.
 * Note that additional `-json` command line argument is specified for tests run.
 * ```
 * object GoLandBuild : BuildType({
 *     name = "GoLand Build"
 *
 *     steps {
 *         script {
 *             name = "Run Tests"
 *             scriptContent = "go test -json"
 *         }
 *     }
 *
 *     features {
 *         golang {
 *             testFormat = "json"
 *         }
 *     }
 * })
 * ```
 *
 * **Example.**
 * Adds a [Golang build feature](https://www.jetbrains.com/help/teamcity/?Golang) for test reporting.
 * Note that additional [environment variable](https://www.jetbrains.com/help/teamcity/?Using+Build+Parameters)
 * `GOFLAGS` value `-json` is specified for tests run.
 * ```
 * object GoLandBuild : BuildType({
 *     name = "GoLand Build"
 *
 *     params {
 *         param("env.GOFLAGS", "-json")
 *     }
 *
 *     steps {
 *         script {
 *             name = "Run Tests"
 *             scriptContent = "go test"
 *         }
 *     }
 *
 *     features {
 *         golang {
 *             testFormat = "json"
 *         }
 *     }
 * })
 * ```
 *
 *
 * @see golang
 */
fun BuildFeatures.golang(init: GolangFeature.() -> Unit): GolangFeature {
    val result = GolangFeature(init)
    feature(result)
    return result
}
