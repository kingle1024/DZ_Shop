<%--
  Created by IntelliJ IDEA.
  User: ejy1024
  Date: 2022/01/04
  Time: 9:39 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
    window.onload = function (){
        const myState = '${myStatus}';
        if(myState === 'like'){
            $("#like").css("backgroundColor", "#008CBA");
        }else if(myState === 'dislike') {
            $("#dislike").css("backgroundColor", "#f44336");
        }
    }
</script>

<img class="card-img-top" width="10" height="300px" src="${pageContext.request.contextPath}/product/thumbnail.do?no=${product.no}" alt=" " /><br/>
<c:choose>
    <c:when test="${isLogin == true}">
<button id="like">좋아유
    <span id="likeCount">${product.like_count}</span>
</button>
<button id="dislike">싫어유
    <span id="dislikeCount">${product.dislike_count}</span>
</button><br/>
    </c:when>
    <c:otherwise>
            좋아유<span id="likeCount">${product.like_count}</span> |
            싫어유<span id="dislikeCount">${product.dislike_count}</span>
    </c:otherwise>
</c:choose>
<br/>
상품명 : ${product.title} <br/>
내용 : ${product.content} <br/>
가격 : ${product.price} <br/><br/>
<button id="buy" data-no="${product.no}">구매하기</button>
<button id="basket" data-no="${product.no}">장바구니</button>

<h3>파일</h3>
<c:forEach var="list" items="${files}" varStatus="status">
    <a href="${pageContext.request.contextPath}/product/fileDownload.do?fid=${list.f_id}">${list.org_name}</a> <br/>
</c:forEach>
<input type="hidden" id="product_no" name="product_no" value="${product.no}">

<br/>
<h3>상품 문의</h3>
<table class="table">
    <thead>
        <tr>
            <td>답변상태</td>
            <td>문의제목</td>
            <td>작성자</td>
            <td>작성일자</td>
        </tr>
    </thead>
    <tbody id="tbody">
    <c:forEach var="detail" items="${cs}">
            <tr>
                <td>
                    <button onclick="showDetail(this, '${detail.parent_no}')">
                        <c:choose>
                            <c:when test="${detail.status eq 'READY'}">
                                미답변
                            </c:when>
                            <c:when test="${detail.status eq 'DONE'}">
                                답변완료
                            </c:when>
                            <c:otherwise>
                                미답변2
                            </c:otherwise>
                        </c:choose>
                    </button>
                </td>
                <td>${detail.comment}</td>
                <td><c:if test="${detail.parent_no != detail.no }">ㄴ</c:if>
                        ${detail.writer}
                            (${detail.writer_id})
                </td>
                <td>${detail.register_date}</td>
                <td>
<%--                    <c:if test="${comment.parent_no == comment.no}">--%>
<%--                    <button class="reply" data-id="${comment.no}">[답글]</button>--%>
<%--                    </c:if>--%>
                </td>
            </tr>
    </c:forEach>
    </tbody>
</table>

<div>

<textarea <c:if test="${isLogin != true}">disabled</c:if> class="form-control" id="comment" rows="3"></textarea>
<button id="add">문의하기</button>
</div>
<script>
    function showDetail(e, parentNo){
        console.log(e.parentNode.parentNode);
        console.log(parentNo);

        fetch('${pageContext.request.contextPath}/api/comment/detail?parentNo='+parentNo)
            .then(response => response.json())
            .then(jsonResult => {
                console.log("result");
                // console.log(jsonResult.list);
                let html = "";
                // selectedEls.forEach((el) => {
                //     array.push(el.getAttribute("data-id"))
                // });

                jsonResult.list.forEach((x) => {
                    html += "<tr>";
                    html += "<td>"+ x.writer +" </td>";
                    html += "</tr";
                    html += "<tr>";
                    html += "<td>"+ x.comment +" </td>";
                    html += "</tr";
                });

                console.log(html);
                console.log(jsonResult);
            });
    }
</script>
<script>
    $(".reply").on("click", e => {
        let link = e.target;
        let parent_no = link.getAttribute("data-id");
        console.log(parent_no);
        let param = {
            'comment' : 'test',
            'parent_no' : parent_no,
            'product_no' : product_no.value
        }
        console.log(param);

        fetch('${pageContext.request.contextPath}/api/comment/add', {
            method : 'POST',
            headers : {
                'Content-Type' : 'application/json;charset=utf-8'
            },
            body: JSON.stringify(param)
        })
        .then(response => response.json())
        .then(jsonResult => {
            // TODO 댓글 입력 폼 나와야함

        });
    });

    $("#add").on("click", e => {
        let isLogin = '${isLogin}';
        console.log(isLogin);
        if(isLogin == ''){
            alert("로그인 후 이용 가능합니다.");
            return false;
        }

        e.preventDefault();

        let param = {
            'comment' : comment.value,
            'product_no' : ${product.no}
        }
        console.log(param);

        fetch('${pageContext.request.contextPath}/api/comment/add', {
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
                td1.innerHTML = "<button>미답변</button>";

                let td2 = document.createElement("td");
                td2.innerHTML = comment.value;

                console.log(jsonResult.comment);

                let td3 = document.createElement("td");
                td3.innerHTML = jsonResult.comment.writer+"("+jsonResult.comment.writer_id+")";

                let td4 = document.createElement("td");
                td4.innerHTML = jsonResult.register_date;

                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                tr.appendChild(td4);

                document.querySelector("#tbody").prepend(tr);
        });
    });
</script>
<script>
    let likeButton = document.querySelector("#like");
    likeButton.onclick = () => {
        let btype = "like";
        let params = {
            "bno" : ${product.no},
            "type" : btype
        }
        boardPopularity(params, btype);
        likeButton.style.backgroundColor = '#008CBA'; // blue
    }

    let dislikeButton = document.querySelector("#dislike");
    dislikeButton.onclick = () => {
        let btype = "dislike";
        let params = {
            "bno" : ${product.no},
            "type" : btype
        }
        boardPopularity(params, btype);
        dislikeButton.style.backgroundColor = '#f44336'; // red
    }

    function boardPopularity(params, btype){
        fetch('${pageContext.request.contextPath}/api/product/popularity', {
            method : 'POST',
            headers : {
                'Content-Type' : 'application/json;charset=utf-8'
            },
            body : JSON.stringify(params)
        })
            .then(response => response.json())
            .then(jsonResult => {
                alert(jsonResult.message);
                let count;

                if(jsonResult.status === "add"){
                    if(btype === "like"){
                        count = document.getElementById("likeCount");
                        count.textContent = parseInt(count.textContent) +1;
                    }else{
                        count = document.getElementById("dislikeCount");
                        count.textContent = parseInt(count.textContent) +1;
                    }
                } else if(jsonResult.status === "cancel"){
                    if(btype === "like"){
                        count = document.getElementById("likeCount");
                        count.textContent = parseInt(count.textContent) -1;
                        count.style.backgroundColor = "";
                        document.getElementById("like").style.backgroundColor='';
                    }else{
                        count = document.getElementById("dislikeCount");
                        count.textContent = parseInt(count.textContent) -1;
                        count.style.backgroundColor = "";
                        document.getElementById("dislike").style.backgroundColor='';
                    }
                } else if(jsonResult.status === "change"){
                    if(btype === "like"){
                        let likeCnt = document.getElementById("likeCount");
                        let dislikeCnt = document.getElementById("dislikeCount");

                        likeCnt.textContent = (parseInt(likeCnt.textContent) +1);
                        dislikeCnt.textContent = (parseInt(dislikeCnt.textContent) -1);
                        document.getElementById("dislike").style.backgroundColor='';
                    }else{
                        let likeCnt = document.getElementById("likeCount");
                        let dislikeCnt = document.getElementById("dislikeCount");

                        likeCnt.textContent = (parseInt(likeCnt.textContent) -1);
                        dislikeCnt.textContent = (parseInt(dislikeCnt.textContent) +1);
                        document.getElementById("like").style.backgroundColor='';
                    }
                }
            });
    }
</script>
<script>
    let buyButton = document.querySelector("#buy");
    let buyNo = buyButton.getAttribute("data-no");
    buyButton.onclick = () => {
        let userId = document.querySelector("#sessionUserId");
        if(userId == null){
            if(confirm("로그인이 필요합니다. 로그인 페이지로 이동하시겠습니까?")){
                location.href="${pageContext.request.contextPath}/member/loginForm.do";
            }
            return;
        }

        fetch('${pageContext.request.contextPath}/api/product/basket?no='+buyNo)
            .then(response => response.json())
            .then(jsonResult => {
                if(jsonResult.status === true){
                    alert("장바구니에 추가되었습니다.");
                }else{
                    alert("장바구니 추가 실패");
                }
            });


    }
</script>
<script>
    let basketButton = document.querySelector("#basket");
    let basketNo = buyButton.getAttribute("data-no");
    basketButton.onclick = () => {
        let userId = document.querySelector("#sessionUserId");
        if(userId == null){
            if(confirm("로그인이 필요합니다. 로그인 페이지로 이동하시겠습니까?")){
                location.href="${pageContext.request.contextPath}/member/loginForm.do";
            }
            return;
        }

        fetch('${pageContext.request.contextPath}/api/basket/add?no='+basketNo)
            .then(response => response.json())
            .then(jsonResult => {
                if(jsonResult.status === true){
                    alert("장바구니에 추가되었습니다.");
                }else{
                    alert("장바구니 추가 실패");
                }
            });
    }
</script>