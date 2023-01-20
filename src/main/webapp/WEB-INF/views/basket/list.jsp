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
        <td><input type="checkbox" name="basketList" data-id="${list.no}" checked></td>
        <td><img width="50px" height="300px" class="card-img-top" src="${pageContext.request.contextPath}/product/thumbnail.do?no=${list.product_no}" alt=" " /></td>
        <td>${list.title}</td>
        <td>
            <button class="minusCnt" data-type="minus" data-product="${list.product_no}" data-id="${list.no}" data-cnt="${list.cnt}">-</button> <span class="cnt">${list.cnt}</span>
            <button class="plusCnt" data-type="plus" data-product="${list.product_no}" data-id="${list.no}" data-cnt="${list.cnt}">+</button>
        </td>
        <td><span name="price">${list. cnt * list.price}</span> <button class="delButton" data-id="${list.no}"> X </button></td>
    </tr>
    </c:forEach>
    </tbody>
</table>
<form action="${pageContext.request.contextPath}/order/prepareOrder" id="form" method="post">
    <input type="hidden" name="checkNoList" id="checkNoList">
    <button id="orderButton">주문하기</button>
</form>

<script>
    document.querySelector("#orderButton").onclick = (event) => {
        event.preventDefault();
        let query = 'input[name="basketList"]:checked';
        let selectedEls = document.querySelectorAll(query);
        let array = [];
        selectedEls.forEach((el) => {
            array.push(el.getAttribute("data-id"))
        });
        document.querySelector("#checkNoList").value = array;
        document.getElementById("form").submit();
    }
</script>
<script>
    let minusCnt = document.querySelector(".minusCnt");
    let plusCnt = document.querySelector(".plusCnt");
    edit(minusCnt);
    edit(plusCnt);
    function edit(editButton){
        editButton.addEventListener("click", (e) => {
            let link = e.target;
            let no = link.getAttribute("data-id");
            let type = link.getAttribute("data-type");
            let product_no = link.getAttribute("data-product");
            console.log("===========");
            console.log(product_no);
            let cnt = document.querySelector(".minusCnt").parentNode.children.item(1);

            let params = {
                'no' : no,
                'type' : type,
                'cnt' : cnt.innerHTML,
                'product_no' : product_no
            }
            console.log(params);

            fetch('${pageContext.request.contextPath}/api/basket/edit', {
                method : 'POST',
                headers : {
                    'Content-Type' : 'application/json; charset=utf-8'
                },
                body : JSON.stringify(params)
            })
                .then(response => response.json())
                .then(jsonResult => {
                    console.log(jsonResult);

                    cnt.innerHTML = jsonResult.cnt;
                    document.querySelector(".minusCnt").parentNode.parentNode.children.item(4).children.item(0).innerHTML = jsonResult.price;

                });
        });
    }
</script>

<script>
    let delButton = document.querySelector(".delButton");
    delButton.addEventListener("click", (e) => {
        let link = e.target;
        let dataId = link.getAttribute("data-id");

        let params = {
            'no' : dataId
        }
        console.log(params);

        fetch('${pageContext.request.contextPath}/api/basket/del?no='+dataId)
            .then(response => response.json())
            .then(jsonResult => {
                console.log(jsonResult);
                link.parentNode.parentNode.remove()
                alert('성공');
            });
    });
</script>
</body>
</html>
