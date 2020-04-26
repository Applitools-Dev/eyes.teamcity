package com.applitools.teamcity.buildFeature;

import com.applitools.teamcity.Constants;
import jetbrains.buildServer.serverSide.BuildFeature;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.lang.Override;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;

/**
 * The Applitools build feature settings.
 */
public class ApplitoolsSystemSettings extends BuildFeature {

    private final PluginDescriptor applitoolsPluginDescriptor;

    public ApplitoolsSystemSettings(PluginDescriptor applitoolsPluginDescriptor) {
        this.applitoolsPluginDescriptor = applitoolsPluginDescriptor;
    }

    @NotNull
    @Override
    public String getType() {
        return Constants.APPLITOOLS_BUILD_FEATURE_TYPE;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return "Applitools Support";
    }

    @Nullable
    @Override
    public String getEditParametersUrl() {
        return applitoolsPluginDescriptor.getPluginResourcesPath("applitoolsSettings.jsp");
    }

    @Nullable
    @Override
    public Map<String, String> getDefaultParameters() {
        final HashMap<String, String> map = new HashMap<String, String>();
        map.put("applitoolsPlugin.serverURL", Constants.DEFAULT_APPLITOOLS_SERVER_URL);
        return map;
    }
}
