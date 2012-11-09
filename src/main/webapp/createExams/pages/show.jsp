<%-- Author: Christopher Biel --%>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"
	language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>

<h3>Neue Prüfung anlegen</h3>

<s:form action="save" method="post">
	<table border="0">
		<tr>
			<td align="center" valign="top" colspan="2"><s:fielderror
					theme="iaa" fieldName="formPruefungsfach">
				</s:fielderror></td>
			<td><s:select list="pruefungsfaecher" name="formPruefungsfach"
					listKey="id" listValue="titel" label="Prüfungsfach" headerKey="-1"
					headerValue="---Select---" cssErrorClass="fieldErrorCls" /></td>
		</tr>
		<tr>
			<td align="center" valign="top" colspan="2"><s:fielderror
					theme="iaa" fieldName="formDozent">
				</s:fielderror></td>
			<td><s:select list="dozenten" name="formDozent" listKey="id"
					label="Dozent" headerKey="-1" headerValue="---Select---"
					cssErrorClass="fieldErrorCls" /></td>
		</tr>
		<tr>
			<td><sx:datetimepicker name="formDate"
					displayFormat="dd-MM-yyyy" label="Datum der Prüfung" /></td>
		</tr>
		<tr>
			<td><s:submit value="Anlegen" /></td>
		</tr>
	</table>
</s:form>