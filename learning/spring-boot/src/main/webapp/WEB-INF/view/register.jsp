<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<body>
<form action="${pageContext.request.contextPath}/register" method="post">
    <label>
        <input type="text" name="username" placeholder="username"/>
    </label>
    <label>
        <input type="password" name="password" placeholder="password"/>
    </label>
    <label>
        <input type="email" name="email" placeholder="email"/>
    </label>
    <input type="submit" value="submit"/>
</form>
</body>
</html>