<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<h3>Hauptmenü</h3>

<s:a action="modulSelection" namespace="writtenExams">Prüfungsleistungen eintragen</s:a><br />
<s:a action="show" namespace="oralExams">Ergänzungsprüfungen eintragen</s:a><br />
<s:a action="show" namespace="editExams">Prüfungsleistungen ändern</s:a><br />