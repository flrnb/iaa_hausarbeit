<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"
	language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:set var="" value="" scope="request"></s:set>

<tr>
	<s:iterator value="history" var="versuch">
		<th><s:property value="%{#versuch.key.getInt()}" />. Versuch</th>
	</s:iterator>
</tr>
<tr>
	<s:iterator value="history" var="versuch" status="colState">
		<td>
		<ol>
		<s:iterator value="#versuch.value" var="leistug">
			<li><s:property value="%{#leistung.getNote()}" /></li>
			<s:if test="pruefungsleistungHasErgaenzungspruefung(#leistung)">
				<li><s:property value="%{#leistung.getErgaenzungspruefung().getNote()}" /></li>
			</s:if>
		</s:iterator>
		</ol>
		</td>
	</s:iterator>
</tr>


<%-- Ibos altes
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
--%>