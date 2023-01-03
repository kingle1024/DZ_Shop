<%--
  Created by IntelliJ IDEA.
  User: ejy1024
  Date: 2022/01/03
  Time: 8:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <script src="https://code.jquery.com/jquery-2.2.4.min.js" integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous" >
    </script>
</head>
<body>
<a href="${pageContext.request.contextPath}/admin/product/add">상품등록</a><br/>

<form name="searchForm" id="searchForm">
    <label for="search"></label><input type="text" placeholder="검색할 제목 입력" id="search" name="search" autofocus>
    <input type="hidden" id="type" name="type" value="${type}">
    <input type="hidden" id="pageIndex" name="pageIndex" >
    <input type="hidden" id="pageSize" name="pageSize">
    <input type="submit" value="검색">
</form>

<table>
    <thead>
        <tr>
            <td>이미지</td>
            <td>상품명</td>
            <td>가격</td>
            <td>수정</td>
        </tr>
    </thead>
    <tbody id="tbody">
    <c:forEach var="list" items="${list }">
        <tr>
            <td><img src="${pageContext.request.contextPath}/admin/product/thumbnail.do?no=${list.no}" alt=" " /></td>
            <td>${list.title}</td>
            <td>${list.price}</td>
            <td><button class="editMode" data-id="${list.no}">수정</button></td>
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

        fetch('${pageContext.request.contextPath}/api/admin/product/search?search='+search+'&pageIndex='+pageIndex)
            .then(response => response.json())
            .then(jsonResult => {
                $("#tbody").empty();
                let html = "";
                let product = jsonResult.list;
                for(let key in product){
                    html += "<tr>";
                    html += "<td><img src='" + product[key].thumbnail + "' alt=' ' /></td>";
                    html += "<td>" + product[key].title + "</td>";
                    html += "<td>" + product[key].price + "</td>";
                    html += "<td><button class='editMode' data-id='" + product[key].no + "'>수정</button></td>";
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
    $(".editMode").on("click", e => {
        e.preventDefault();
        let link = e.target;
        let userId = link.getAttribute("data-id");

        location.href = '${pageContext.request.contextPath}/admin/product/view/'+userId;
    });
</script>
</body>
</html>
