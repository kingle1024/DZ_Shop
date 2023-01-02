<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>
<br/>
<c:if test="sessionName != ''">
	${sessionName}님, 안녕하세요! <br/>
</c:if>
${sessionName}님, 안녕하세요! <br/>
<img src="resources/image/duke.png">
</body>
</html>
