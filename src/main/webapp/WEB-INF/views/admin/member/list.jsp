<%--
  Created by IntelliJ IDEA.
  User: ejy1024
  Date: 2022/12/31
  Time: 8:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    </script>
</head>
<body>
<div style="margin-left: auto; margin-right: 0;">
    <form name="searchForm" id="searchForm" action="" method="get">
        <label for="search"></label><input type="text" placeholder="검색할 제목 입력" id="search" name="search" autofocus>
    <%--    <input type="hidden" id="type" name="type" value="${type}">--%>
        <input type="hidden" id="pageIndex" name="pageIndex" >
        <input type="hidden" id="pageSize" name="pageSize">
        <input type="submit" value="검색" value="${param.search}">
    </form>
</div>
<table class="table">
    <thead>
        <tr>
            <th scope="col">아이디</th>
            <th scope="col">이름</th>
            <th scope="col">회원 상태 변경</th>
            <th scope="col">삭제</th>
        </tr>
    </thead>
    <tbody id="tbody">
    <c:forEach var="list" items="${list }">
        <tr>
            <th scope="row">${list.userId}</th>
            <td>${list.name}</td>
            <td>
                <button class="userStatusUids btn btn-warning" data-status="${list.userStatus}" data-uid="${list.userId}">
                    ${list.userStatus == 'STOP' ? '사용' : '미사용'}으로 변경
                </button>
            </td>
            <td><button class="userDel btn btn-danger" data-uid="${list.userId}">삭제</button></td>
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
                    html += "<th scope='col'>" + member[key].userId + "</th>";
                    html += "<td>" + member[key].name + "</td>";
                    let userStatusTxt = member[key].userStatus == 'STOP' ? '사용' : '미사용';
                    html += "<td>"
                        + "<button class='userStatusUids btn btn-warning' data-status='"+member[key].userStatus+"' data-uid='"+member[key].userId+"'>"
                        + userStatusTxt +"으로 변경"
                        + "</button>"
                        + "</td>";
                    html += '<td><button class="userDel btn btn-danger" data-uid="'+member[key].userId+'">삭제</button></td>';
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

    $(".userDel").on("click", e => {
        e.preventDefault();
        let link = e.target;
        if(!confirm("삭제하시겠습니까?")) return;

        let userId = link.getAttribute("data-uid");

        fetch('${pageContext.request.contextPath}/api/admin/del?userId='+userId)
            .then(response => response.json())
            .then(jsonResult => {
                console.log(jsonResult);
                if(jsonResult.status === true) {
                    link.parentNode.parentNode.remove();
                    alert("삭제되었습니다.");
                }else{
                    alert("삭제 실패");
                }
            });
    });
</script>
</body>
</html>
