<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Личный кабинет</title>
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
    <div class="container-fluid" id="content">
        <div class="row">
            <div class="col-xs-10 col-md-5 col-xs-offset-1" id="user-data">
                Данные пользователя:
                <form action="user_save_changes.do" method="post">
                    <input class="field" type="text" name="last_name" value="${sessionScope.user.lastName}"/>
                    <input class="field" type="text" name="first_name" value="${sessionScope.user.firstName}"/>
                    <input class="field" type="text" name="patronymic" value="${sessionScope.user.patronymic}"/>
                    <input class="field" type="text" name="passport" value="${sessionScope.user.passport}"/>
                    <button class="button" type="submit" name="btn" value="user_save_changes">Сохранить</button>
                </form>
            </div>
        </div>
        <div class="row flights-list">
            <ul>
                <c:forEach items="${tickets}" var="elem">
                    <li>
                        <fieldset>
                            <legend>Номер билета: ${elem.id}</legend>
                        Номер рейса: ${elem.flight.flightNumber}<br>
                        Номер места: ${elem.seat.place}<br>
                        Пункт назначения: ${elem.flight.destination}<br>
                        Время отправления: ${elem.flight.departureTime}, дата отправления: ${elem.flight.departureDate}<br>
                        Время прибытия: ${elem.flight.arrivalTime}, дата прибытия: ${elem.flight.arrivalDate}<br>
                        Номер самолета: ${elem.flight.plane.privateNumber}<br>
                            </fieldset>
                        <form action="download_ticket.do" method="post">
                            <input hidden name="id" value="${elem.id}">
                        <select name="format" class="field">
                            <option value="PDF">PDF</option>
                            <option value="XLSX">XLSX</option>
                            <option value="CSV">CSV</option>
                        </select>
                        <button class="button" type="submit" name="btn" value="download_ticket">Скачать билет</button></form>
                    </li>
                </c:forEach>
            </ul>
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