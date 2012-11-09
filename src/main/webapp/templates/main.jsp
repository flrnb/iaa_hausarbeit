<%-- Author: Christopher Biel --%>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"
	language="java"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<html>
<head>
<title><tiles:insertAttribute name="title" /></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<link rel="stylesheet" href="<s:url value="/styles/style.css" />" />
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.8.2.min.js"></script>
<script type="text/javascript"
	src="<s:url value="/scripts/script.js" />"></script>
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