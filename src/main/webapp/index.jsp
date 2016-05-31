<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %><html>
<title>Авторизация</title>
<link rel="stylesheet" href="jsp/css/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="jsp/css/commonStyle.css">
<link rel="stylesheet" href="jsp/css/indexStyle.css">
<body>
<div class="container-fluid">
    <header id="header" class="row">
        <img src="jsp/img/plane.jpg" alt="plane">
    </header>
    <nav role="navigation" class="navbar navbar-default">
        <div class="navbar-header">
            <button type="button" data-target="#navbarCollapse" data-toggle="collapse" class="navbar-toggle">
                <span class="sr-only">Навигация</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>
        <div id="navbarCollapse" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <form action="menu.do">
                    <c:if test="${empty sessionScope.role}">
                        <li class="active"><a href="index.jsp">Авторизация</a>
                        </li>
                        <li><a href="registration.jsp">Регистрация</a></li>
                    </c:if>
                    <li><button name="btn" value="show_flights" type="submit">Рейсы</button></li>
                    <c:if test="${not empty sessionScope.role}">
                    <li><button class="btn" type="submit" name="btn" value="sign_in">Кабинет</button>
                    </li>
                    <li><button name="btn" value="log_off" type="submit">Выйти</button></c:if>
                </form>
            </ul>
        </div>
    </nav>
    <div class="row">
        <div class="col-sm-4 col-xs-10 col-sm-offset-4 col-xs-offset-1" id="content">
            <c:if test="${empty sessionScope.role}">
            <form name="sign_in" action="sign_in.do" method="post">
                <input class="field" type="text" placeholder="Логин" name="login" autofocus/>
                <input class="field" type="text" placeholder="Пароль" name="password"/>
                <button class="btn-info button" type="submit" name="btn" value="sign_in">Войти</button>
            </form></c:if>
            <%--УБРАТЬ--%>
            <c:if test="${not empty sessionScope.role}">
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
            </c:if>
        </div>
    </div>
    <footer id="footer" class="row">
        &copy;2016, Бигвава Лариса, Виноградова Анна, Игнатович Виктория
    </footer>
</div>
<script>
    function setCookie(){
        var login = document.sign_in.login.value;
        var password = document.sign_in.password.value;

        document.cookie = "login="+login;
        document.cookie = "password="+password;
    }

    document.sign_in.addEventListener("submit", setCookie);
</script>
<script src="jsp/css/bootstrap/js/jquery.min.js"></script>
<script src="jsp/css/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>