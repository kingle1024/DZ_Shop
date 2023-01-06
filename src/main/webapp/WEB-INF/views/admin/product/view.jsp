<%--
  Created by IntelliJ IDEA.
  User: ejy1024
  Date: 2022/01/03
  Time: 8:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form id="uploadForm" method="post" enctype="multipart/form-data">
    <img src="${pageContext.request.contextPath}/admin/product/thumbnail.do?no=${product.no}" alt=" " /><br/>
    <input type="file" name="thumbnail" id="thumbnail"> <br/>

    <input type="text" name="title" value="${product.title}"> <br/>
    <input type="text" name="price" value="${product.price}"> <br/>
    <input type="hidden" name="no" value="${product.no}">
    <input type="hidden" id="delFiles" name="delFiles" value="">
    <textarea name="editor" id="editor">${product.content}</textarea> <br/>
    <c:forEach var="list" items="${files}" varStatus="status">
        ${list.org_name} <button class="fileDel" name="fileDel" data-id="${list.f_id}">삭제</button> <br/>
    </c:forEach>

    <table>
        <tbody id="fileBody">
        <tr>
            <th><label>첨부파일</label></th>
            <td><input type="file" name="filename"/></td>
            <td><input type="button" value="추가" class="insertFile"></td>
        </tr>
        </tbody>
        <tfoot>
        <tr style="display:none">
            <th><label>첨부파일</label></th>
            <td><input type="file" name="filename" /></td>
            <td><input type="button" value="추가" class="insertFile"></td>
            <td><input type="button" value="삭제" class="deleteFile"></td>
        </tr>
        </tfoot>
    </table>
    <input type="submit" value="수정"/>
</form>
<script>
    let tbody = document.querySelector("#fileBody");
    let tr = document.querySelector("tfoot tr");
    let insertFile = document.querySelector(".insertFile");

    function insertFileEventHandler() {
        let newTr = tr.cloneNode(true);
        tbody.append(newTr);
        newTr.style.display = "";

        newTr.querySelector(".insertFile").addEventListener("click", insertFileEventHandler);
        newTr.querySelector(".deleteFile").addEventListener("click", e => {
            tbody.removeChild(e.target.parentNode.parentNode)
        });
    }
    insertFile.addEventListener("click", insertFileEventHandler);
</script>
<script>
    let editor;
    ClassicEditor
        .create(document.querySelector( '#editor' ), {language : "ko"} )
        .then(newEditor => {
            editor = newEditor;
        })
        .catch( error => {
            console.error( error );
        } );

    let boardForm = document.querySelector("#uploadForm");
    boardForm.addEventListener("submit", (e) => {
        e.preventDefault();

        fetch('${pageContext.request.contextPath}/api/admin/product/edit', {
            method : 'POST',
            cache: 'no-cache',
            body: new FormData(boardForm)
        })
        .then(response => response.json())
        .then(jsonResult => {
            alert(jsonResult.message);
            if(jsonResult.status === true){
                location.href = jsonResult.url;
            }
        });
    });
</script>
<script>
    document.querySelector(".fileDel").addEventListener("click", e => {
        e.preventDefault();
        let link = e.target;
        let delData = link.getAttribute("data-id");
        let delFiles = document.querySelector("#delFiles");

        if(delFiles.value.length < 1) {
            delFiles.value = delData;
        }else{
            delFiles.value += ","+delData;
        }

        console.log(document.querySelector("#delFiles"));


    });

</script>