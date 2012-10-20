<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<h3>Ergänzungsprüfungen erfassen</h3>
<p>Hier können Sie die erreichten Prozentanteile an den mündlichen
	Ergänzungsprüfungen eintragen.</p>

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
