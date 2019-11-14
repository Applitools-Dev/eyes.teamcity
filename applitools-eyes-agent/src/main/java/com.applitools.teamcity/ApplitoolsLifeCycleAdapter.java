package com.applitools.teamcity;

import jetbrains.buildServer.agent.*;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.util.EventDispatcher;
import jetbrains.buildServer.util.StringUtil;
import jetbrains.buildServer.util.StringUtils;
import org.jetbrains.annotations.NotNull;
import java.lang.String;
import java.util.Collection;

/**
 * A class used to export Applitools related properties to the build agent.
 */
public class ApplitoolsLifeCycleAdapter extends AgentLifeCycleAdapter {

    public ApplitoolsLifeCycleAdapter(
            @NotNull EventDispatcher<AgentLifeCycleListener> agentDispatcher) {
        agentDispatcher.addListener(this);
    }

    @Override
    public void buildStarted(@NotNull AgentRunningBuild runningBuild) {
        super.buildStarted(runningBuild);
        runningBuild.getBuildLogger().message("Build Started, setting Applitools environment variables:");
        Loggers.AGENT.info("Build Started, setting Applitools environment variables");
        Collection<AgentBuildFeature> features = runningBuild.getBuildFeaturesOfType(Constants.APPLITOOLS_BUILD_FEATURE_TYPE);
        if (features.isEmpty()) return;
        for (AgentBuildFeature feature : features) {
            populateEnvironmentVariables(runningBuild, feature);
        }
    }

    private String getApplitoolsURL(AgentBuildFeature feature) {
        String serverURL = feature.getParameters().get(Constants.APPLITOOLS_SERVER_URL_FIELD);
        return Common.getServerUrl(serverURL);
    }

    private void populateEnvironmentVariables(AgentRunningBuild runningBuild, AgentBuildFeature feature) {
        runningBuild.getBuildLogger().message("Creating Applitools environment variables:");
        Loggers.AGENT.info("Creating Applitools environment variables:");
        String apiKey = feature.getParameters().get(Constants.APPLITOOLS_API_KEY_FIELD);
        if (apiKey != null && !StringUtil.isEmpty(apiKey)) {
            addSharedEnvironmentVariable(runningBuild, Constants.APPLITOOLS_API_KEY_ENV_VAR, apiKey);
        }
        addSharedEnvironmentVariable(runningBuild, Constants.APPLITOOLS_PROJECT_SERVER_URL_ENV_VAR, getApplitoolsURL(feature));

        String batchId = Common.generateBatchId(runningBuild.getBuildTypeId(), runningBuild.getBuildNumber(), runningBuild.getBuildId());
        addSharedEnvironmentVariable(runningBuild, Constants.APPLITOOLS_BATCH_ID_ENV_VAR, batchId);

        addSharedEnvironmentVariable(runningBuild, Constants.APPLITOOLS_BATCH_NAME_ENV_VAR,
                runningBuild.getProjectName() + " / " + runningBuild.getBuildTypeName());

        String sequenceName = runningBuild.getProjectName();
        addSharedEnvironmentVariable(runningBuild, Constants.APPLITOOLS_BATCH_SEQUENCE_ENV_VAR, sequenceName);

        addSharedEnvironmentVariable(runningBuild, Constants.APPLITOOLS_DONT_CLOSE_BATCHES_ENV_VAR, "true");
    }

    private void addSharedEnvironmentVariable(AgentRunningBuild runningBuild, String key, String value) {
        if (value != null) {
            runningBuild.getBuildLogger().message(key + " = " + value);
            Loggers.AGENT.info(key + " = " + value);
            runningBuild.addSharedEnvironmentVariable(key, value);
        }
    }
}
