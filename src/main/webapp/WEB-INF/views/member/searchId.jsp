<%--
  Created by IntelliJ IDEA.
  User: ejy1024
  Date: 2022/12/05
  Time: 2:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>아이디 찾기</title>
    <script src="https://code.jquery.com/jquery-2.2.4.min.js" integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous"></script>
</head>
<body>
    아이디 찾기 <br/>

    name : <input type="text" name="searchId_name" id="searchId_name"> <br/>
    email : <input type="text" name="searchId_email" id="searchId_email"> <br/>

    <input type="button" id="searchId" name="searchId" value="인증번호 받기">
<script>
    let idButton = document.querySelector("#searchId");
    idButton.onclick = (event) => {
        event.preventDefault();
        let params = {
            "name" : document.getElementById("searchId_name").value,
            "email" : document.getElementById("searchId_email").value
        }
        fetch('${pageContext.request.contextPath}/api/member/searchId', {
            method : 'POST',
            headers : {
                'Content-Type' : 'application/json;charset=utf-8'
            },
            body : JSON.stringify(params)
        })
            .then(response => response.json())
            .then(jsonResult => {
                alert(jsonResult.message);
            });
    }
</script>
</body>
</html>
