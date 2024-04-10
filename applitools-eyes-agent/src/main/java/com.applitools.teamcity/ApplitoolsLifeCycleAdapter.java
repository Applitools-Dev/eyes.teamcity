package com.applitools.teamcity;

import jetbrains.buildServer.agent.AgentBuildFeature;
import jetbrains.buildServer.agent.AgentLifeCycleAdapter;
import jetbrains.buildServer.agent.AgentLifeCycleListener;
import jetbrains.buildServer.agent.AgentRunningBuild;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.util.EventDispatcher;
import jetbrains.buildServer.util.StringUtil;
import jetbrains.buildServer.vcs.VcsRoot;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
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
            bindPointersIfNeeded(runningBuild, feature);
            populateEnvironmentVariables(runningBuild, feature);
        }
    }

    private void bindPointersIfNeeded(AgentRunningBuild build, AgentBuildFeature feature) {
        String apiKey = feature.getParameters().get(Constants.APPLITOOLS_API_KEY_FIELD);
        String serverUrl = Common.getServerUrl(feature.getParameters().get(Constants.APPLITOOLS_SERVER_URL_FIELD));
        String buildId = Common.generateBatchId(build.getBuildTypeId(), build.getBuildNumber(), build.getBuildId());
        String eyesScmIntegrationEnabled = feature.getParameters().get(Constants.APPLITOOLS_SCM_INTEGRATION_ENABLED_FIELD);
        if (apiKey != null && !apiKey.isEmpty() && "true".equalsIgnoreCase(eyesScmIntegrationEnabled)) {
            String batchId = feature.getParameters().get(Constants.APPLITOOLS_BATCH_ID_ENV_VAR);
            if (batchId == null || batchId.isEmpty()){
                System.getenv(Constants.APPLITOOLS_BATCH_ID_ENV_VAR);
            }
            if (batchId == null || batchId.isEmpty()) {
                batchId = getCommitHash(build);
            }

            HttpClient httpClient = new HttpClient();
            try {
                URI targetUrl = new URI(serverUrl, false);
                targetUrl.setPath(String.format(Constants.BATCH_BIND_POINTERS_PATH, batchId));
                targetUrl.setQuery("apiKey=" + apiKey);
                PostMethod postRequest = new PostMethod(targetUrl.toString());
                try {
                    RequestEntity reqEnt = new StringRequestEntity("{\"secondaryBatchPointerId\":\"" + buildId + "\"}", "application/json", "UTF-8");
                    postRequest.setRequestEntity(reqEnt);
                    Loggers.AGENT.info(String.format("Binding build id %s to batch id %s", buildId, batchId));
                    int statusCode = httpClient.executeMethod(postRequest);
                    Loggers.AGENT.info("Batch binding is done with " + statusCode + " status");
                } catch (IOException e) {
                    Loggers.AGENT.error("Failed to complete HTTP request", e);
                } finally {
                    postRequest.releaseConnection();
                }
            } catch (URIException exception) {
                Loggers.AGENT.error("Failed to get API endpoint URL",exception);
            }
        }
    }

    @NotNull
    private static String getCommitHash(AgentRunningBuild build) {
        VcsRoot vcsRoot = build.getVcsRootEntries().get(0).getVcsRoot();
        return build.getBuildCurrentVersion(vcsRoot);
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

        String eyesScmIntegrationEnabled = feature.getParameters().get(Constants.APPLITOOLS_SCM_INTEGRATION_ENABLED_FIELD);
        String batchId;
        if (eyesScmIntegrationEnabled != null && eyesScmIntegrationEnabled.equalsIgnoreCase("true")) {
            batchId = getCommitHash(runningBuild);
        }
        else {
            batchId = Common.generateBatchId(runningBuild.getBuildTypeId(), runningBuild.getBuildNumber(), runningBuild.getBuildId());
        }
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
