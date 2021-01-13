
package com.applitools.teamcity.pageExtension;

import javax.servlet.http.HttpServletRequest;
import com.applitools.teamcity.Constants;
import com.applitools.teamcity.Common;
import jetbrains.buildServer.serverSide.*;
import jetbrains.buildServer.web.openapi.PagePlaces;
import jetbrains.buildServer.web.openapi.SimplePageExtension;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Map;
import jetbrains.buildServer.util.EventDispatcher;

import static jetbrains.buildServer.web.openapi.PlaceId.BUILD_RESULTS_FRAGMENT;

public class OverviewPageExtension extends SimplePageExtension {
  private final SBuildServer sBuildServer;

  public OverviewPageExtension(@NotNull PagePlaces pagePlaces, @NotNull SBuildServer sBuildServer,
                               EventDispatcher<BuildServerListener> eventDispatcher)
  {
    super(pagePlaces, BUILD_RESULTS_FRAGMENT, Constants.PLUGIN_ID, "overviewPageExtension.jsp");
    this.sBuildServer = sBuildServer;
  }

  @Override
  public void fillModel(@NotNull Map<String, Object> model, @NotNull HttpServletRequest request)
  {
    super.fillModel(model, request);
    final SBuild sBuild = getBuild(request);
    model.put(Constants.APPLITOOLS_PROJECT_SERVER_URL_BEAN_ID, generateIframeURL(sBuild));
  }

  @Override
  public boolean isAvailable(@NotNull HttpServletRequest request)
  {
    final SBuild sBuild = getBuild(request);
    final SBuildFeatureDescriptor applitoolsBuildFeature = getApplitoolsBuildFeature(sBuild);
    if (applitoolsBuildFeature != null) {
       return sBuild.getBuildType().isEnabled(applitoolsBuildFeature.getId());
    }
    return false;
  }

  private String generateBatchId(SBuild sBuild) {
    return Common.generateBatchId(sBuild.getBuildTypeId(), sBuild.getBuildNumber(), sBuild.getBuildId());
  }

  private String generateIframeURL(SBuild sBuild)
  {
    try {
      String apiServerURL = getApplitoolsBuildFeature(sBuild).getParameters().get(Constants.APPLITOOLS_SERVER_URL_FIELD);
      apiServerURL = apiServerURL + "/app/batchesnoauth/?startInfoBatchId=" + generateBatchId(sBuild) + "&hideBatchList=true&intercom=false&agentId=eyes-teamcity-1.3.0";
      try {
        URI serverUrl = new URI(apiServerURL, true, Charset.forName("UTF-8").toString());
        String hostName = serverUrl.getHost();
        hostName = hostName.replaceAll("^([^.]+)api.(.*)$", "$1.$2");
        return new URI(serverUrl.getScheme(), null, hostName, serverUrl.getPort(),serverUrl.getPath(), serverUrl.getQuery()).toString();
      } catch (URIException e) {
        return apiServerURL;
      }
    }
    catch(NullPointerException exception) {
      return "";
    }
  }

  private SBuildFeatureDescriptor getApplitoolsBuildFeature(SBuild build) {
    Collection<SBuildFeatureDescriptor> features = build.getBuildType().getBuildFeatures();
    if (features.isEmpty()) return null;
    for (SBuildFeatureDescriptor feature : features) {
      if (feature.getType().equals(Constants.APPLITOOLS_BUILD_FEATURE_TYPE)) {
        return feature;
      }
    }
    return null;
  }

  private SBuild getBuild(HttpServletRequest request) {
    final Long buildId;
    buildId = Long.valueOf(request.getParameter("buildId"));
    return sBuildServer.findBuildInstanceById(buildId);
  }

}
