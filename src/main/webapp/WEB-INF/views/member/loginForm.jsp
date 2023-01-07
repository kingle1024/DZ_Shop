<%@ page contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<title>일반 게시판</title>
	<script src="https://code.jquery.com/jquery-2.2.4.min.js" integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous"></script>
</head>
<body>
<form name="loginForm" id="loginForm" action="" method="post">
	아이디 : <input type="text" name="userId" id="userId"> <br/>	
	비밀번호 : <input type="password" name="pwd" id="pwd"> <br/>
	<input type="submit">
</form>
<a href="${pageContext.request.contextPath}/member/searchId.do">아이디 찾기</a><br/>
<a href="${pageContext.request.contextPath}/member/searchPwd.do">패스워드 찾기</a><br/>
<a href="${pageContext.request.contextPath}/member/register.do">회원가입</a>

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
			if(jsonResult.status === false){
				alert("아이디 및 패스워드가 다릅니다.");
				return;
			}else {
				location.href = jsonResult.href;
			}
		});

	});

</script>
</body>
</html>
