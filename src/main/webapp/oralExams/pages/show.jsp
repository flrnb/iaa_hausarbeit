<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<h3>
	Ergänzungsprüfungen erfassen (Modul <i><s:property
			value="selectedPruefungsfach" /></i> )
</h3>

<s:a action="modulSelection" namespace="/oralExams">Modul wechseln</s:a>

<p>Hier können Sie die erreichten Prozentanteile an den mündlichen
	Ergänzungsprüfungen eintragen.</p>

<s:if test="#pruefungenBeans.size() > 0">
	<s:form action="save">
		<input type="hidden" name="pruefungsfach"
			value="<s:property value="pruefungsfach"/>" />
		<input type="hidden" name="pruefung"
			value="<s:property value="pruefung"/>" />
		<table class="notenTabelle" border="1">
			<tr>
				<th>Matrikelnummer</th>
				<th>Name</th>
				<th>Prozent</th>
			</tr>
			<s:iterator value="pruefungenBeans" var="pruefung" status="stat">
				<s:hidden name="pruefungenBeans[%{#stat.index}].student.matrikelNr"
					value="%{#pruefung.student.matrikelNr}" />
				<tr>
					<td><s:property value="#pruefung.matrikelNummer" /></td>
					<td><s:property value="#pruefung.name" /></td>
					<td class="editorField"><s:textfield
							name="pruefungenBeans[%{#stat.index}].resultPercent"
							theme="simple" cssClass="notenInputField" />
						<s:fielderror theme="iaa">
							<s:param>pruefungenBeans[${stat.index}].resultPercent</s:param>
						</s:fielderror></td>
				</tr>
			</s:iterator>
		</table>
		<s:submit value="Speichern" />
	</s:form>
</s:if>
<s:else>
	<h4>Keine Studenten für diese Auswahl gepflegt!</h4>
</s:else>