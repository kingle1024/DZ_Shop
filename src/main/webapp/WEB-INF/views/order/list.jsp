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
<h1>주문 폼</h1>

<h2>주문상품</h2>

<table>
    <thead>
        <th>상품명</th>
        <th>갯수</th>
        <th>가격</th>
    </thead>
    <tbody>
    <c:forEach var="list" items="${list}">
    <tr name="tr" data-id="${list.no}">
        <td>${list.title}</td>
        <td>${list.cnt}"></td>
        <td>${list.price * list.cnt}</td>
    </tr>
    </c:forEach>
    </tbody>
</table>

<h2>배송정보</h2>
<input type="text" id="address" name="address" value="서울시">
<input type="hidden" id="order_sheet" value="${order_sheet}">
<button id="buyButton">주문하기</button>
<script>
    let buyButton = document.querySelector("#buyButton");
    buyButton.addEventListener("click", (e) => {
        let tr = document.getElementsByName("tr");
        let array = new Array();
        tr.forEach((el) => {
           array.push(el.getAttribute("data-id"));
        });

        let params = {
            'address' : document.querySelector("#address").value,
            'order_sheet' : document.querySelector("#order_sheet").value,
            'checkList' : array
        }
        console.log(params);

        fetch('${pageContext.request.contextPath}/api/order/add', {
            method : 'POST',
            headers : {
                'Content-Type' : 'application/json; charset=utf-8'
            },
            body : JSON.stringify(params)
        })
            .then(response => response.json())
            .then(jsonResult => {
                console.log(jsonResult);
                alert('성공');
                location.href = jsonResult.url;
            });
    });


    <%--let buyForm = document.querySelector("#buyButton");--%>

    <%--buyForm.addEventListener("submit", (e) => {--%>
    <%--    e.preventDefault();--%>

    <%--    fetch('${pageContext.request.contextPath}/api/order/add', {--%>
    <%--        method : 'POST',--%>
    <%--        cache : 'no-cache',--%>
    <%--        body : new FormData(buyForm)--%>
    <%--    })--%>
    <%--        .then(response => response.json())--%>
    <%--        .then(jsonResult => {--%>
    <%--            if(jsonResult.status === true)--%>
    <%--                alert("구매 성공");--%>
    <%--        });--%>

    <%--});--%>

</script>
</body>
</html>
