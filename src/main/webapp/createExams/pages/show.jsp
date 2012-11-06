<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"
	language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<h3>Neue Prüfung anlegen</h3>

<s:form action="save" method="post">
	<sx:autocompleter name="formPruefungsfach" autoComplete="false"
		forceValidOption="true" label="Prüfungsfach" list="pruefungsfaecher"
		listKey="id" listValue="titel" />
	<br />

	<sx:autocompleter name="formDozent" autoComplete="false"
		forceValidOption="true" label="Dozent" list="dozenten" listKey="id" />
	<br />

	<sx:datetimepicker name="formDate" displayFormat="dd-MM-yyyy"
		label="Datum der Prüfung" />
	<br />
	<s:submit />
</s:form>