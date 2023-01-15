<%@ page contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 카카오 스크립트 -->
<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
<table border=0  width="100%" style="text-align: center">
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
            <h3 id="sessionUserId">환영합니다. ${sessionName}님!</h3>
            <a href="<c:url value='/member/logout.do'/>"><h3>로그아웃</h3></a>
<%--              <li onclick="kakaoLogout();">--%>
<%--                  <a href="javascript:void(0)">--%>
<%--                      <span>카카오 로그아웃</span>--%>
<%--                  </a>--%>
<%--              </li>--%>
              <a href="<c:url value='/basket/list' />">장바구니</a><br/>
              <a href="<c:url value='/order/mylist' />">구매목록</a><br/>

          </c:when>

          <c:otherwise>
	        <a href="<c:url value='/member/loginForm.do'/>"><h3>로그인</h3></a>
              <a href="<c:url value='/member/register.do'/>"><h3>회원가입</h3></a>
	      </c:otherwise>
	   </c:choose>

     </td>
  </tr>
</table>

<script>
    //카카오로그아웃
    function kakaoLogout() {
        if (Kakao.Auth.getAccessToken()) {
            Kakao.API.request({
                url: '/v1/user/unlink',
                success: function (response) {
                    console.log(response)
                },
                fail: function (error) {
                    console.log(error)
                },
            })
            Kakao.Auth.setAccessToken(undefined)
        }
    }
</script>