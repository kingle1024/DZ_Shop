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

<img class="card-img-top" width="10" height="10" src="${pageContext.request.contextPath}/product/thumbnail.do?no=${product.no}" alt=" " /><br/>
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
가격 : ${product.price} <br/>

<br/>
<h3>댓글</h3>
<table class="table">
    <tbody id="tbody">
    <c:forEach var="comment" items="${cs}">
            <tr>
                <td><c:if test="${comment.parent_no != comment.no }">ㄴ</c:if>
                        ${comment.writer}
                            (${comment.writer_id})
                </td>
                <td>${comment.comment}</td>
                <td>${comment.register_date}</td>
                <td>
                    <c:if test="${comment.parent_no == comment.no}">
                    <button class="reply" data-id="${comment.no}">[답글]</button>
                    </c:if>
                </td>
            </tr>
    </c:forEach>
    </tbody>
</table>

<div>
<textarea class="form-control" id="comment" rows="3"></textarea>
<button id="add">버튼</button>
</div>

<script>
    $(".reply").on("click", e => {
        let link = e.target;
        let parent_no = link.getAttribute("data-id");
        console.log(parent_no);
        let param = {
            'comment' : 'test',
            'parent_no' : parent_no,
            'product_no' : '${product.no}'
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
                td1.innerHTML = jsonResult.param_comment.writer+"(" + jsonResult.param_comment.writer_id + ")";

                let td2 = document.createElement("td");
                td2.innerHTML = comment.value;

                let td3 = document.createElement("td");
                td3.innerHTML = jsonResult.register_date;

                let td4 = document.createElement("td");
                td4.innerHTML = '<td><button class="reply" data-id="'+jsonResult.param_comment.no+'">[답글]</button></td>';

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