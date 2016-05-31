<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Путь к файлу</title>
    <link rel="stylesheet" href="jsp/css/bootstrap/css/bootstrap.css" type="text/css">
    <link rel="stylesheet" href="jsp/css/commonStyle.css" type="text/css">
    <link rel="stylesheet" href="jsp/css/userStyle.css" type="text/css">
</head>
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
    <div class="container-fluid" id="content">
        <form name="sign_in" action="sign_in.do" method="post">
            <button class="btn-info button" type="submit" name="btn" value="sign_in">Кабинет</button>
        </form>
        <div class="row">
            <a href="C:${link}">C:${link}</a>
        </div>
    </div>

    <br><br><br><br><br><br><br>
    <footer id="footer" class="row">
        &copy;2016, Бигвава Лариса, Виноградова Анна, Игнатович Виктория
    </footer>
</div>
<script src="jsp/css/bootstrap/js/jquery.min.js"></script>
<script src="jsp/css/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>
