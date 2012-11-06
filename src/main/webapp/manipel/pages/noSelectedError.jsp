<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<h3>Sie müssen einen Manipel auswählen, bevor Sie Änderungen durchführen können:</h3>

<s:form action="save" namespace="/manipel" method post>
	<s:select list="manipel" name="selectedManipel" label="Manipel wählen"
		headerKey="-1" headerValue="--- Select ---" />
	<s:submit value="Ändern"></s:submit>
</s:form>