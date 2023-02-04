<%--
  Created by IntelliJ IDEA.
  User: ejy1024
  Date: 2022/12/05
  Time: 2:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <h2>비밀번호 찾기</h2>

    <input type="text" name="userId" id="userId" value="" placeholder="아이디"> <br/>
    <input type="text" name="email" id="email" value="" placeholder="이메일 주소(@포함)"> <br/><br/>
    <input type="button" class="btn btn-primary" id="searchPwd" name="searchId" value="인증번호 받기"><br/><br/>

    <div id="divRetValue" style="display:none">
        <input type="text" name="value" id="value" placeholder="인증번호 입력"><br/><br/>
        <input type="hidden" name="retValue" id="retValue">
        <input type="button" class="btn btn-primary" id="checkValue" name="checkValue" value="확인"><br/><br/>
    </div>
    <div id="divPassword" style="display:none">
        <input type="password" id="pwd" name="pwd" placeholder="비밀번호 입력"> <br/>
        <input type="password" id="rePwd" name="rePwd" placeholder="비밀번호 재입력"> <br/><br/>
        <input type="button" class="btn btn-primary" id="updatePwd" name="updatePwd" value="비밀번호 변경"><br/>
    </div>
<script>
    let idButton = document.querySelector("#searchPwd");
    idButton.onclick = (event) => {
        let params = {
            "userId" : document.getElementById("userId").value,
            "email" : document.getElementById("email").value
        }

        fetch('${pageContext.request.contextPath}/api/member/searchPwd', {
            method : 'POST',
            headers : {
                'Content-Type' : 'application/json; charset=utf-8'
            },
            body : JSON.stringify(params)
        })
            .then(response => response.json())
            .then(jsonResult => {
                alert(jsonResult.message);
                if(jsonResult.status === true) {
                    document.querySelector("#divRetValue").style.display = "";
                }
            });
    }

    let validButton = document.querySelector("#checkValue");
    validButton.onclick = (event) => {
        let params = {
            "value" : document.getElementById("value").value,
            "email" : document.getElementById("email").value
        }

        fetch('${pageContext.request.contextPath}/api/member/checkValue', {
            method : 'POST',
            headers : {
                'Content-Type' : 'application/json; charset=utf-8'
            },
            body : JSON.stringify(params)
        })
        .then(response => response.json())
        .then(jsonResult => {
            if(jsonResult.status === true){
                document.querySelector("#retValue").value = jsonResult.retValue;
                document.querySelector("#divPassword").style.display="";
            }
            alert(jsonResult.message);
        });
    }

    document.querySelector("#updatePwd").onclick = (event) => {
        let pwd = document.getElementById("pwd").value;
        let rePwd = document.getElementById("rePwd").value;
        if(pwd === ''){
            alert("비밀번호를 입력해주세요.");
            return;
        }else if(pwd !== rePwd){
            alert("비밀번호가 서로 다릅니다.");
            return;
        }

        let param = {
            "retValue" : document.getElementById("retValue").value,
            "email" : document.getElementById("email").value,
            "userId" : document.getElementById("userId").value,
            "pwd" : document.getElementById("pwd").value
        }

        fetch('${pageContext.request.contextPath}/api/member/password', {
            method : 'POST',
            headers : {
                'Content-Type' : 'application/json; charset=utf-8'
            },
            body : JSON.stringify(param)
        })
        .then(response => response.json())
        .then(jsonResult => {
            alert(jsonResult.message);
            if(jsonResult.status === true){
                location.href = jsonResult.url;
            }
        });
    }
</script>
