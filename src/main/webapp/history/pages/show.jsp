<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"
	language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<h3>
	Historie von
	<s:property value="%{getSelectedStudent().getFullname()}" />
	(
	<s:property value="%{getSelectedManipel()}" />
	)<br /> Fach:
	<s:property value="%{getSelectedPruefungsfach()}" />
</h3>
<table>
	<s:iterator value="history" var="versuch" status="colState">
		<tr>
			<td style="font-weight: bold; font-size: medium;">Versuch <s:property
					value="%{#versuch.key.toInt()}" /></td>
		</tr>
		<tr>
			<td>
				<ol style="border: 1px dotted gray; padding-left: 20px;">
					<s:if test="#versuch.value.size() > 0">
						<s:iterator value="#versuch.value" var="leistug">
							<li><s:if test="isGeloescht()">
				Storniert am
							<s:date name="getGueltigVon()" format="dd.MM.yyyy" />

								</s:if> <s:elseif
									test="getPruefungsleistung().getErgaenzungsPruefung() != null">
								Ergänzungsprüfung eingetragen am <s:date
										name="getPruefungsleistung().getErgaenzungsPruefung().getDatum()"
										format="dd.MM.yyyy" />
									mit Note <s:property
										value="getPruefungsleistung().getErgaenzungsPruefung().getNote()" />
								</s:elseif> <s:else>
				Geändert am
		<s:date name="getGueltigVon()" format="dd.MM.yyyy" />
		auf Note
		<s:property value="getPruefungsleistung().getNote()" />
								</s:else></li>
						</s:iterator>
					</s:if>
					<s:else>
						<li style="list-style: none;">Keine Eintragungen für diesen
							Versuch</li>
					</s:else>
				</ol> <s:if test="#versuch.key.toInt() != 3">
					<hr />
				</s:if>
			</td>
		</tr>
	</s:iterator>
</table>

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