<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Авиакомпания</title>
    <link rel="stylesheet" href="jsp/css/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="jsp/css/commonStyle.css">
    <link rel="stylesheet" href="jsp/css/userStyle.css">
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
    <div id="content">
        <div class="tabs">
            <ul class="nav nav-tabs">
                <li class="active"><a href="#tab-1" data-toggle="tab">Пилоты авиакомпании</a></li>
                <li><a href="#tab-2" data-toggle="tab">Рейсы авиакомпании</a></li>
            </ul>
            <div class="tab-content">
                <div class="tab-pane fade in active" id="tab-1">
                    <div class="row flights-list">
                        <a href="pilotinfo.jsp">Добавить пилота</a>
                        <ul>
                            <c:forEach items="${pilots}" var="elem">
                                <form action="pilot.do" method="post">
                                <li>
                                    ФИО: ${elem.lastName} ${elem.firstName} ${elem.patronymic}<br>
                                    ИИН: ${elem.iin}<br>
                                    Стаж: ${elem.experience}<br>
                                    <button class="button" type="submit" name="btn"  value="edit_pilot">Изменить</button>
                                    <button class="button" type="submit" name="btn"  value="delete_pilot">Удалить</button>
                                </li>
                                </form>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
                <div class="tab-pane fade" id="tab-2">
                    <div class="row flights-list">
                        <a href="flightinfo.jsp">Добавить рейс</a>
                        <ul>
                            <c:forEach items="${flights}" var="elem">
                                <form action="flight.do">
                                <li>
                                    Номер рейса: ${elem.flightNumber}<br>
                                    Пункт назначения: ${elem.destination}<br>
                                    Время и дата отправления: ${elem.departureTime},  ${elem.departureDate}<br>
                                    Время и дата прибытия: ${elem.arrivalTime}, ${elem.arrivalDate}<br>
                                    Номер самолета: ${elem.plane.privateNumber}<br>
                                    Пилот: ${elem.pilot.lastName} ${elem.pilot.firstName} ${elem.pilot.patronymic}<br>
                                    <button class="button" type="submit" name="btn"  value="edit_flight">Изменить</button>
                                    <button class="button" type="submit" name="btn"  value="delete_flight">Удалить</button>
                                </li>
                                </form>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <footer id="footer" class="row">
        &copy;2016, Бигвава Лариса, Виноградова Анна, Игнатович Виктория
    </footer>
</div>
<script src="jsp/css/bootstrap/js/jquery.min.js"></script>
<script src="jsp/css/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>
