package jetbrains.buildServer.configs.kotlin.v10.buildSteps

import jetbrains.buildServer.configs.kotlin.v10.*

/**
 * A [NuGet installer step](https://confluence.jetbrains.com/display/TCDL/NuGet+Installer) to run nuget restore command
 *
 * **Example.**
 * Installs all NuGet packages referenced in the Visual Studio solution file "my_solution.sln" including the pre-release packages.
 * Uses the default NuGet tool version installed on the TeamCity server.
 * ```
 * nuGetInstaller {
 *   toolPath = "%teamcity.tool.NuGet.CommandLine.DEFAULT%"
 *   projects = "my_solution.sln"
 *   updatePackages = updateParams {
 *     includePreRelease = true
 *   }
 * }
 * ```
 *
 * **Example.**
 * Installs all NuGet packages referenced in the Visual Studio solution file "my_solution.sln".
 * Uses a custom path to the NuGet executable.
 * ```
 * nuGetInstaller {
 *   toolPath = "<path to NuGet executable on agent>"
 *   projects = "my_solution.sln"
 * }
 * ```
 *
 *
 * @see nuGetInstaller
 */
open class NuGetInstallerStep : BuildStep {
    constructor(init: NuGetInstallerStep.() -> Unit = {}, base: NuGetInstallerStep? = null): super(base = base as BuildStep?) {
        type = "jb.nuget.installer"
        init()
    }

    /**
     * Specify path to NuGet.exe.
     */
    var toolPath by stringParameter("nuget.path")

    /**
     * Specify the location of a solution or a packages.config file.
     */
    var projects by stringParameter("sln.path")

    /**
     * Select NuGet.exe restore or NuGet.exe install command to restore packages
     */
    var mode by compoundParameter<Mode>("nuget.use.restore")

    sealed class Mode(value: String? = null): CompoundParam(value) {
        class Install() : Mode("install") {

            /**
             * Exclude version from package folder names
             */
            var excludeVersion by booleanParameter("nuget.excludeVersion", trueValue = "true", falseValue = "")

        }
    }

    fun install(init: Mode.Install.() -> Unit = {}) : Mode.Install {
        val result = Mode.Install()
        result.init()
        return result
    }

    /**
     * Disable looking up packages from local machine cache.
     */
    var noCache by booleanParameter("nuget.noCache", trueValue = "true", falseValue = "")

    /**
     * Specifies NuGet package sources to use during the restore.
     */
    var sources by stringParameter("nuget.sources")

    /**
     * Enter additional parameters to use when calling nuget pack command.
     */
    var args by stringParameter("nuget.restore.commandline")

    /**
     * Uses the NuGet update command to update all packages under solution
     */
    var updatePackages by compoundParameter<UpdatePackages>("nuget.updatePackages")

    sealed class UpdatePackages(value: String? = null): CompoundParam(value) {
        class UpdateParams() : UpdatePackages("") {

            /**
             * Exclude version from package folder names
             */
            var excludeVersion by booleanParameter("nuget.excludeVersion", trueValue = "true", falseValue = "")

            /**
             * Select how to update packages: via a call to nuget update SolutionFile.sln or via calls to nuget update packages.config
             *
             *
             * @see UpdateMode
             */
            var mode by enumParameter<UpdateMode>("nuget.updatePackages.mode", mapping = UpdateMode.mapping)

            /**
             * Include pre-release packages.
             */
            var includePreRelease by booleanParameter("nuget.updatePackages.include.prerelease", trueValue = "true", falseValue = "")

            /**
             * Perform safe update.
             */
            var useSafe by booleanParameter("nuget.updatePackages.safe", trueValue = "true", falseValue = "")

            /**
             * Enter additional parameters to use when calling nuget update command.
             */
            var args by stringParameter("nuget.update.commandline")

        }
    }

    fun updateParams(init: UpdatePackages.UpdateParams.() -> Unit = {}) : UpdatePackages.UpdateParams {
        val result = UpdatePackages.UpdateParams()
        result.init()
        return result
    }

    /**
     * Update mode
     */
    enum class UpdateMode {
        SolutionFile,
        PackagesConfig;

        companion object {
            val mapping = mapOf<UpdateMode, String>(SolutionFile to "sln", PackagesConfig to "perConfig")
        }

    }
}


/**
 * Adds a [NuGet installer step](https://confluence.jetbrains.com/display/TCDL/NuGet+Installer) to run nuget restore command
 *
 * **Example.**
 * Installs all NuGet packages referenced in the Visual Studio solution file "my_solution.sln" including the pre-release packages.
 * Uses the default NuGet tool version installed on the TeamCity server.
 * ```
 * nuGetInstaller {
 *   toolPath = "%teamcity.tool.NuGet.CommandLine.DEFAULT%"
 *   projects = "my_solution.sln"
 *   updatePackages = updateParams {
 *     includePreRelease = true
 *   }
 * }
 * ```
 *
 * **Example.**
 * Installs all NuGet packages referenced in the Visual Studio solution file "my_solution.sln".
 * Uses a custom path to the NuGet executable.
 * ```
 * nuGetInstaller {
 *   toolPath = "<path to NuGet executable on agent>"
 *   projects = "my_solution.sln"
 * }
 * ```
 *
 *
 * @see NuGetInstallerStep
 */
fun BuildSteps.nuGetInstaller(base: NuGetInstallerStep? = null, init: NuGetInstallerStep.() -> Unit = {}) {
    step(NuGetInstallerStep(init, base))
}
