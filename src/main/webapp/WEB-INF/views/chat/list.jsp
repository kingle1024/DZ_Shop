<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<html>
<head>
	<script type="text/javascript" src="<c:url value='/resources/js/sockjs-0.3.4.js'/>"></script>
	<script type="text/javascript">
		let roomName = '${roomName}';
		window.addEventListener("load", e => {
			document.querySelector("#mForm").addEventListener("submit", e => {
				e.preventDefault();
				let params = {
					'roomName' :roomName,
					'message' : message.value
				}

				let p = document.createElement("p");
				p.innerHTML = "me > " +message.value;
				// data.append(p);
				console.log("client send ");
				console.log(JSON.stringify(params));
				sock.send(JSON.stringify(params));
				// sock.send(param);
				message.value = '';
				message.focus();

				return false;
			});
		});

		//웸소켓을 지정한 url로 연결한다.
		var sock = new SockJS("<c:url value="/echo"/>");

		sock.onopen = evt => {
			console.log(evt);
		}

		//자바스크립트 안에 function을 집어넣을 수 있음.
		//데이터가 나한테 전달되읐을 때 자동으로 실행되는 function
		sock.onmessage = evt => {
			let response = JSON.parse(evt.data);
			console.log(response);
			console.log(response.roomName +" : " +roomName);
			if(response.roomName === roomName) {
				console.log("in");
				let p = document.createElement("p");
				p.innerHTML = evt.data;
				// data.append(p);
			}
		}

		//데이터를 끊고싶을때 실행하는 메소드
		sock.onclose = evt => {
			console.log(evt);
			data.append("연결 끊김");
		}
	</script>
</head>
<body>
<h2>일반인 채팅 - ${roomName}</h2>
<img src="https://image.fmkorea.com/files/attach/new2/20220520/6042994/3564131214/4636129222/74204d8ac6546bf135bf215f55877286.png"><br/>

<form name="mForm" id="mForm">
	<input type="text" id="message" />
	<input type="submit" value="전송" />
</form>
<div id="data"></div>

</body>
</html>
