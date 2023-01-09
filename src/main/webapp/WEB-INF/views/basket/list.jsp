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
<button id="updatePwd">주문하기</button>

<script>
    document.querySelector("#updatePwd").onclick = (event) => {
        event.preventDefault();
        let checkbox = $("input:checkbox[name=basketList]:checked");
        let idList = "";
        let nameList = "";
        checkbox.each(function (i) {
            let tr = checkbox.parent().parent().eq(i);
            // console.log(tr);
            let td = tr.children();
            console.log(td);
            let name = td.eq(3).text();
            let userId = td.eq(4).text();

            nameList += name + "/";
            idList += userId + "/";
        });

        console.log(idList);
        console.log(nameList);
    }
</script>
</body>
</html>
