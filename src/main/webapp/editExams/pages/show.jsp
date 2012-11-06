<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<h3>
	Prüfungsleistungen ändern (Modul <i><s:property
			value="selectedPruefungsfach" /></i> )
</h3>

<s:a action="modulSelection" namespace="/writtenExams">Modul wechseln</s:a>

<p>Hier können Sie die erreichten Noten von dem Modul ändern.</p>

<s:if test="pruefungenBeans.size() > 0">
	<form action="<s:url action="save"/>">
		<input type="hidden" name="pruefungsfach"
			value="<s:property value="pruefungsfach"/>" />
		<table class="notenTabelle" border="1">
			<tr>
				<th>Matrikel Nr.</th>
				<th>Name</th>
				<th>Note 1</th>
				<th>Note 2</th>
				<th>Note 3</th>
			</tr>
			<s:iterator value="pruefungenBeans" var="pruefung" status="stat">
				<s:hidden name="pruefungenBeans[%{#stat.index}].student.matrikelNr"
					value="%{#pruefung.student.matrikelNr}" />
				<tr>
					<td><s:property value="%{#pruefung.student.matrikelNr}" /></td>
					<td><s:property value="%{#pruefung.student.vorname}" /> <s:property
							value="%{#pruefung.student.name}" /></td>

					<%-- <s:iterator begin="0" end="2" step="1" var="current"> --%>
					<s:iterator value="pruefungsleistungen" var="current"
						status="stat1">
						<s:hidden
							name="pruefungenBeans[%{#stat.index}].pruefungsleistungen[%{#stat1.index}].id"
							value="%{#current.id}"></s:hidden>
						<td class="editorField"><s:textfield
								name="pruefungenBeans[%{#stat.index}].pruefungsleistungen[%{#stat1.index}].note"
								cssClass="changeNote notenInputField"
								cssErrorClass="fieldErrorCls" theme="simple"
								value="%{#current.note}"
								disabled="%{!isWriteable(#current.id)}" /> <a
							href="<s:url action="delete"/>?pruefungsfach=<s:property value="%{getSelectedPruefungsfach().getId()}"/>&deleteId=<s:property value="%{#current.id}"/>"
							class="linkButton" title="Eintrag löschen">X</a> <s:fielderror
								theme="iaa">
								<s:param>pruefungenBeans[${stat.index}].pruefungsleistungen[${stat1.index}].note</s:param>
							</s:fielderror></td>
					</s:iterator>

					<s:iterator begin="%{pruefungsleistungen.size()}" end="2" step="1"
						var="current">
						<td></td>
					</s:iterator>
				</tr>
			</s:iterator>
		</table>
		<s:submit value="Speichern" />
	</form>
</s:if>
<s:else>
	<h4>Keine Studenten für diese Auswahl gepflegt!</h4>
</s:else>