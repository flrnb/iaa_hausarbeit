<%-- Author: Christopher Biel, Ibrahim Karagac --%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<div id="hautpmenueschrift"></div>

<s:a action="show" namespace="/pruefungAnlegen">
	<div id="link_pruefungAnlegen"></div>
</s:a>

<s:a action="modulSelection" namespace="/writtenExams">
	<div id="link_writtenExams"></div>
</s:a>
<s:a action="modulSelection" namespace="/oralExams">
	<div id="link_oralExams"></div>
</s:a>
<s:a action="modulSelection" namespace="/editExams">
	<div id="link_editExams"></div>
</s:a>

<s:a action="show" namespace="/overview">
	<div id="link_overview"></div>
</s:a>
<s:a action="select" namespace="/history">
	<div id="link_history"></div>
</s:a>