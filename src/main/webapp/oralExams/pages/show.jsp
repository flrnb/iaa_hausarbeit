<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

Show

<s:form action="save">
	<table>
		<tr>
			<th>ID</th>
			<th>Matrikelnummer</th>
			<th>Name</th>
			<th>Prozent</th>
		</tr>
		<s:iterator value="exams" var="row">
			<tr>
				<td><s:property value="#row.examId" /></td>
				<td><s:property value="exams[#row.index].matrikelNummer" /></td>
				<td><s:property value="%{#exam.name}" /></td>
				<td><s:textfield name="resultPercent"
						value="%{#exams.resultPercent}" /></td>
			</tr>
		</s:iterator>
	</table>
	<s:submit value="test"></s:submit>
</s:form>
