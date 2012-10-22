<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<h3>Manipel ändern</h3>
Aktuell ausgewählt:

<s:if test="selectedManipel == null || selectedManipel == \"-1\"">
	<i>keiner Ausgewählt</i>
</s:if>
<s:else>
	<s:property value="selectedManipel" />
</s:else>
<br />

<s:form action="save" namespace="/manipel">
	<s:select list="manipel" name="selectedManipel" label="Manipel wählen"
		headerKey="-1" headerValue="--- Select ---" />
	<s:submit value="Ändern"></s:submit>
</s:form>