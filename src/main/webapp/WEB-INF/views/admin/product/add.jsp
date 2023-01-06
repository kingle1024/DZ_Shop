<%--
  Created by IntelliJ IDEA.
  User: ejy1024
  Date: 2022/01/03
  Time: 12:08 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form id="uploadForm" method="post" enctype="multipart/form-data">
    상품명 : <input type="text" name="title" id="title" value="상품명 "><br/>
    가격 : <input type="number" name="price" id="price" value="1000"><br/>
    썸네일 : <input type="file" name="thumbnail" id="thumbnail">

    <textarea name="editor" id="editor"></textarea>
    <table>
        <tbody>
        <tr>
            <th><label>첨부파일</label></th>
            <td><input type="file" name="filename1"></td>
            <td><input type="button" value="추가" class="insertFile"></td>
        </tr>
        </tbody>
        <tfoot>
        <tr style="display:none">
            <th><label>첨부파일</label></th>
            <td><input type="file" name="filename1"></td>
            <td><input type="button" value="추가" class="insertFile"></td>
            <td><input type="button" value="삭제" class="deleteFile"></td>
        </tr>
        </tfoot>
    </table>
    <input type="submit" id="insertButton" value="제출">
</form>
<script type="text/javascript">
    let tbody = $("tbody")[0];
    let tr = $("tfoot tr")[0];
    let insertFile = $(".insertFile");
    function insertFileEventHandler() {
        let newTr = tr.cloneNode(true);
        tbody.append(newTr);
        newTr.style.display = "";
        newTr.querySelector(".insertFile").addEventListener("click", insertFileEventHandler);
        newTr.querySelector(".deleteFile").addEventListener("click", e => {
            tbody.removeChild(e.target.parentNode.parentNode)
        });
    }
    insertFile.on("click", insertFileEventHandler);
</script>

<script type="text/javascript">
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

        fetch('${pageContext.request.contextPath}/api/admin/product/add', {
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
