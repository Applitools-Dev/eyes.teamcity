package com.applitools.teamcity;

import java.lang.String;
/**
 * Constants used by both server and agent sides of the plugin.
 */
public class Constants {

    /* Environment Variables */
    public static final String APPLITOOLS_PROJECT_SERVER_URL_ENV_VAR = "APPLITOOLS_PROJECT_SERVER_URL";
    public static final String APPLITOOLS_BATCH_ID_ENV_VAR = "APPLITOOLS_BATCH_ID";
    public static final String APPLITOOLS_BATCH_NAME_ENV_VAR = "APPLITOOLS_BATCH_NAME";
    public static final String APPLITOOLS_SEQUENCE_NAME_ENV_VAR = "APPLITOOLS_SEQUENCE_NAME";


    /* Applitools Settings Page */
    public static final String APPLITOOLS_PROJECT_SERVER_URL_BEAN_ID = "serverURL";
    public static final String APPLITOOLS_SERVER_URL_FIELD = "applitoolsPlugin.serverURL";

    /* Applitools Build Feature Common */
    public static final String DEFAULT_APPLITOOLS_SERVER_URL = "https://eyes.applitools.com";
    public static final String APPLITOOLS_BATCH_ID_PREFIX = "teamcity";
    public static final String APPLITOOLS_BUILD_FEATURE_TYPE = "applitools";
    public static final String PLUGIN_ID = "applitools-eyes";


    private Constants() {
    }
}
