<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="https://cdn.ckeditor.com/ckeditor5/35.3.2/classic/ckeditor.js"></script>
<script src="https://cdn.ckeditor.com/ckeditor5/35.3.2/classic/translations/ko.js"></script>
<script src="https://code.jquery.com/jquery-2.2.4.min.js" integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN" crossorigin="anonymous"></script>
<style>
    /* 넓이 높이 조절 */
    .ck.ck-editor {
        max-width: 1000px;
    }
    .ck-editor__editable {
        min-height: 300px;
    }

</style>
<table border=0  width="100%">
  <tr>
     <td>
		<a href="<c:url value='/'/>">
			<img src="<c:url value='/resources/image/duke_swing.gif'/>"  />
		</a>
     </td>
     <td>
       <h1><font size=30>스프링실습 홈페이지!!</font></h1>
     </td>
     
     <td>
       <c:choose>
          <c:when test="${isLogOn == true  && member != null}">
            <h3>환영합니다. ${member.name }님!</h3>
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
