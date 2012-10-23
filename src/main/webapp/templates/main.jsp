<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<html>
<head>
<title><tiles:insertAttribute name="title" /></title>
<link rel="stylesheet" href="/styles/style.css" />

<s:head />
<sx:head />
</head>
<body>
	<div class="header">
		<tiles:insertAttribute name="header" />
	</div>
	<hr />
	<tiles:insertAttribute name="content" />
</body>
</html>