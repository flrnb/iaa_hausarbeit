<%-- Author: Christopher Biel --%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<h3>
	Ergänzungsprüfungen erfassen (Modul <i><s:property
			value="selectedPruefungsfach" /></i> )
</h3>

<s:a action="modulSelection" namespace="/oralExams">Modul wechseln</s:a>

<p>Hier können Sie die erreichten Prozentanteile an den mündlichen
	Ergänzungsprüfungen eintragen.</p>

<s:if test="pruefungenBeans.size() > 0">
	<s:form action="save" method="post">
		<input type="hidden" name="pruefungsfach"
			value="<s:property value="pruefungsfach"/>" />
		<table class="notenTabelle" border="1">
			<tr>
				<th>Datum der<br />Prüfung
				</th>
				<th>Datum der<br />Ergänzungsprüfung
				</th>
				<th>Matrikelnummer</th>
				<th>Name</th>
				<th>Prozent</th>
			</tr>
			<s:iterator value="pruefungenBeans" var="pruefung" status="stat">
				<s:hidden name="pruefungenBeans[%{#stat.index}].student.matrikelNr"
					value="%{#pruefung.student.matrikelNr}" />
				<s:hidden name="pruefungenBeans[%{#stat.index}].datum"
					value="%{#pruefung.datum}" />
				<tr>
					<td><s:property value="#pruefung.datum" /></td>
					<td><sx:datetimepicker cssErrorClass="fieldErrorCls"
							name="pruefungenBeans[%{#stat.index}].ergDatum"
							displayFormat="dd-MM-yyyy" value="%{#pruefung.ergDatum}" /> <s:fielderror
							theme="iaa">
							<s:param>pruefungenBeans[${stat.index}].ergDatum</s:param>
							<br />
						</s:fielderror></td>
					<td><s:property value="#pruefung.student.matrikelNr" /></td>
					<td><s:property value="#pruefung.student" /></td>
					<td class="editorField"><s:textfield
							cssErrorClass="fieldErrorCls"
							name="pruefungenBeans[%{#stat.index}].resultPercent"
							theme="simple" cssClass="notenInputField" /> <s:fielderror
							theme="iaa">
							<s:param>pruefungenBeans[${stat.index}].resultPercent</s:param>
							<br />
						</s:fielderror></td>
				</tr>
			</s:iterator>
		</table>
		<s:submit value="Speichern" />
	</s:form>
</s:if>
<s:else>
	<h4>Es gibt keine Studenten für die eine Ergänzungsprüfung in diesem Fach eingetragen werden kann!</h4>
</s:else>