<%-- Author: Christopher Biel --%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>


<s:if test="pruefungen == null || pruefungen.isEmpty()">
	<h3>
		Keine Prüfungen vorhanden für aktuelles Prüfungsfach<br />
		<s:a action="modulSelection">Zurück zum Modulauswahl</s:a>
	</h3>
</s:if>
<s:else>
	<h3>
		Prüfungen für das Prüfungsfach "
		<s:property value="%{getSelectedPruefungsfach().toString()}" />
		"
	</h3>
	<s:iterator value="pruefungen" var="pruefung">
		<a
			href="<s:url action="show"/>?pruefungsfach=<s:property value="%{getSelectedPruefungsfach().getId()}"/>&pruefung=<s:property value="%{#pruefung.getId()}" />">Prüfung
			vom <s:property value="%{#pruefung.toString()}" />
		</a>
		<br />
	</s:iterator>
</s:else>