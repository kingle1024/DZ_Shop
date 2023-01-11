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
</head>
<body>
<h1>주문 관리</h1>
<form name="searchForm" id="searchForm">
    <label for="search"></label><input type="text" placeholder="검색할 제목 입력" id="search" name="search" autofocus>
    <input type="hidden" id="pageIndex" name="pageIndex" >
    <input type="hidden" id="pageSize" name="pageSize">
    <input type="submit" value="검색">
</form>

<table class="table">
    <thead>
        <tr>
            <th scope="col">이미지</th>
            <th scope="col">상품명</th>
            <th scope="col">가격</th>
            <th scope="col">주문</th>
            <th scope="col">주문일자</th>
        </tr>
    </thead>
    <tbody id="tbody">
    <c:forEach var="list" items="${list }">
        <tr>
            <td><img width="50px" height="50px" src="${pageContext.request.contextPath}/admin/product/thumbnail.do?no=${list.product_no}" alt=" " /></td>
            <td>${list.title}</td>
            <td>${list.price}</td>
            <td>${list.status}</td>
            <td>${list.register_date}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div id="pager">${pager}</div>

<script>

</script>
</body>
</html>
