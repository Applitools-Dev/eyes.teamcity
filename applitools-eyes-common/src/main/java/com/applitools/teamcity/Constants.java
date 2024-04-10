package com.applitools.teamcity;

import java.lang.String;
/**
 * Constants used by both server and agent sides of the plugin.
 */
public class Constants {

    /* Environment Variables */
    public static final String APPLITOOLS_PROJECT_SERVER_URL_ENV_VAR = "APPLITOOLS_SERVER_URL";
    public static final String APPLITOOLS_BATCH_ID_ENV_VAR = "APPLITOOLS_BATCH_ID";
    public static final String APPLITOOLS_BATCH_NAME_ENV_VAR = "APPLITOOLS_BATCH_NAME";
    public static final String APPLITOOLS_BATCH_SEQUENCE_ENV_VAR = "APPLITOOLS_BATCH_SEQUENCE";
    public static final String APPLITOOLS_DONT_CLOSE_BATCHES_ENV_VAR = "APPLITOOLS_DONT_CLOSE_BATCHES";
    public static final String APPLITOOLS_API_KEY_ENV_VAR = "APPLITOOLS_API_KEY";


    /* Applitools Settings Page */
    public static final String APPLITOOLS_PROJECT_SERVER_URL_BEAN_ID = "serverURL";
    public static final String APPLITOOLS_SERVER_URL_FIELD = "applitoolsPlugin.serverURL";
    public static final String APPLITOOLS_API_KEY_FIELD = "applitoolsPlugin.apiKey";
    public static final String APPLITOOLS_NOTIFY_BY_COMPLETION_FIELD = "applitoolsPlugin.notifyByCompletion";
    public static final String APPLITOOLS_SCM_INTEGRATION_ENABLED_FIELD = "applitoolsPlugin.eyesScmIntegrationEnabled";


    /* Applitools Build Feature Common */
    public static final String DEFAULT_APPLITOOLS_SERVER_URL = "https://eyesapi.applitools.com";
    public static final String APPLITOOLS_BATCH_ID_PREFIX = "teamcity";
    public static final String APPLITOOLS_BUILD_FEATURE_TYPE = "applitools";
    public static final String PLUGIN_ID = "applitools-eyes";

    public static final String BATCH_NOTIFICATION_PATH = "/api/sessions/batches/%s/close/bypointerid";
    public final static String BATCH_BIND_POINTERS_PATH = "/api/sessions/batches/bindpointers/%s";


    private Constants() {
    }
}
