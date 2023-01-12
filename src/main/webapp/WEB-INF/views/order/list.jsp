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

<h3>주문상품</h3>

<table class="table">
    <thead>
        <th>#</th>
        <th>상품명</th>
        <th>갯수</th>
        <th>가격</th>
    </thead>
    <tbody>
    <c:forEach var="list" items="${list}">
    <tr name="tr" data-id="${list.pb_no}">
        <td>#</td>
        <td>${list.title}</td>
        <td>${list.pb_cnt}</td>
        <td>${list.price * list.pb_cnt}</td>
    </tr>
    </c:forEach>
    </tbody>
</table>

<h3>배송정보</h3>
<input class="form-control" type="text" id="receiver" value="${member.name}"><br/>
<input class="form-control" type="text" id="address" name="address" value="서울시"><br/>
<input type="text" id="tel1" value="010">-<input type="text" id="tel2" value="1234">-<input type="text" id="tel3" value="5678"><br/>

<input type="hidden" id="order_sheet" value="${order_sheet}"><br/>
<button class="btn btn-primary" id="buyButton">주문하기</button>
<script>
    let buyButton = document.querySelector("#buyButton");
    buyButton.addEventListener("click", (e) => {
        let tr = document.getElementsByName("tr");
        let array = [];
        tr.forEach((el) => {
           array.push(el.getAttribute("data-id"));
        });

        let params = {
            'address' : document.querySelector("#address").value,
            'order_sheet' : document.querySelector("#order_sheet").value,
            'checkNoList' : array,
            'tel1' : document.getElementById("tel1").value,
            'tel2' : document.getElementById("tel2").value,
            'tel3' : document.getElementById("tel3").value,
            'receiver' : document.getElementById("receiver").value
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


</script>
</body>
</html>
