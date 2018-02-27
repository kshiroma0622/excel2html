<%--
  Created by IntelliJ IDEA.
  User: kshiroma
  Date: 2018/02/27
  Time: 15:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Excel2Html</title>
    <script type="text/javascript">
        function fileget(imgfile) {
            if (!imgfile.files.length) return;
            var file = imgfile.files[0];
            var fr = new FileReader();
            fr.onload = function (evt) {
                document.getElementById("file-base64").value = evt.target.result;
                //document.getElementById("datauri").href = evt.target.result;
            }
            fr.readAsDataURL(file);
        }
    </script>
</head>
<body>
<a href="/samples/sample.xlsx">Excelサンプル</a>
<br/>
<input name="file" type="file" value="エクセルファイル" onchange="fileget(this)"/>
<form action="/excel2html" method="post">
    <input id="file-base64" name="file-base64" type="hidden"/>
    <input type="submit"/>
</form>
</body>
</html>
