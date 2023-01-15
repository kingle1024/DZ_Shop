<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>
<head>
 <style>
   .no-underline{
      text-decoration:none;
   }
 </style>
  <meta charset="UTF-8">
  <title>사이드 메뉴</title>
</head>
<body>
	<h1>사이드 메뉴</h1>
	<h1>
		<c:if test="${sessionIsAdmin eq true}">
			<a href="${pageContext.request.contextPath}/admin/member/list"  class="no-underline">[1] 회원 관리</a><br><br/>
			<a href="${pageContext.request.contextPath}/admin/product/list"  class="no-underline">[2] 상품 관리</a><br><br/>
			<a href="${pageContext.request.contextPath}/admin/order/list"  class="no-underline">[3] 주문 관리</a><br><br/>
			<a href="${pageContext.request.contextPath}/admin/comment/list"  class="no-underline">[4] 문의 관리</a><br><br/>
			<a href="${pageContext.request.contextPath}/admin/chat/list"  class="no-underline">[5] 관리자 채팅</a><br>
		</c:if>
		<a href="${pageContext.request.contextPath}/chat/list"  class="no-underline">채팅 문의</a><br><br/>
	</h1>
</body>
</html>