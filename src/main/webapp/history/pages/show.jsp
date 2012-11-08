<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"
	language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<s:iterator value="history" var="versuch" status="colState">
	<tr>
		<td class="uebersichtHeader">Versuch <s:property value="%{#versuch.key.toInt()}" /></td>
	</tr>
	<tr>
		<td>
			<table style="border: 1px dotted gray;" cellspacing="2">
				<s:if test="#versuch.value.size() > 0">

					<s:iterator value="#versuch.value" var="leistug">
						<tr>
							<td><s:if test="isGeloescht()">
				Storniert am</td>
							<td><s:date name="getGueltigVon()" format="dd.MM.yyyy" /></td>
							<td></td>
							<td>
				</s:if>
				<s:else>
				Geändert am</td>
					<td><s:date name="getGueltigVon()" format="dd.MM.yyyy" /></td>
					<td>auf Note</td>
					<td><s:property value="getPruefungsleistung().getNote()" />
				</s:else>
				</td>
				</tr>
				<s:if test="pruefungsleistungHasErgaenzungspruefung(#leistung)">
						<tr><td>Ergänzungsprüfung eingetragen am <s:property
								value="getPruefungsleistung().getErgaenzungsPruefung().getDatum()" />
							mit Note <s:property
								value="getPruefungsleistung().getErgaenzungsPruefung().getNote()" />
						</td></tr>
					</s:if>
				
				</s:iterator>
				</s:if>
				<s:else>
					<tr>
						<td>Keine Eintragungen für diesen Versuch</td>
					</tr>
				</s:else>
			</table> <s:if test="#versuch.key.toInt() != 3">
				<tr>
					<td><hr /></td>
				</tr>
			</s:if> <%-- <ol>
				<s:iterator value="#versuch.value" var="leistug" status="stat">
					<li><s:if test="isGeloescht()">
				Storniert am <s:date name="getGueltigVon()" format="dd.MM.yyyy" />
						</s:if> <s:else>
				Geändert am <s:date name="getGueltigVon()" format="dd.MM.yyyy" /> auf Note <s:property
								value="getPruefungsleistung().getNote()" />
						</s:else></li>
					<s:if test="pruefungsleistungHasErgaenzungspruefung(#leistung)">
						<li>Ergänzungsprüfung eingetragen am <s:property
								value="getPruefungsleistung().getErgaenzungsPruefung().getDatum()" />
							mit Note <s:property
								value="getPruefungsleistung().getErgaenzungsPruefung().getNote()" />
						</li>
					</s:if>
				</s:iterator>
			</ol> --%>
		</td>
	</tr>
</s:iterator>


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