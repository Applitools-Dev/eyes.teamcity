package com.applitools.teamcity.pageExtension;

import jetbrains.buildServer.web.ContentSecurityPolicyConfig;

public class OverviewPageExtensionCSP {
    public OverviewPageExtensionCSP(ContentSecurityPolicyConfig config) {
        config.addDirectiveItems("frame-src", "https://*");
    }
}
