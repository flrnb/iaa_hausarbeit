<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<title><tiles:insertAttribute name="title" /></title>
<link rel="stylesheet" href="/styles/style.css" />
<s:head />
</head>

<body>

	<tiles:insertAttribute name="header" />
	<%-- <s:action name="ShowHeaderMenuAction" namespace="/" executeResult="true"></s:action> --%>
	<hr />
	<tiles:insertAttribute name="content" />
</body>
</html>