<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<s:iterator value="module" var="modul">
<a href="<s:url action="show"/>?module=<s:property value="modul"/>">Modul <s:property value="modul"/></a><br />
</s:iterator>