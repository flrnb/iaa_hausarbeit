<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>


<p class="dontPrint" style="font-style: italic; text-decoration: underline; font-size: small;">
	Wenn Sie diese Seite drucken möchten, verwenden Sie bitte die normale Durckfunktion des Browsers (Bspw. STRG + P).
</p>

<div class="printable">
	<h3>Protokoll</h3>

	<table class="protokollTable">
		<s:iterator value="protokoll" var="zeile">
			<tr>
				<td class="protokollField"><s:if test="#zeile.typ == 'fehler'">
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
<s:if test="protokoll.size() > 0">
	<br /><a class="dontPrint" href="javascript:window.history.back()">Zurück</a>
</s:if>