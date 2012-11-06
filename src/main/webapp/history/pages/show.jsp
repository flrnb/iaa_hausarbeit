<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"
	language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<h4>History Testausgabe</h4>
<s:text name="%{formPruefungsfach}" />
<s:text name="%{formStudent}" />


<table>
	<tr>
		<s:iterator value="history">
			<th><s:property value="key.int" /> <s:text name="Versuch" /></th>
		</s:iterator>
	</tr>
	<%-- 	<s:iterator value="history">
		<tr>
			<td><s:property value="%{value.int}" /></td>
		</tr>
	</s:iterator> --%>
</table>
