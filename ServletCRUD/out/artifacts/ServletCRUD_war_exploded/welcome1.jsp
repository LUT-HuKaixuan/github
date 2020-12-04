<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>


<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>显示</title>
    <style type="text/css">
        table {
            border: 1px solid pink;
            margin: 0 auto;
        }

        td {
            width: 150px;
            border: 1px solid pink;
            text-align: center;
        }
    </style>
</head>
<body background="4.jpg">
<table align="center">
    <tr>
        <th width="336" scope="row">
            <font size="5"><b><i>欢迎来到管理员界面</i></b></font><br>
        </th>
    </tr>
</table>
<br><font color="black">
    <ol>
        <li><a href="${pageContext.request.contextPath}/selectServlet">查看所有用户</a><br>

        <li><a href="login.jsp">返回登录</a>
    </ol>
</font>

</body>
</html>
