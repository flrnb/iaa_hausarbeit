<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<h3>Hauptmenü</h3>

<s:a action="show" namespace="/pruefungAnlegen">Prüfung anlegen</s:a><br /><br />

<s:a action="modulSelection" namespace="/writtenExams">Prüfungsleistungen eintragen</s:a><br />
<s:a action="modulSelection" namespace="/oralExams">Ergänzungsprüfungen eintragen</s:a><br /><br />
<s:a action="modulSelection" namespace="/editExams">Prüfungsleistungen ändern</s:a><br /><br />

<s:a action="show" namespace="/overview">Übersicht anzeigen</s:a><br />
<s:a action="select" namespace="/history">Historie anzeigen</s:a><br />