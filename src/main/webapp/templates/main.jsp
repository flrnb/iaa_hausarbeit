<%-- Author: Christopher Biel, Ibrahim Karagac --%>
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
	<div id="header">
		<tiles:insertAttribute name="header" />
		<div id="kasten_titel"></div>
		<div id="kasten_inhalt">
			<div id="inhalt">
				<div id="navileiste">
					<tiles:insertAttribute name="content" />
				</div>
			</div>
		</div>
		<div id="kasten_boden"></div>
	</div>
</body>
</html>

<%-- meins 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="test.css" />
<title>Notendatenbank</title>
</head>
<body>


	<div id="header">
		<div id="hauptmenue_button"></div>
		<div id="manipel_button"></div>
		<div id="kasten_titel"></div>
		<div id="kasten_inhalt">
			<div id="inhalt">Lorem ipsum dolor sit amet, consetetur</div>
		</div>
		<div id="kasten_boden"></div>
	</div>
</body>




</html>
--%>

<%-- Aktuell 
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
--%>

<!-- backup -->
<%--<body>
	<div id="header">
		<s:a action="" namespace="/"><div id="hauptmenue_button"></div></s:a>
		<s:a action="show" namespace="/manipel"><div id="manipel_button"></div></s:a>
<%-- 		<tiles:insertAttribute name="header" />

		<hr /> <%--
		<div id="kasten_titel"></div>
		<div id="kasten_inhalt">
			<div id="inhalt">
				<tiles:insertAttribute name="content" />
			</div>
		</div>
		<div id="kasten_boden"></div>
	</div>
</body>
--%>
