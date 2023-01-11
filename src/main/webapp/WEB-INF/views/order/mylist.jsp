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
<h1>주문 목록</h1>
<table class="table">
    <thead>
        <th scope="col">#</th>
        <th scope="col">상품명</th>
        <th scope="col">갯수</th>
        <th scope="col">가격</th>
        <th scope="col">주문 가격</th>
        <th scope="col">구매일</th>
        <th scope="col">배송상태</th>
    </thead>
    <tbody>
    <c:forEach var="list" items="${list}">
    <tr name="tr" data-id="${list.no}">
        <td>
            <img width="50px" height="300px" class="card-img-top" src="${pageContext.request.contextPath}/product/thumbnail.do?no=${list.product_no}" alt=" " />
        </td>
        <td>${list.title}</td>
        <td>${list.cnt}</td>
        <td>${list.price}</td>
        <td>${list.price * list.cnt}</td>
        <td>${list.register_date}</td>
        <td>
            <c:choose>
                <c:when test="${list.status == 'READY'}">
                    배송준비중
                </c:when>

                <c:otherwise>
                    에러
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
