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
            border: 1px solid #ffc0cb;
            margin: 0 auto;
        }

        td{
            width: 150px;
            border: 1px solid pink;
            text-align: center;
        }
    </style>
</head>
<body background="4.jpg">
<form action="${pageContext.request.contextPath}/toUpdateServlet" method="get">
<table>
    <tr>
        <td>编号</td>
        <td>姓名</td>
        <td>学号</td>

    </tr>
    <c:forEach items="${list}" var="item">
        <tr>
            <td><input type="text" value="${item.hukaixuan_id}" name="id1" readonly> </td>
            <td><input type="text" value="${item.hukaixuan_name}" name="name1"> </td>
            <td><input type="text" value="${item.hukaixuan_tel}" name="tel1"> </td>
        </tr>
    </c:forEach>
    <tr>
        <td><input type="submit" value="确认修改"></td>
    </tr>
</table>
</form>
</body>
</html>