package jetbrains.buildServer.configs.kotlin.v2018_1.buildSteps

import jetbrains.buildServer.configs.kotlin.v2018_1.*

/**
 * A [build step](https://www.jetbrains.com/help/teamcity/?Docker+Compose) for docker-compose step.
 *
 * **Example.**
 * Adds a Docker Compose build step with specified Compose YAML file.
 * ```
 * dockerCompose {
 *     name = "My Compose"
 *     file = "my-services.yml"
 * }
 * ```
 *
 * **Example.**
 * Adds a Docker Compose build step with multiple Compose YAML files, separated by space.
 * Option for [explicitly pull images](https://www.jetbrains.com/help/teamcity/?Docker+Compose#Docker+Compose+Settings) is enabled.
 * This step will we [executed only if build status is sucessfull](https://www.jetbrains.com/help/teamcity/?Configuring+Build+Steps#Build+Steps+Execution)
 * for previous build steps and extra condition is met.
 * ```
 * dockerCompose {
 *     name = "My Compose"
 *
 *     executionMode = BuildStep.ExecutionMode.RUN_ON_SUCCESS
 *     conditions {
 *         equals("teamcity.build.branch", "release")
 *     }
 *
 *     file = "my-services.yml my-other-services.yml"
 *     forcePull = true
 * }
 * ```
 *
 *
 * @see dockerCompose
 */
open class DockerComposeStep() : BuildStep() {

    init {
        type = "DockerCompose"
    }

    constructor(init: DockerComposeStep.() -> Unit): this() {
        init()
    }

    /**
     * TeamCity will start docker-compose services from the file in this step, and will shut down the services at the end of the build.
     */
    var file by stringParameter("dockerCompose.file")

    /**
     * If enabled, "docker-compose pull" will be run before "docker-compose up" command.
     */
    var forcePull by booleanParameter("dockerCompose.pull", trueValue = "true", falseValue = "")

    override fun validate(consumer: ErrorConsumer) {
        super.validate(consumer)
    }
}


/**
 *
 * **Example.**
 * Adds a Docker Compose build step with specified Compose YAML file.
 * ```
 * dockerCompose {
 *     name = "My Compose"
 *     file = "my-services.yml"
 * }
 * ```
 *
 * **Example.**
 * Adds a Docker Compose build step with multiple Compose YAML files, separated by space.
 * Option for [explicitly pull images](https://www.jetbrains.com/help/teamcity/?Docker+Compose#Docker+Compose+Settings) is enabled.
 * This step will we [executed only if build status is sucessfull](https://www.jetbrains.com/help/teamcity/?Configuring+Build+Steps#Build+Steps+Execution)
 * for previous build steps and extra condition is met.
 * ```
 * dockerCompose {
 *     name = "My Compose"
 *
 *     executionMode = BuildStep.ExecutionMode.RUN_ON_SUCCESS
 *     conditions {
 *         equals("teamcity.build.branch", "release")
 *     }
 *
 *     file = "my-services.yml my-other-services.yml"
 *     forcePull = true
 * }
 * ```
 *
 *
 * @see DockerComposeStep
 */
fun BuildSteps.dockerCompose(init: DockerComposeStep.() -> Unit): DockerComposeStep {
    val result = DockerComposeStep(init)
    step(result)
    return result
}
