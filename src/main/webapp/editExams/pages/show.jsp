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
			value="<s:property value="pruefungsfach"/>" /> <input type="hidden"
			name="pruefung" value="<s:property value="pruefung"/>" />
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

					<s:iterator begin="0" end="2" step="1" var="current">
						<td class="editorField"><s:textfield
								name="pruefungenBeans[%{#stat.index}].note%{#current}"
								cssClass="changeNote" cssErrorClass="fieldErrorCls"
								theme="simple" value="%{#pruefungsleistung[#current].note}"
								disabled="%{isWriteable(#pruefungsleistung[#current].id)}" /> <s:fielderror
								theme="iaa">
								<s:param>pruefungenBeans[${stat.index}].note${current}</s:param>
							</s:fielderror></td>
					</s:iterator>

					<%-- <td class="editorField"><s:textfield
							name="pruefungenBeans[%{#stat.index}].note1"
							cssClass="changeNote" cssErrorClass="fieldErrorCls"
							theme="simple" value="%{#pruefung.note1}"
							disabled="%{#pruefung.writeable[0]}" /> <s:fielderror
							theme="iaa">
							<s:param>pruefungenBeans[${stat.index}].note1</s:param>
						</s:fielderror></td>
					<td class="editorField"><s:textfield
							name="pruefungenBeans[%{#stat.index}].note2"
							cssClass="changeNote" cssErrorClass="fieldErrorCls"
							theme="simple" value="%{#pruefung.note2}"
							disabled="%{#pruefung.writeable[1]}" /> <s:fielderror
							theme="iaa">
							<s:param>pruefungenBeans[${stat.index}].note2</s:param>
						</s:fielderror></td>
					<td class="editorField"><s:textfield
							name="pruefungenBeans[%{#stat.index}].note3"
							cssClass="changeNote" cssErrorClass="fieldErrorCls"
							theme="simple" value="%{#pruefung.note3}"
							disabled="%{#pruefung.writeable[2]}" /> <s:fielderror theme="iaa">
							<s:param>pruefungenBeans[${stat.index}].note3</s:param>
						</s:fielderror></td> --%>
				</tr>
			</s:iterator>
		</table>
		<s:submit value="Speichern" />
	</form>
</s:if>
<s:else>
	<h4>Keine Studenten für diese Auswahl gepflegt!</h4>
</s:else>