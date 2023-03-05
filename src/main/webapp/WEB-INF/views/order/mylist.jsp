<%--
  Created by IntelliJ IDEA.
  User: ejy1024
  Date: 2022/01/03
  Time: 8:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>주문 목록</h1>
<table class="table">
    <thead>
        <th scope="col">#</th>
        <th scope="col">상품명</th>
        <th scope="col">갯수</th>
        <th scope="col">가격</th>
        <th scope="col">주문 가격</th>
        <th scope="col">구매일</th>
        <th scope="col">배송상태</th>
    </thead>
    <tbody>
    <c:forEach var="list" items="${list}">
    <tr name="tr" data-id="${list.no}">
        <td>
            <img width="50px" height="300px" class="card-img-top" src="${pageContext.request.contextPath}/product/thumbnail.do?no=${list.product_no}" alt=" " />
        </td>
        <td>${list.title}</td>
        <td>${list.cnt}</td>
        <td>${list.price}</td>
        <td>${list.price * list.cnt}</td>
        <td>${list.register_date}</td>
        <td>
            <a href="javascript:openPop('${list.no}')">
                <div>
            <c:choose>
                <c:when test="${list.status == 'READY'}">
                    배송준비중
                </c:when>
                <c:when test="${list.status == 'SENDING'}">
                    배송중
                </c:when>
                <c:when test="${list.status == 'FINISH'}">
                    배송완료
                </c:when>

                <c:otherwise>
                    ${list.status}
                </c:otherwise>
            </c:choose>
                </div>
            </a>
        </td>
    </tr>
    </c:forEach>
    </tbody>
</table>
<div class="popup_layer" id="popup_layer" style="display: none;">
    <div class="popup_box">
        <div class="popup_btn" style="float: top; margin-bottom: 0px;">
            <a href="javascript:closePop();">주문서 상세</a>
        </div>
        <!--팝업 컨텐츠 영역-->
        <div class="popup_cont">
            <%--            <h5 id="pop_title">주문서 상세</h5>--%>
            <p id="pop_content">
            <h4>주문정보</h4>
            <table class="table">
                <thead>
                <tr>
                    <td>상품명</td>
                    <td>상품금액</td>
                    <td>갯수</td>
                    <td>주문상태</td>
                </tr>
                </thead>
                <tbody id="pb_tbody"></tbody>
            </table>
            <h4>배송정보</h4>
            <table class="table">
                <tr>
                    <td>받으시는 분</td>
                    <td id="pb_receiver"></td>
                </tr>
                <tr>
                    <td>연락처</td>
                    <td id="pb_phone"></td>
                </tr>
                <tr>
                    <td>주소</td>
                    <td id="pb_address"></td>
                </tr>
            </table>
            </p>
        </div>
        <!--팝업 버튼 영역-->
        <div class="popup_btn" style="float: bottom; margin-bottom: 0px;">
            <a href="javascript:closePop();">닫기</a>
        </div>
    </div>
</div>
</body>
</html>
<script>
    function openPop(productNo) {
        let params = {
            'no' : productNo
        }
        document.getElementById("popup_layer").style.display = "block";
        fetch('${pageContext.request.contextPath}/api/admin/order/detail', {
            method : 'POST',
            headers : {
                'Content-Type' : 'application/json; charset=utf-8'
            },
            body : JSON.stringify(params)
        })
            .then(response => response.json())
            .then(jsonResult => {
                let detail = jsonResult.detail;
                console.log(jsonResult.detail);
                document.getElementById("pop_content").innerHTML = '<img width="90%" src="/DZ_shop_war_exploded/admin/product/thumbnail.do?no='+detail.product_no+'" alt=" ">';
                let html = "";
                html += "<tr>";
                html += "<td>"+detail.title+"</td>";
                html += "<td>"+detail.price+"</td>";
                html += "<td>"+detail.cnt+"</td>";
                html += "<td>"+detail.status+"</td>";
                html += "</tr>";
                document.getElementById("pb_tbody").innerHTML = html;

                document.getElementById("pb_receiver").innerHTML = detail.receiver;
                let phone = detail.tel1 + "-" + detail.tel2 + "-" + detail.tel3;
                document.getElementById("pb_phone").innerHTML = phone;
                document.getElementById("pb_address").innerHTML = detail.address;
            });
    }

    //팝업 닫기
    function closePop() {
        document.getElementById("popup_layer").style.display = "none";
    }
</script>
<style>
    /*popup*/
    .popup_layer {position:fixed;top:0;left:0;z-index: 10000; width: 100%; height: 100%; background-color: rgba(0, 0, 0, 0.4); }
    /*팝업 박스*/
    .popup_box{position: relative;top:50%;left:50%; overflow: auto; height: 600px; width:375px;transform:translate(-50%, -50%);z-index:1002;box-sizing:border-box;background:#fff;box-shadow: 2px 5px 10px 0px rgba(0,0,0,0.35);-webkit-box-shadow: 2px 5px 10px 0px rgba(0,0,0,0.35);-moz-box-shadow: 2px 5px 10px 0px rgba(0,0,0,0.35);}
    /*컨텐츠 영역*/
    .popup_box .popup_cont {padding:30px;line-height:1.4rem;font-size:14px; }
    .popup_box .popup_cont h2 {padding:15px 0;color:#333;margin:0;}
    .popup_box .popup_cont p{ border-top: 1px solid #666;padding-top: 30px;}
    /*버튼영역*/
    .popup_box .popup_btn {display:table;table-layout: fixed;width:100%;height:70px;background:#ECECEC;word-break: break-word;}
    .popup_box .popup_btn a {position: relative; display: table-cell; height:70px;  font-size:17px;text-align:center;vertical-align:middle;text-decoration:none; background:#ECECEC;}
    .popup_box .popup_btn a:before{content:'';display:block;position:absolute;top:26px;right:29px;width:1px;height:21px;background:#fff;-moz-transform: rotate(-45deg); -webkit-transform: rotate(-45deg); -ms-transform: rotate(-45deg); -o-transform: rotate(-45deg); transform: rotate(-45deg);}
    .popup_box .popup_btn a:after{content:'';display:block;position:absolute;top:26px;right:29px;width:1px;height:21px;background:#fff;-moz-transform: rotate(45deg); -webkit-transform: rotate(45deg); -ms-transform: rotate(45deg); -o-transform: rotate(45deg); transform: rotate(45deg);}
    .popup_box .popup_btn a.close_day {background:#5d5d5d;}
    .popup_box .popup_btn a.close_day:before, .popup_box .popup_btn a.close_day:after{display:none;}

    /*오버레이 뒷배경*/
    .popup_overlay{position:fixed;top:0px;right:0;left:0;bottom:0;z-index:1001;;background:rgba(0,0,0,0.5);}
    /*popup*/
</style>