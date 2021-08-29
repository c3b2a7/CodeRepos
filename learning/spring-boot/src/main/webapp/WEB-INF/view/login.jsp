<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/login" method="post">
    <label>
        <input type="text" name="username" placeholder="username"/>
    </label>
    <label>
        <input type="password" name="password" placeholder="password"/>
    </label>
    <input type="submit" value="submit"/>
</form>
</body>
</html>
