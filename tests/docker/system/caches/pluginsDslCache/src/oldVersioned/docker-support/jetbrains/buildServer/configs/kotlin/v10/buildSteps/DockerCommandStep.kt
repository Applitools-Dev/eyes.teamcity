package jetbrains.buildServer.configs.kotlin.v10.buildSteps

import jetbrains.buildServer.configs.kotlin.v10.*

/**
 * A [build step](https://www.jetbrains.com/help/teamcity/docker.html#Docker-DockerCommand) for a generic docker command runner (can run Docker build, push, other...)
 *
 * **Example.**
 * Adds a Docker [Build](https://www.jetbrains.com/help/teamcity/?Docker#Docker+Command) build step
 * with a source as Dockerfile located in the checkout directory with extra Docker command line argument.
 * ```
 * dockerCommand {
 *     name = "My Docker step"
 *     commandType = build {
 *         source = file {
 *             path = "my-docker/Dockerfile"
 *         }
 *         commandArgs = "--pull"
 *     }
 * }
 * ```
 *
 * **Example.**
 * Adds a Docker [Build](https://www.jetbrains.com/help/teamcity/?Docker#Docker+Command) build step
 * with a URL as Dockerfile with extra platform settings and images names and tags provided
 * (using [parameter reference](https://www.jetbrains.com/help/teamcity/?Using+Build+Parameters)).
 * ```
 * dockerCommand {
 *     name = "My Docker step"
 *     commandType = build {
 *         source = url {
 *             url = "https://my.repo.org/files/Dockerfile"
 *         }
 *         platform = DockerCommandStep.ImagePlatform.Linux
 *         namesAndTags = """
 *             imageName:%myimage.version%
 *             otherImage:%myimage.version%
 *         """.trimIndent()
 *     }
 * }
 * ```
 *
 * **Example.**
 * Adds a Docker [Build](https://www.jetbrains.com/help/teamcity/?Docker#Docker+Command) build step
 * with a source as Dockerfile content provided explicitly in the DSL configuration
 * with extra platform settings and images names and tags provided.
 * ```
 * dockerCommand {
 *     name = "My Docker step"
 *
 *     commandType = build {
 *         source = content {
 *             content = """
 *                 Dockerfile content
 *             """.trimIndent()
 *         }
 *         platform = DockerCommandStep.ImagePlatform.Linux
 *         namesAndTags = "myImage:1.0"
 *     }
 * }
 * ```
 *
 * **Example.**
 * Adds a Docker [Push](https://www.jetbrains.com/help/teamcity/?Docker#Docker+Command) build step
 * with images names and tags provided (using [parameter reference](https://www.jetbrains.com/help/teamcity/?Using+Build+Parameters)).
 * Additional property for not removing the image after the step is set up.
 * This step will we [executed only if build status is sucessfull](https://www.jetbrains.com/help/teamcity/?Configuring+Build+Steps#Build+Steps+Execution)
 * for previous build steps and extra condition is met.
 * ```
 * dockerCommand {
 *     name = "My Docker push step"
 *
 *     executionMode = BuildStep.ExecutionMode.RUN_ON_SUCCESS
 *     conditions {
 *         equals("teamcity.build.branch", "release")
 *     }
 *
 *     commandType = push {
 *         namesAndTags = "imageName:%myimage.version%"
 *         removeImageAfterPush = false
 *     }
 * }
 * ```
 *
 * **Example.**
 * Adds a Docker [Other](https://www.jetbrains.com/help/teamcity/?Docker#Docker+Command) build step
 * with a docker command provided and Docker command additional arguments.
 * Custom [working directory](https://www.jetbrains.com/help/teamcity/?Build+Working+Directory) is provided.
 * ```
 * dockerCommand {
 *     name = "My Docker other (tag) step"
 *
 *     commandType = other {
 *         subCommand = "tag"
 *         workingDir = "docker/images"
 *         commandArgs = "source target:%myimage.version%"
 *     }
 * }
 * ```
 *
 *
 * @see dockerCommand
 */
open class DockerCommandStep : BuildStep {
    constructor(init: DockerCommandStep.() -> Unit = {}, base: DockerCommandStep? = null): super(base = base as BuildStep?) {
        type = "DockerCommand"
        init()
    }

    /**
     * Specifies the type of the command, at the moment "build", "push", "other"
     */
    var commandType by compoundParameter<CommandType>("docker.command.type")

    sealed class CommandType(value: String? = null): CompoundParam(value) {
        class Build() : CommandType("build") {

            /**
             * Specifies the source of the Dockerfile
             */
            var source by compoundParameter<Source>("dockerfile.source")

            sealed class Source(value: String? = null): CompoundParam(value) {
                class File() : Source("PATH") {

                    /**
                     * The specified path should be relative to the checkout directory.
                     */
                    var path by stringParameter("dockerfile.path")

                }

                class Path() : Source("PATH") {

                    /**
                     * The specified path should be relative to the checkout directory.
                     */
                    var path by stringParameter("dockerfile.path")

                }

                class Url() : Source("URL") {

                    /**
                     * URL to Dockerfile
                     */
                    var url by stringParameter("dockerfile.url")

                }

                class Content() : Source("CONTENT") {

                    /**
                     * 
                     */
                    var content by stringParameter("dockerfile.content")

                }
            }

            /**
             * Sets filesystem path to Dockerfile
             */
            fun file(init: Source.File.() -> Unit = {}) : Source.File {
                val result = Source.File()
                result.init()
                return result
            }

            /**
             * Sets filesystem path to Dockerfile
             */
            @Deprecated("Use 'file' option instead")
            fun path(init: Source.Path.() -> Unit = {}) : Source.Path {
                val result = Source.Path()
                result.init()
                return result
            }

            /**
             * Sets Dockerfile URL
             */
            fun url(init: Source.Url.() -> Unit = {}) : Source.Url {
                val result = Source.Url()
                result.init()
                return result
            }

            /**
             * Use Dockerfile content
             */
            fun content(init: Source.Content.() -> Unit = {}) : Source.Content {
                val result = Source.Content()
                result.init()
                return result
            }

            /**
             * If blank, the folder containing the Dockerfile will be used.
             */
            var contextDir by stringParameter("dockerfile.contextDir")

            /**
             * Specifies which Docker image platform will be used to run this build step.
             */
            var platform by enumParameter<ImagePlatform>("dockerImage.platform", mapping = ImagePlatform.mapping)

            /**
             * Newline-separated list of the image name:tag(s).
             */
            var namesAndTags by stringParameter("docker.image.namesAndTags")

            /**
             * Additional arguments that will be passed to the command.
             */
            var commandArgs by stringParameter("command.args")

        }

        class Push() : CommandType("push") {

            /**
             * Newline-separated list of the image name:tag(s).
             */
            var namesAndTags by stringParameter("docker.image.namesAndTags")

            /**
             * Remove image from agent after push.
             */
            var removeImageAfterPush by booleanParameter("docker.push.remove.image", trueValue = "true", falseValue = "")

            /**
             * Additional arguments that will be passed to the command.
             */
            var commandArgs by stringParameter("command.args")

        }

        class Other() : CommandType("other") {

            /**
             * Docker sub-command, like 'pull' or 'tag'
             */
            var subCommand by stringParameter("docker.sub.command")

            /**
             * [Build working directory](https://www.jetbrains.com/help/teamcity/?Build+Working+Directory) for the command script,
             * specify it if it is different from the [checkout directory](https://www.jetbrains.com/help/teamcity/?Build+Checkout+Directory).
             */
            var workingDir by stringParameter("teamcity.build.workingDir")

            /**
             * Additional arguments that will be passed to the docker command.
             */
            var commandArgs by stringParameter("command.args")

        }
    }

    /**
     * Run "docker build" command
     */
    fun build(init: CommandType.Build.() -> Unit = {}) : CommandType.Build {
        val result = CommandType.Build()
        result.init()
        return result
    }

    /**
     * Run "docker push" command
     */
    fun push(init: CommandType.Push.() -> Unit = {}) : CommandType.Push {
        val result = CommandType.Push()
        result.init()
        return result
    }

    /**
     * Run a specified docker command
     */
    fun other(init: CommandType.Other.() -> Unit = {}) : CommandType.Other {
        val result = CommandType.Other()
        result.init()
        return result
    }

    /**
     * Docker image platforms
     */
    enum class ImagePlatform {
        Any,
        Linux,
        Windows;

        companion object {
            val mapping = mapOf<ImagePlatform, String>(Any to "", Linux to "linux", Windows to "windows")
        }

    }
}


/**
 *
 * **Example.**
 * Adds a Docker [Build](https://www.jetbrains.com/help/teamcity/?Docker#Docker+Command) build step
 * with a source as Dockerfile located in the checkout directory with extra Docker command line argument.
 * ```
 * dockerCommand {
 *     name = "My Docker step"
 *     commandType = build {
 *         source = file {
 *             path = "my-docker/Dockerfile"
 *         }
 *         commandArgs = "--pull"
 *     }
 * }
 * ```
 *
 * **Example.**
 * Adds a Docker [Build](https://www.jetbrains.com/help/teamcity/?Docker#Docker+Command) build step
 * with a URL as Dockerfile with extra platform settings and images names and tags provided
 * (using [parameter reference](https://www.jetbrains.com/help/teamcity/?Using+Build+Parameters)).
 * ```
 * dockerCommand {
 *     name = "My Docker step"
 *     commandType = build {
 *         source = url {
 *             url = "https://my.repo.org/files/Dockerfile"
 *         }
 *         platform = DockerCommandStep.ImagePlatform.Linux
 *         namesAndTags = """
 *             imageName:%myimage.version%
 *             otherImage:%myimage.version%
 *         """.trimIndent()
 *     }
 * }
 * ```
 *
 * **Example.**
 * Adds a Docker [Build](https://www.jetbrains.com/help/teamcity/?Docker#Docker+Command) build step
 * with a source as Dockerfile content provided explicitly in the DSL configuration
 * with extra platform settings and images names and tags provided.
 * ```
 * dockerCommand {
 *     name = "My Docker step"
 *
 *     commandType = build {
 *         source = content {
 *             content = """
 *                 Dockerfile content
 *             """.trimIndent()
 *         }
 *         platform = DockerCommandStep.ImagePlatform.Linux
 *         namesAndTags = "myImage:1.0"
 *     }
 * }
 * ```
 *
 * **Example.**
 * Adds a Docker [Push](https://www.jetbrains.com/help/teamcity/?Docker#Docker+Command) build step
 * with images names and tags provided (using [parameter reference](https://www.jetbrains.com/help/teamcity/?Using+Build+Parameters)).
 * Additional property for not removing the image after the step is set up.
 * This step will we [executed only if build status is sucessfull](https://www.jetbrains.com/help/teamcity/?Configuring+Build+Steps#Build+Steps+Execution)
 * for previous build steps and extra condition is met.
 * ```
 * dockerCommand {
 *     name = "My Docker push step"
 *
 *     executionMode = BuildStep.ExecutionMode.RUN_ON_SUCCESS
 *     conditions {
 *         equals("teamcity.build.branch", "release")
 *     }
 *
 *     commandType = push {
 *         namesAndTags = "imageName:%myimage.version%"
 *         removeImageAfterPush = false
 *     }
 * }
 * ```
 *
 * **Example.**
 * Adds a Docker [Other](https://www.jetbrains.com/help/teamcity/?Docker#Docker+Command) build step
 * with a docker command provided and Docker command additional arguments.
 * Custom [working directory](https://www.jetbrains.com/help/teamcity/?Build+Working+Directory) is provided.
 * ```
 * dockerCommand {
 *     name = "My Docker other (tag) step"
 *
 *     commandType = other {
 *         subCommand = "tag"
 *         workingDir = "docker/images"
 *         commandArgs = "source target:%myimage.version%"
 *     }
 * }
 * ```
 *
 *
 * @see DockerCommandStep
 */
fun BuildSteps.dockerCommand(base: DockerCommandStep? = null, init: DockerCommandStep.() -> Unit = {}) {
    step(DockerCommandStep(init, base))
}
