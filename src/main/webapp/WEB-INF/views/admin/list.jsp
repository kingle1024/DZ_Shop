<%--
  Created by IntelliJ IDEA.
  User: ejy1024
  Date: 2022/12/31
  Time: 8:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <script src="https://code.jquery.com/jquery-2.2.4.min.js" integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous" >
    </script>
</head>
<body>
<form name="searchForm" id="searchForm" action="" method="get">
    <label for="search"></label><input type="text" placeholder="검색할 제목 입력" id="search" name="search" autofocus>
    <input type="hidden" id="type" name="type" value="${type}">
    <input type="hidden" id="pageIndex" name="pageIndex" >
    <input type="hidden" id="pageSize" name="pageSize">
    <input type="submit" value="검색" value="${param.search}">
</form>

<table>
    <thead>
        <tr>
            <td>아이디</td>
            <td>이름</td>
            <td>회원 상태 변경</td>
        </tr>
    </thead>
    <tbody id="tbody">
    <c:forEach var="list" items="${list }">
        <tr>
            <td>${list.userId}</td>
            <td>${list.name}</td>
            <td>${list.userStatus}</td>
            <td>
                ${list.userStatus}
                <button class="userStatusUids" data-status="${list.userStatus}" data-uid="${list.userId}">
                    ${list.userStatus == 'STOP' ? '사용' : '미사용'}으로 변경
                </button>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div id="pager">${pager}</div>
<script>
    $("#searchForm").on('submit', (event) => {
        event.preventDefault();
        searchF();
    });
</script>
<script>
    function searchF(index){
        let start = new Date();
        let search = document.getElementById("search").value;
        let pageIndex = document.getElementById("pageIndex").value;
        if(index !== undefined){
            pageIndex = index;
        }

        fetch('${pageContext.request.contextPath}/api/admin/search?search='+search+'&pageIndex='+pageIndex)
            .then(response => response.json())
            .then(jsonResult => {
                $("#tbody").empty();
                let html = "";
                let member = jsonResult.list;
                for(let key in member){
                    html += "<tr>";
                    html += "<td>" + member[key].userId + "</td>";
                    html += "<td>" + member[key].name + "</td>";
                    html += "</tr>";
                }
                document.querySelector("#tbody").innerHTML = html;

                html = jsonResult.pager;

                $("#pager").empty();
                document.querySelector("#pager").innerHTML = html;
                let end = new Date();
                console.log("실행기간 : ");
                console.log(end-start);
            });
    }
</script>
<script>
    $(".userStatusUids").on("click", e => {
        e.preventDefault();
        let link = e.target;
        if(!confirm("변경하시겠습니까?")) return;

        let userId = link.getAttribute("data-uid");
        let userStatus = link.getAttribute("data-status");

        fetch('${pageContext.request.contextPath}/api/admin/userStatus?userId='+userId+"&userStatusParam="+userStatus)
            .then(response => response.json())
            .then(jsonResult => {
                link.setAttribute('data-status', jsonResult.userStatus);
                if(jsonResult.userStatus === "USE"){
                    link.textContent = "미사용으로 변경";
                }else{
                    link.textContent = "사용으로 변경";
                }
                alert("변경되었습니다.");
            });
    });
</script>
</body>
</html>
