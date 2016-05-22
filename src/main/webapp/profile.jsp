<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
</head>
<body>
    <c:if test="${sessionScope.role == 'pilot'}">
        <jsp:forward page="pilot.jsp"/>
        pilot
    </c:if>
    <c:if test="${sessionScope.role == 'administrator'}">
        <jsp:forward  page="admin.jsp"/>
        administrator
    </c:if>
    <c:if test="${sessionScope.role == 'airline'}">
        <jsp:forward page="airline.jsp"/>
        airline
    </c:if>
    <c:if test="${sessionScope.role == 'client'}">
        <c:redirect url="user.jsp"/>
        client
    </c:if>
</body>
</html>
