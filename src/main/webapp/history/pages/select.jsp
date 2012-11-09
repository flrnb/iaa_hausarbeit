<%-- Author: Christopher Biel --%>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"
	language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<h3>Historie anzeigen</h3>

<s:actionerror/>

<s:form action="show" method="post">
	
	<s:actionmessage cssClass="actionMessage" theme="simple"/>

	<sx:autocompleter list="pruefungsfaecher" listValue="titel"  label="Prüfungsfach wählen" listKey="id" name="formPruefungsfach" autoComplete="true" forceValidOption="true" value=""/>
	<br />
	<sx:autocompleter list="studenten" listKey="id" label="Studenten wählen" name="formStudent" autoComplete="true" forceValidOption="true" value=""/>
	<s:submit value="Anzeigen"/>
</s:form>
