<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form name="loginForm" id="loginForm" action="" method="post">
	아이디 : <input type="text" name="userId" id="userId"> <br/>	
	비밀번호 : <input type="password" name="pwd" id="pwd"> <br/>
	<input type="submit">
</form>

<script>
	let form = document.querySelector("#loginForm");
	form.addEventListener('submit', (event) => {
		event.preventDefault();
		var userId = document.getElementById("userId").value;
		var password = document.getElementById("pwd").value;

		let param = {
			"userId" : userId,
			"pwd" : password
		}

		fetch('${pageContext.request.contextPath}/api/member/login', {
			method : 'POST',
			headers : {
				'Content-Type' : 'application/json; charset=utf-8'
			},
			body : JSON.stringify(param)
		}).then(response => response.json())
		.then(jsonResult => {
			alert(jsonResult)
		});

	});

</script>