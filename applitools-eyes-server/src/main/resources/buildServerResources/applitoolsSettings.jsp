<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="forms" tagdir="/WEB-INF/tags/forms" %>
<c:url var="actionUrl" value="/applitoolsSettings.html"/>
<jsp:useBean id="propertiesBean" scope="request" type="jetbrains.buildServer.controllers.BasePropertiesBean"/>

<c:set var="applitoolsEnabled"
       value="${propertiesBean.properties['applitoolsPlugin.applitoolsEnabled']}"/>

<tr id="applitoolsPlugin.serverURL.container">
    <th><label for="applitoolsPlugin.serverURL">Applitools URL:</label></th>
    <td>
        <props:textProperty name="applitoolsPlugin.serverURL" value="https://eyes.applitools.com" />
    </td>
</tr>
