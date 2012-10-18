<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

Show

<s:form action="OralExams/save">

<s:textfield  name="exams[0].text"></s:textfield>
<s:textfield name="exams[1].text"></s:textfield>
<s:submit value="test"></s:submit>

</s:form>
