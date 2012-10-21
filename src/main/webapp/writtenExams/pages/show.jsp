<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<h3>
	Prüfungsergebnisse erfassen (Modul
	<i><s:property value="selectedPruefungsfach" /></i>
	)
</h3>
<s:a action="modulSelection" namespace="writtenExams">Modul wechseln</s:a>
<p>Hier können Sie die erreichten Noten von dem Modul eingetragen
	werden.</p>
<form action="save">
	<table class="notenTabelle" border="1">
		<tr>
			<th>id</th>
			<th>matrikelnummer</th>
			<th>name</th>
			<th>alte noten</th>
			<th>neue note</th>
		</tr>
		<s:iterator value="pruefungen" var="pruefung">
			<tr>
				<td><s:property value="%{#pruefung.examId}" /></td>
				<td><s:property value="%{#pruefung.matrikelNummer}" /></td>
				<td><s:property value="%{#pruefung.name}" /></td>
				<td><s:property value="%{#pruefung.alteNoten}" /></td>
				<td><s:textfield theme="simple" cssClass="notenInputField" /></td>
			</tr>
		</s:iterator>
	</table>
	<s:submit value="Speichern" />
</form>