<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Просмотр рейса</title>
    <link rel="stylesheet" href="jsp/css/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="jsp/css/commonStyle.css">
    <link rel="stylesheet" href="jsp/css/flightsStyle.css">
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
                    <li class="active"><a href="index.jsp">Главная</a>
                    </li>
                    <li><a href="registration.jsp">Регистрация</a></li>
                    <li><button name="btn" value="show_flights" type="submit">Рейсы</button>
                        <c:if test="${not empty sessionScope.role}"></li>
                    <li><button name="btn" value="log_off" type="submit">Выйти</button></c:if>
                </form>
            </ul>
        </div>
    </nav>
    <div class="row" id="flight-content">
        <ul>
            <li>Номер рейса: ${elem.flight_number}</li>
            <li>Пункт назначения: ${elem.destination}</li>
            <li>Время отправления: ${elem.departure_time}</li>
            <li>Дата отправления: ${elem.departure_date}</li>
            <li>Время прибытия: ${elem.departure_time}</li>
            <li>Дата прибытия: ${elem.arrival_time}</li>
            <li>Номер самолета: ${elem.plane_number}</li>
        </ul>
        <span>Выбор места:</span>
        <ul>
            <li>№ места</li><a href="">Заказать</a>
            <ul>
    </div>
    <footer id="footer" class="row">
        &copy;2016, Бигвава Лариса, Виноградова Анна, Игнатович Виктория
    </footer>
    </div>

<script src="jsp/css/bootstrap/js/jquery.min.js"></script>
<script src="jsp/css/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>