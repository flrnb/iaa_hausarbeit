<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"
	language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<h4>History Testausgabe</h4>
<s:text name="%{formPruefungsfach}" />
<s:text name="%{formStudent}" />


<table>

	<s:iterator value="history" var="versuch">
		<tr>
			<th><s:property value="%{#versuch.key.toInt()}" /></th>
		</tr>
		<s:iterator value="versuch.value" var="pruefung">
			<tr>
				<td><s:property value="%{#pruefung.value.getPruefungsfach()}" /></td>
			</tr>
		</s:iterator>
	</s:iterator>



</table>

<!-- Map<Versuch, SortedMap<Date, Pruefungsleistung>> -->

<%--<th><s:property value="key.int" /> <s:text name="Versuch" /></th>
			<th><s:property value="key.toInt()" /> <s:text name="Versuch" /></th> --%>


<%-- 	<s:iterator value="history">
		<tr>
			<td><s:property value="%{value.int}" /></td>
		</tr>
	</s:iterator> --%>