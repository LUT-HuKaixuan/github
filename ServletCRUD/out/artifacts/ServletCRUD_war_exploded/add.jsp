<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>添加</title>
</head>

<body>
<form action="insertServlet" method="get">
    <table border="1" class="t1">
        <tr>
            <td colspan="2"><h1>添加用户</h1></td>
        </tr>
        <tr>
            <td>姓名:</td>
            <td><input  type="text" name="name"/></td>
        </tr>
        <tr>
            <td>电话:</td>
            <td><input  type="text" name="tel"/></td>
        </tr>
        <tr>
            <td colspan="2">
                <input  type="submit" value="提交"/>
                <input  type="reset" value="清空"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
