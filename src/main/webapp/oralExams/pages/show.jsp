<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<h3>Ergänzungsprüfungen erfassen (Modul <i><s:property
			value="selectedPruefungsfach" /></i> )</h3>

<s:a action="modulSelection" namespace="writtenExams">Modul wechseln</s:a>

<p>Hier können Sie die erreichten Prozentanteile an den mündlichen
	Ergänzungsprüfungen eintragen.</p>

<s:if test="#pruefungenBeans.size() > 0">
	<s:form action="save">
		<table class="notenTabelle" border="1">
			<tr>
				<th>ID</th>
				<th>Matrikelnummer</th>
				<th>Name</th>
				<th>Prozent</th>
			</tr>
			<s:iterator value="exams" var="exam">
				<tr>
					<td><s:property value="#exam.value.examId" /></td>
					<td><s:property value="#exam.value.matrikelNummer" /></td>
					<td><s:property value="#exam.value.name" /></td>
					<td><s:textfield name="resultPercent"
							value="%{#exam.value.resultPercent}" theme="simple"
							cssClass="notenInputField" /></td>
				</tr>
			</s:iterator>
		</table>
		<s:submit value="Speichern" />
	</s:form>
</s:if>
<s:else>
	<h4>Keine Studenten für diese Auswahl gepflegt!</h4>
</s:else>