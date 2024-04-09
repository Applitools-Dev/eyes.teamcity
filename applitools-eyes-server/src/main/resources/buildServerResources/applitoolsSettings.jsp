<%@ page import="com.applitools.teamcity.Constants" %>
<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="forms" tagdir="/WEB-INF/tags/forms" %>
<c:url var="actionUrl" value="/applitoolsSettings.html"/>
<jsp:useBean id="propertiesBean" scope="request" type="jetbrains.buildServer.controllers.BasePropertiesBean"/>

<c:set var="applitoolsEnabled"
       value="${propertiesBean.properties['applitoolsPlugin.applitoolsEnabled']}"/>

<tr id="applitoolsPlugin.apiKey.container">
    <th><label for="applitoolsPlugin.apiKey">Applitools API key:</label></th>
    <td>
        <props:textProperty name="applitoolsPlugin.apiKey"/>
    </td>
</tr>

<tr id="applitoolsPlugin.serverURL.container">
    <th><label for="applitoolsPlugin.serverURL">Applitools URL:</label></th>
    <td>
        <props:textProperty name="applitoolsPlugin.serverURL"/>
    </td>
</tr>

<tr id="applitoolsPlugin.notifyByCompletion.container">
    <th><label for="applitoolsPlugin.notifyByCompletion">Notify by completion:</label></th>
    <td>
        <props:checkboxProperty name="applitoolsPlugin.notifyByCompletion"/>
    </td>
</tr>

<tr id="applitoolsPlugin.eyesScmIntegrationEnabled.container">
    <th><label for="applitoolsPlugin.eyesScmIntegrationEnabled">Eyes SCM Integration Enabled:</label></th>
    <td>
        <props:checkboxProperty name="applitoolsPlugin.eyesScmIntegrationEnabled"/>
    </td>
</tr>

