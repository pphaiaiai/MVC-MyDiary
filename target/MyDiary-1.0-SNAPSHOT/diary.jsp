<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: sanit
  Date: 2/8/22
  Time: 20:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Diary</title>
    <link rel="stylesheet" href="css/style2.css">
</head>
<body>
<form action="list-diaries" method="post">
    <c:if test="${diary != null}">
        <input type="hidden" name="id" value="${diary.id}">
        <input type="hidden" name="action" value="edit">
    </c:if>
    <label for="diaryDate">Date : </label>
    <input type="date" id="diaryDate" name="diaryDate" required value="${diary==null?'':diary.diaryDate}"><br>
    <label for="title">Title : </label>
    <input type="text" it="title" name="title" required value="${diary==null?'':diary.title}"><br>
    <textarea name="body" rows="5" cols="50" required>${diary==null?'':diary.body}</textarea><br>
    <c:if test="${diary != null}">
        <div class="logger">
            Created at <f:formatDate value="${diary.createdDate}" pattern="dd-MMM-yyyy hh:mm:ss"/><br>
            Updated at <f:formatDate value="${diary.updatedDate}" pattern="dd-MMM-yyyy hh:mm:ss"/>
        </div>
    </c:if>
    <input class="button" type="submit" value="Save">
    <a class="button" href="list-diaries">Cancel</a>
    <c:if test="${diary != null}">
        <a class="button" href="list-diaries?id=${diary.id}&action=remove">Delete</a>
    </c:if>
</form>
</body>
</html>
