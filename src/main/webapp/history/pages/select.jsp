<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"
	language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<s:actionerror/>

<s:form action="show">
	<sx:autocompleter list="pruefungsfaecher" listValue="titel"  label="Prüfungsfach wählen" listKey="id" name="formPruefungsfach" autoComplete="false" forceValidOption="true"/>
	<br />
	<sx:autocompleter list="studenten" listKey="id" label="Studenten wählen" name="formStudent" autoComplete="false" forceValidOption="true"/>
	<s:submit/>
</s:form>