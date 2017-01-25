package com.applitools.teamcity;

/**
 * Methods used by both server and agent sides of the plugin.
 */
public class Common {

    public static String generateBatchId(String buildTypeId, String buildNumber, long buildId)
    {
        return Constants.APPLITOOLS_BATCH_ID_PREFIX + "-" + buildTypeId + "-" + buildNumber + "-" + Long.toString(buildId);
    }

}
