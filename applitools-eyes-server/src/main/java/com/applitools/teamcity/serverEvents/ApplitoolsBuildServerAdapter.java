package com.applitools.teamcity.serverEvents;

import com.applitools.teamcity.Common;
import com.applitools.teamcity.Constants;
import jetbrains.buildServer.serverSide.*;
import jetbrains.buildServer.serverSide.buildLog.BuildLog;
import jetbrains.buildServer.util.EventDispatcher;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;



public class ApplitoolsBuildServerAdapter extends BuildServerAdapter {

    public ApplitoolsBuildServerAdapter(EventDispatcher<BuildServerListener> dispatcher) {
        dispatcher.addListener(this);
    }

    @Override
    public void beforeBuildFinish(@NotNull SRunningBuild build) {
        super.buildFinished(build);
        closeBatch(build);
    }

    private void closeBatch(SRunningBuild build) {

        Collection<SBuildFeatureDescriptor> features = build.getBuildFeaturesOfType(Constants.APPLITOOLS_BUILD_FEATURE_TYPE);
        for (SBuildFeatureDescriptor feature : features) {

            String apiKey = feature.getParameters().get(Constants.APPLITOOLS_API_KEY_FIELD);
            String notifyByCompletion = feature.getParameters().get(Constants.APPLITOOLS_NOTIFY_BY_COMPLETION_FIELD);
            String batchId = Common.generateBatchId(build.getBuildTypeId(), build.getBuildNumber(), build.getBuildId());
            String serverUrl = Common.getServerUrl(feature.getParameters().get(Constants.APPLITOOLS_SERVER_URL_FIELD));
            if (apiKey != null && !apiKey.isEmpty() && notifyByCompletion.equalsIgnoreCase("true")) {
                HttpClient httpClient = new HttpClient();
                BuildLog log = build.getBuildLog();
                try {
                    URI targetUrl = new URI(serverUrl, false);
                    targetUrl.setPath(String.format(Constants.BATCH_NOTIFICATION_PATH, batchId));
                    targetUrl.setQuery("apiKey=" + apiKey);
                    DeleteMethod deleteRequest = new DeleteMethod(targetUrl.toString());
                    try {
                        log.progressMessage(String.format("Batch notification called with %s", batchId), new Date(System.currentTimeMillis()), "batchNotification", new ArrayList<String>());
                        int statusCode = httpClient.executeMethod(deleteRequest);
                        log.progressMessage("Delete batch is done with " + Integer.toString(statusCode) + " status", new Date(System.currentTimeMillis()), "batchNotification", new ArrayList<String>());
                    } catch(HttpException exception) {
                        log.progressMessage("Failed to complete HTTP request: " + exception.getMessage(), new Date(System.currentTimeMillis()), "batchNotification", new ArrayList<String>());
                    } catch (IOException e) {
                        log.progressMessage("Failed to complete HTTP request (IOException): " + e.getMessage(), new Date(System.currentTimeMillis()), "batchNotification", new ArrayList<String>());
                    } finally {
                        deleteRequest.releaseConnection();
                    }
                } catch (URIException exception) {
                    log.progressMessage("Failed to get API endpoint URL: " + exception.getMessage(), new Date(System.currentTimeMillis()), "batchNotification", new ArrayList<String>());
                }
            }
        }
    }
}
