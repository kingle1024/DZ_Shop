<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>


<html>
<head>
</head>
<body>
<h2>주문 댓글 상세보기</h2>
<img src="https://image.fmkorea.com/files/attach/new2/20220520/6042994/3564131214/4636129222/74204d8ac6546bf135bf215f55877286.png"><br/>
${comment}
<br/>
${detail}
<h3>문의</h3>
<table class="table">
    <tr>
        <td>작성자</td>
        <td>${comment.writer}(${comment.writer_id})</td>
    </tr>
    <tr>
        <td>등록일</td>
        <td>${comment.register_date}</td>
    </tr>
    <tr>
        <td>문의</td>
        <td>${comment.comment}</td>
    </tr>
</table>

<h3>답변</h3>
<table class="table">
    <tbody id="tbody">
    <c:forEach var="detail" items="${detail }">
        <tr>
            <td>${detail.writer}(${detail.writer_id})</td>
            <td>${detail.comment}</td>

            <td>${detail.register_date}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<div>
    <textarea class="form-control" id="comment" rows="3"></textarea>
    <button id="add">답변하기</button>
</div>
<script>
    $("#add").on("click", e => {
        e.preventDefault();

        let param = {
            'comment' : comment.value,
            'product_no' : ${comment.product_no},
            'parent_no' : ${comment.parent_no}
        }

        console.log(param);

        fetch('${pageContext.request.contextPath}/api/comment/reply', {
            method : 'POST',
            headers : {
                'Content-Type' : 'application/json;charset=utf-8'
            },
            body: JSON.stringify(param)
        })
            .then(response => response.json())
            .then(jsonResult => {
                // alert(jsonResult);
                // $("#tbody").empty();
                console.log(jsonResult.param_comment);
                let tr = document.createElement("tr");

                let td1 = document.createElement("td");
                td1.innerHTML = jsonResult.comment.writer+"(" + jsonResult.comment.writer_id + ")";

                let td2 = document.createElement("td");
                td2.innerHTML = comment.value;

                let td3 = document.createElement("td");
                td3.innerHTML = jsonResult.comment_register_date;

                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);

                document.querySelector("#tbody").prepend(tr);
            });
    });
</script>
</body>
</html>
