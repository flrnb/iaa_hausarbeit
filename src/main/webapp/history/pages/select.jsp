<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"
	language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<s:form action="show">

	<sx:autocompleter list="pruefungsfaecher" listValue="titel"  label="" listKey="id" name="formPruefungsfach" autoComplete="false" forceValidOption="true"/>
	
	<sx:autocompleter list="studenten" listKey="id" label="" name="formStudent" autoComplete="false" forceValidOption="true"/>
	
	<s:submit/>

</s:form>