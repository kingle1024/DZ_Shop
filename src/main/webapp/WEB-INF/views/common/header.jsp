<%@ page contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table border=0  width="100%">
  <tr>
     <td>
		<a href="<c:url value='/'/>">
			<img src="<c:url value='/resources/image/duke_swing.gif'/>"  />
		</a>
     </td>
     <td>
       <h1><font size=30>DZ SHOP!!</font></h1>
     </td>
     
     <td>
       <c:choose>
          <c:when test="${isLogin == true}">
            <h3>환영합니다. ${sessionName}님!</h3>
            <a href="<c:url value='/member/logout.do'/>"><h3>로그아웃</h3></a>
          </c:when>
          <c:otherwise>
	        <a href="<c:url value='/member/loginForm.do'/>"><h3>로그인</h3></a>
              <a href="<c:url value='/member/register.do'/>"><h3>회원가입</h3></a>
	      </c:otherwise>
	   </c:choose>     
     </td>
  </tr>
</table>

