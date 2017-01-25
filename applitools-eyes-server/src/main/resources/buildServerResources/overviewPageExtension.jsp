<%@include file="/include.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="serverURL" type="java.lang.String" scope="request"/>
<iframe id="applitoolsFrame" src="${serverURL}" style="overflow:hidden;overflow-x:hidden;overflow-y:hidden;height:600px;width:100%;resize:vertical;">