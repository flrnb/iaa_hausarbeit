<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<table>
<s:iterator value="protokoll" var="zeile">
	<tr>
	<td>
	<s:if test="zeile.typ == \"FEHLER\"">
		<span class="protokollError"><s:property value="zeile.nachricht"/></span>
	</s:if>
	<s:else>
		<span class="protokollMessage"><s:property value="zeile.nachricht"/></span>	
	</s:else>
	</td>
	</tr>
</s:iterator>
</table>
<a href="window.history.back()">Zurück</a>