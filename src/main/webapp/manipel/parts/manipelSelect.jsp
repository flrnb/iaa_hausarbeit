<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:form action="save" namespace="manipel">
	<s:select list="manipel" listKey="id" name="selectedManipel" label="Manipel wählen"
		headerKey="-1" headerValue="--- Select ---" />
	<s:submit value="Ändern"></s:submit>
</s:form>