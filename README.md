# eyes.teamcity
Applitools Eyes plugin for TeamCity.

This plugin enables you to get Applitools' tests results directly inside your TeamCity's results page.

![Applitools TeamCity Plugin Screenshot](/images/plugin-screenshot-small.png)

### TOC
1. [Installation](#installation)
2. [Usage (after the plugin is installed)](#usage)
  1. [Enabling Applitools Support](#enabling-applitools-support)
  2. [Updating Your Tests Code](#updating-your-tests-code)

### Installation
1. Download the [latest version of the Applitools Eyes Plugin zip file](https://plugins.jetbrains.com/plugin/9441-applitools-eyes-integration/versions).
1. Copy *applitools-eyes.zip* to your TeamCity's plugins folder (e.g, *~/.BuildServer/Plugins*).
1. Restart TeamCity.
1. From your TeamCity's *Projects* page go to: *Administration* -> *Plugins List*. You Should see the Applitools Eyes plugin under *External plugins*:

![External plugins list with Applitools](/images/external-plugins-with-applitools.png)

### Usage
#### Enabling Applitools Support 
1. Go to your project's page and click on *Edit Project Settings* and click on the build configuration for which you want to add Applitools.
1. Click on *Build Features* and add **Applitools Support**.
1. If you have Applitools' private cloud or on-premise server, you can set the server URL in the *Applitools URL* setting.

#### Updating Your Tests Code
The Applitools Plugin causes TeamCity to export the batch ID to the *APPLITOOLS_BATCH_ID* environment variable. You need to update your tests code to use this ID in order for your tests to appear in the Applitools window in the build results.

In addition, TeamCity exports a suggested batch name to the *APPLITOOLS_BATCH_NAME* environment variable. Using this batch name is optional (the batch name is used for display purposes only).

Following is a Java code example:

```Java
BatchInfo batchInfo = new BatchInfo(System.getenv("APPLITOOLS_BATCH_NAME"));
// If the test runs via TeamCity, set the batch ID accordingly.
String batchId = System.getenv("APPLITOOLS_BATCH_ID");
if (batchId != null) {
   batchInfo.setId(batchId);
}
eyes.setBatch(batchInfo);
```

Tests which will contain the above code, will show the Applitools results window inside TeamCity's tests results page (as can be seen in the screenshot at the top of this document).


If you have any questions or need any assistance in using the plugin, feel free to contact Applitools support at: support [at] applitools dot com.
