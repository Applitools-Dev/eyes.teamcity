package jetbrains.buildServer.configs.kotlin.v10.buildSteps

import jetbrains.buildServer.configs.kotlin.v10.*

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
open class DockerComposeStep : BuildStep {
    constructor(init: DockerComposeStep.() -> Unit = {}, base: DockerComposeStep? = null): super(base = base as BuildStep?) {
        type = "DockerCompose"
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
fun BuildSteps.dockerCompose(base: DockerComposeStep? = null, init: DockerComposeStep.() -> Unit = {}) {
    step(DockerComposeStep(init, base))
}
