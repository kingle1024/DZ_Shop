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

<table class="table">
    <thead>
        <tr>
            <th scope="col">no</th>
            <th scope="col">작성자</th>
            <th scope="col">문의</th>
            <th scope="col">답변여부</th>
        </tr>
    </thead>
    <tbody id="tbody">
    <c:forEach var="list" items="${list }">
        <tr>
            <th scope="row">${list.no}</th>
            <td>${list.writer}(${list.writer_id})</td>
            <td>${list.comment}</td>
            <td>${list.register_date}</td>
            <td>
                <a href="${pageContext.request.contextPath}/admin/comment/view?no=${list.no}">
                    <c:choose>
                        <c:when test="${list.status eq 'READY'}">
                            미답변
                        </c:when>
                        <c:when test="${list.status eq 'DONE'}">
                            답변완료
                        </c:when>
                        <c:otherwise>
                            미답변2
                        </c:otherwise>
                    </c:choose>
                </a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<%--<div id="pager">${pager}</div>--%>
<%--<script>--%>
<%--    $("#searchForm").on('submit', (event) => {--%>
<%--        event.preventDefault();--%>
<%--        searchF();--%>
<%--    });--%>
<%--</script>--%>
<%--<script>--%>
<%--    function searchF(index){--%>
<%--        let start = new Date();--%>
<%--        let search = document.getElementById("search").value;--%>
<%--        let pageIndex = document.getElementById("pageIndex").value;--%>
<%--        if(index !== undefined){--%>
<%--            pageIndex = index;--%>
<%--        }--%>

<%--        fetch('${pageContext.request.contextPath}/api/admin/search?search='+search+'&pageIndex='+pageIndex)--%>
<%--            .then(response => response.json())--%>
<%--            .then(jsonResult => {--%>
<%--                $("#tbody").empty();--%>
<%--                let html = "";--%>
<%--                let member = jsonResult.list;--%>
<%--                for(let key in member){--%>
<%--                    html += "<tr>";--%>
<%--                    html += "<th scope='col'>" + member[key].userId + "</th>";--%>
<%--                    html += "<td>" + member[key].name + "</td>";--%>
<%--                    html += "</tr>";--%>
<%--                }--%>
<%--                document.querySelector("#tbody").innerHTML = html;--%>

<%--                html = jsonResult.pager;--%>

<%--                $("#pager").empty();--%>
<%--                document.querySelector("#pager").innerHTML = html;--%>
<%--                let end = new Date();--%>
<%--                console.log("실행기간 : ");--%>
<%--                console.log(end-start);--%>
<%--            });--%>
<%--    }--%>
<%--</script>--%>
</body>
</html>
