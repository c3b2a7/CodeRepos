<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
<form>
    ${user}
    <c:forEach items="${userList}" var="u">
        <ul>
            <li>${u.id}</li>
            <li>${u.name}</li>
            <li>${u.password}</li>
            <li>${u.email}</li>
            <li>${u.authCode}</li>
            <li>${u.created}</li>
            <li>${u.group}</li>
            <li>${u.logged}</li>
        </ul>
    </c:forEach>
</form>
</body>
</html>