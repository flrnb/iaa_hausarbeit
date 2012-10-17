<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<title><tiles:insertAttribute name="title" /></title>
<s:head />
</head>

<body>
	<tiles:insertAttribute name="header" />
	<hr />
	<tiles:insertAttribute name="content" />
</body>
</html>