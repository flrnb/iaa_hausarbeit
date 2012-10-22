<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<h3>
	Prüfungsleistungen erfassen (Modul <i><s:property
			value="selectedPruefungsfach" /></i> )
</h3>

<s:a action="modulSelection" namespace="/writtenExams">Modul wechseln</s:a>

<p>Hier können Sie die erreichten Noten von dem Modul eingetragen
	werden.</p>

<s:if test="pruefungenBeans.size() > 0">
	<form action="<s:url action="save"/>">
		<input type="hidden" name="pruefungsfach"
			value="<s:property value="pruefungsfach"/>" /> <input type="hidden"
			name="pruefung" value="<s:property value="pruefung"/>" />
		<table class="notenTabelle" border="1">
			<tr>
				<th>matrikelnummer</th>
				<th>name</th>
				<th>alte noten</th>
				<th>neue note</th>
			</tr>
			<s:iterator value="pruefungenBeans" var="pruefung" status="stat">
				<tr>
					<td><s:property value="%{#pruefung.matrikelNummer}" /></td>
					<td><s:property value="%{#pruefung.name}" /></td>
					<td><s:property value="%{#pruefung.alteNote}" /></td>
					<td class="editorField"><s:textfield
							cssErrorClass="fieldErrorCls"
							name="pruefungenBeans[%{#stat.index}].note" theme="simple"
							cssClass="notenInputField" /> <s:fielderror theme="iaa">
							<s:param>pruefungenBeans[${stat.index}].note</s:param>
						</s:fielderror></td>
				</tr>
			</s:iterator>
		</table>
		<s:submit value="Speichern" />
	</form>
</s:if>
<s:else>
	<h4>Keine Studenten für diese Auswahl gepflegt!</h4>
</s:else>