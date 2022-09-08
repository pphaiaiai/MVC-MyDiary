<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login Page</title>
    <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
<div class="container">
    <h1>Sign In</h1>
    <form action="authen" method="post">
        <div class="form-control">
            <label for="username">User Name :</label>
            <input type="text" name="userName"><br/>
        </div>
        <div class="form-control">
            <label for="password">Password :</label>&nbsp;&nbsp;&nbsp;
            <input type="password" name="password"><br/>
        </div>
        <input type="submit" class="btn" value="Login">
    </form>
</div>
<%--<script>--%>
<%--    const labels = document.querySelectorAll(".form-control label");--%>
<%--    labels.forEach((label) => {--%>
<%--        label.innerHTML = label.innerText--%>
<%--            .split("")--%>
<%--            .map(--%>
<%--                (letter, idx) =>--%>
<%--                    `<span style="transition-delay:${idx * 50}ms">${ letter }</span>`--%>
<%--            )--%>
<%--            .join("");--%>
<%--    });--%>
<%--</script>--%>
</body>
</html>