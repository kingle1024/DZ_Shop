<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>


<html>
<head>

</head>
<body>
<P>  The time on the server is ${serverTime}. </P>

<img src="https://image.fmkorea.com/files/attach/new2/20220520/6042994/3564131214/4636129222/74204d8ac6546bf135bf215f55877286.png"><br/>
<form name="searchForm" id="searchForm">
	<label for="search"></label><input type="text" placeholder="검색할 제목 입력" id="search" name="search" autofocus>
	<input type="hidden" id="pageIndex" name="pageIndex" >
	<input type="hidden" id="pageSize" name="pageSize">
	<input type="submit" value="검색">
</form>
<c:forEach var="list" items="${list}" varStatus="status">
	<c:if test="${(status.index) % 3 == 0}" >
	<div class="card-group">
	</c:if>

	<div class="card">
		<img width="50px" height="300px" class="card-img-top" src="${pageContext.request.contextPath}/product/thumbnail.do?no=${list.no}" alt=" " />
		<div class="card-body">
			<h5 class="card-title">${list.title}</h5>
			<i class="fa-solid fa-thumbs-up"></i>${list.like_count}  |
			<i class="fa-solid fa-thumbs-down"></i>${list.dislike_count}
			<p class="card-text">${list.content}</p>
			<p class="card-text">
				<small class="text-muted">
				<a href="${pageContext.request.contextPath}/product/view/${list.no}" class="btn btn-primary">상세 보기</a>
				</small>
			</p>
		</div>
	</div>

	<c:if test="${(status.index+1) % 3 == 0}">
		</div>
	</c:if>
</c:forEach>
	<br/>
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
						html += "<td><img width='50px' height='50px' src='" + product[key].thumbnail + "' alt=' ' /></td>";
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
