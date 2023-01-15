<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<html>
<head>
	<script type="text/javascript" src="<c:url value='/resources/js/sockjs-0.3.4.js'/>"></script>
	<script type="text/javascript">
		let roomName = '123';
		window.addEventListener("load", e => {
			document.querySelector("#mForm").addEventListener("submit", e => {
				e.preventDefault();
				let params = {
					'roomName' :roomName,
					'message' : message.value
				}

				let p = document.createElement("p");
				p.innerHTML = "me > " +message.value;
				console.log("admin send ");
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
			console.log("admin ")
			let response = JSON.parse(evt.data);
			// document.getElementById("log").innerHTML += response;
			console.log(response);
			console.log(response.roomName +" : " +roomName);
			document.getElementById(response.roomName).innerHTML += "[상대방] "+response.message +"<br/>";
		}

		//데이터를 끊고싶을때 실행하는 메소드
		sock.onclose = evt => {
			console.log(evt);
			console.log("연결 끊김");
			document.getElementById("data").innerHTML = "연결 끊김";
		}
	</script>
</head>
<body>
<h1>채팅방 목록</h1>
<div id="data"></div>

<%--<img src="https://image.fmkorea.com/files/attach/new2/20220520/6042994/3564131214/4636129222/74204d8ac6546bf135bf215f55877286.png"><br/>--%>

<c:forEach var="list" items="${list}">
	<a href="${pageContext.request.contextPath}/admin/chat/view?chatRoom=${list.chat_room}">${list.chat_room}</a> <br/>
	<div class="card">
		<h5 class="card-header">채팅방 이름 : ${list.chat_room}</h5>
		<div class="card-body" id="${list.chat_room}">

		</div>
		<div>
			<input type="text"><button data-room="${list.chat_room}" onclick="send(this)">전송</button>
		</div>
	</div>
	<br/>
</c:forEach>

<form name="mForm" id="mForm">
<%--	<input type="text" id="message" />--%>
<%--	<input type="submit" value="전송" />--%>
</form>

<div id="log"></div>
<script>
	let idButton = document.querySelector(".sendMsg");
	idButton.onclick = (event) => {
		console.log(idButton.getAttribute("data-room"));
	}
</script>
<script>
	function send(event){
		let roomName = event.getAttribute("data-room");
		let message = event.parentNode.childNodes.item(1);
		// console.log(event.parentNode.childNodes.item(1).value);

		let params = {
			'roomName' :roomName,
			'message' : message.value
		}

		console.log("send");
		console.log(params);
		sock.send(JSON.stringify(params));

		document.getElementById(roomName).innerHTML += "[me] "+message.value +"<br/>";
		message.value = '';
		message.focus();
	}
</script>
</body>
</html>
