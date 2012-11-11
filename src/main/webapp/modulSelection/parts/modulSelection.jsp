<%-- Author: Christopher Biel --%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>


<s:if test="pruefungsfaecher == null">
	<h3>
		Keine Module gepflegt für aktuellen Manipel<br />
		<s:a action="" namespace="/">Zurück zum Hauptmenü</s:a>
	</h3>
</s:if>
<s:else>
	<h3>
		Modul auswahlen (Manipel "
		<s:property value="%{getSelectedManipel().toString()}" />
		")
	</h3>
	<s:iterator value="pruefungsfaecher" var="pruefungsfach">
		<a
			href="
			<s:if test="getCurrentNamespace() == '/oralExams' || getCurrentNamespace() == '/editExams'">
				<s:url action="show"/>
			</s:if><s:else>
				<s:url action="pruefungSelection"/>
			</s:else>
			?pruefungsfach=<s:property value="%{#pruefungsfach.getId()}"/>">Modul
			<s:property value="%{#pruefungsfach.toString()}" />
		</a>
		<br />
	</s:iterator>
</s:else>