<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
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

        td{
            width: 150px;
            border: 1px solid pink;
            text-align: center;
        }
    </style>
</head>
<body>
<table>
    <tr>
        <td>编号</td>
        <td>姓名</td>
        <td>电话</td>
    </tr>
    <c:forEach items="${list}" var="item">
        <tr>
            <td>${item.hukaixuan_id}</td>
            <td>${item.hukaixuan_name}</td>
            <td>${item.hukaixuan_tel}</td>
            <td><a href="DeleteServlet?hukaixuan_id=${item.hukaixuan_id }">删除</a>|<a href="updateServlet?hukaixuan_id=${item.hukaixuan_id }">修改</a></td>
        </tr>
    </c:forEach>
    <tr>
        <td colspan="6" style="text-align: left;"><a href="add.jsp">添加用户</a></td>
    </tr>
</table>
</body>
</html>