<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>


<s:if test="pruefungsfaecher == null">
	<h4>
		Keine Module gepflegt für aktuellen Manipel<br />
		<s:a action="" namespace="/">Zurück zum Hauptmenü</s:a>
	</h4>
</s:if>
<s:else>
	<s:iterator value="pruefungsfaecher" var="pruefungsfach">
		<a
			href="<s:url action="pruefungSelection"/>?pruefungsfach=<s:property value="%{#pruefungsfach.getId()}"/>">Modul
			<s:property value="%{#pruefungsfach.toString()}" />
		</a>
		<br />
	</s:iterator>
</s:else>