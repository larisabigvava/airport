<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Рейсы</title>
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
    <div class="container-fluid" id="content">
        <div class="row">
            <div class="col-xs-10 col-xs-offset-1" id="find">
                <form name="search" action="search.do">
                    <input class="field" type="text" name="search_field">
                    <button name="btn" class="button" type="submit" value="search">Поиск</button><br>
                    <ul class="sort">
                        <li>
                            <input type="radio" name="choice" value="destination" checked="checked"> По пункту назначения
                        </li>
                        <li>
                            <input type="radio" name="choice" value="date"> По дате отправления
                        </li>
                    </ul>
                </form>

            </div>
        </div>
        <div class="row flights-list">
            <c:if test="${not empty sessionScope.role}">
                    <form name="sign_in" action="sign_in.do" method="post">
                        <button class="btn-info button" type="submit" name="btn" value="sign_in">Кабинет</button>
                    </form>
            </c:if>
            <ul>
                <c:forEach items="${flights}" var="elem">
                    <form action="reserve.do" name="reserve">
                    <li>
                        <input type="hidden" name="id_flight" value="${elem.id}">
                        Номер рейса: ${elem.flightNumber}<br>
                        Пункт назначения: ${elem.destination}<br>
                        Время и дата отправления: ${elem.departureTime},  ${elem.departureDate}<br>
                        Время и дата прибытия: ${elem.arrivalTime}, ${elem.arrivalDate}<br>
                        Номер самолета: ${elem.plane.privateNumber}<br>
                        <c:if test="${sessionScope.role == 'client'}">
                            <button name="btn" value="reserve" type="submit">Заказать билет</button>
                        </c:if>
                    </form>
                    </li>
                </c:forEach>
                <c:if test="${empty flights}">
                    <li>
                    Рейсов по запросу не найдено.
                    </li>
                </c:if>
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