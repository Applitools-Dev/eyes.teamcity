package com.applitools.teamcity;

import jetbrains.buildServer.agent.*;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.util.EventDispatcher;
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
        Loggers.AGENT.info("Build Started, setting Applitools environment variables");
        Collection<AgentBuildFeature> features = runningBuild.getBuildFeaturesOfType(Constants.APPLITOOLS_BUILD_FEATURE_TYPE);
        if (features.isEmpty()) return;
        for (AgentBuildFeature feature : features) {
            populateEnvironmentVariables(runningBuild, feature);
        }
    }

    private String getApplitoolsURL(AgentBuildFeature feature) {
        String serverURL = feature.getParameters().get(Constants.APPLITOOLS_SERVER_URL_FIELD);
        if (serverURL == null || serverURL.isEmpty()) {
            serverURL = Constants.DEFAULT_APPLITOOLS_SERVER_URL;
        }
        return serverURL;
    }

    private void populateEnvironmentVariables(AgentRunningBuild runningBuild, AgentBuildFeature feature) {
        Loggers.AGENT.info("Creating Applitools environment variables:");
        addSharedEnvironmentVariable(runningBuild, Constants.APPLITOOLS_PROJECT_SERVER_URL_ENV_VAR, getApplitoolsURL(feature));

        String batchId = Common.generateBatchId(runningBuild.getBuildTypeId(), runningBuild.getBuildNumber(), runningBuild.getBuildId());
        addSharedEnvironmentVariable(runningBuild, Constants.APPLITOOLS_BATCH_ID_ENV_VAR, batchId);
    }

    private void addSharedEnvironmentVariable(AgentRunningBuild runningBuild, String key, String value) {
        if (value != null) {
            Loggers.AGENT.info(key + " = " + value);
            runningBuild.addSharedEnvironmentVariable(key, value);
        }
    }
}
