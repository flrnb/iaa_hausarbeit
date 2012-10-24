<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<table class="collapsibleTable" border="1">
	<tr>
		<td class="c-header">
			Titel
		</td>
		<td align="right">
			<input id="toggleButton" type="image" class="toggleButton" onclick="javascript:togglePanel(this)"/>
		</td>
	</tr>
	<tr>
		<td class="c-content collapsed" colspan="2">
			Content
		</td>
	</tr>
</table>