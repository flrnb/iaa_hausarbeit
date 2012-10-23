<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>


<div class="printable">
	<h3>Protokoll</h3>

	<table>
		<s:iterator value="protokoll" var="zeile">
			<tr>
				<td><s:if test="#zeile.typ == 'fehler'">
						<span class="protokollError"><s:property
								value="#zeile.nachricht" /></span>
					</s:if> <s:else>
						<span class="protokollMessage"><s:property
								value="#zeile.nachricht" /></span>
					</s:else></td>
			</tr>
		</s:iterator>
	</table>
</div>
<s:if test="getFieldErrors().size() > 0">
	<a href="javascript:window.history.back()">Zurück</a>
</s:if>