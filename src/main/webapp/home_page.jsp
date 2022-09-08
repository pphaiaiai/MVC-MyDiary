<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 8/2/2022
  Time: 1:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home Page</title>
    <link rel="stylesheet" href="css/style2.css">
</head>
<body>
<h1>
    ${owner.userName}'s <span class="logger">(Last login at ${owner.lastLogin})</span>
</h1>
<div>
    <a class="button" href="list-diaries?action=new">New</a> &nbsp;&nbsp; <a class="button" href="authen?action=logout">Logout</a>
</div>
<table>
    <tr>
        <th>Date</th>
        <th class="title">Title</th>
        <th>Updated Date</th>
    </tr>
    <c:forEach items="${diaries}" var="diary">
        <tr>
            <td><f:formatDate value="${diary.diaryDate}" pattern="dd-MMM-yyyy"/></td>
            <td class="title"><a href="list-diaries?id=${diary.id}&action=edit">${diary.title}</a></td>
            <td><f:formatDate value="${diary.updatedDate}" pattern="dd-MMM-yyyy hh:mm:ss"/></td>
        </tr>
    </c:forEach>
</table>
<br/>
</body>
</html>
