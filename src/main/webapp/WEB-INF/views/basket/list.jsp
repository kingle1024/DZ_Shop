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
<h2>장바구니 목록</h2>


<table class="table">
    <tbody id="tbody">
    <c:forEach var="list" items="${list}">
    <tr>
        <td><input type="checkbox" name="basketList" data-id="${list.product_no}" checked></td>
        <td><img width="50px" height="300px" class="card-img-top" src="${pageContext.request.contextPath}/product/thumbnail.do?no=${list.product_no}" alt=" " /></td>
        <td>${list.title}</td>
        <td><button>-</button> ${list.cnt} <button>+</button></td>
        <td><span name="price">${list. cnt * list.price}</span> <button> X </button></td>
    </tr>
    </c:forEach>
    </tbody>
</table>
<form action="${pageContext.request.contextPath}/order/prepareOrder" id="form" method="post">
    <input type="hidden" name="checkList" id="checkList">
    <button id="orderButton">주문하기</button>
</form>

<script>
    document.querySelector("#orderButton").onclick = (event) => {
        event.preventDefault();
        let query = 'input[name="basketList"]:checked';
        let selectedEls = document.querySelectorAll(query);
        let array = new Array();
        selectedEls.forEach((el) => {
           // result += el.getAttribute("data-id")+",";
           array.push(el.getAttribute("data-id"))
        });
        document.querySelector("#checkList").value = array;
        document.getElementById("form").submit();
    }
</script>
</body>
</html>
