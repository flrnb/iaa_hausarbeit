<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<h4>
	Übersicht (Manipel
	<s:property value="getSelectedManipel()" />
	)
</h4>

<s:iterator value="ergebnisse" var="ergebnis">

	<table class="collapsibleTable">
		<tr class="c-header">
			<td width="100%"><s:property value="#ergebnis.key" /></td>
			<td align="right">
				<button id="toggleButton" class="toggleButton"
					onclick="javascript:togglePanel(this)">Toggle</button>
			</td>
		</tr>
		<tr>
			<td class="c-content collapsed" colspan="2">
				<table style="margin: auto; width: 90%; border: 0px" border="1">
					<tr>
						<th align="left">Name</th>
						<th align="left">Note</th>
					</tr>
					<s:iterator value="#ergebnis.value" var="row">
					<tr>
						<td><s:property value="#row.key"/></td>
						<td align="center"><s:property value="#row.value.getNote()"/></td>
					</tr>
					</s:iterator>
				</table>
			</td>
		</tr>
	</table>

</s:iterator>