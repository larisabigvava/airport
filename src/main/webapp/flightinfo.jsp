<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Информация о рейсе</title>
    <link rel="stylesheet" href="jsp/css/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="jsp/css/commonStyle.css">
    <link rel="stylesheet" href="jsp/css/infoStyle.css">
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
    <div class="row" id="content">
        <div class="col-xs-10 col-md-5 col-xs-offset-1 col-md-offset-3" id="user-data">
            <form action="flight.do" method="post">
                <span>Введите данные о рейсе:</span>
                <input class="field" type="text" placeholder="Номер рейса" name="flight_number" required/>
                <input class="field" type="text" placeholder="Пункт назначения" name="destination" required/>
                <input class="field" type="text" placeholder="Время отправления" name="departure_time"required/>
                <input class="field" type="text" placeholder="Дата отправления" name="departure_date" required/>
                <input class="field" type="text" placeholder="Время прибытия" name="arrival_time" required/>
                <input class="field" type="text" placeholder="Дата прибытия" name="arrival_date" required/>
                <input class="field" type="text" placeholder="Номер самолета" name="id_plane" required/>
                <input class="field" type="text" placeholder="Количество мест" name="seats_count" required/>
                <input class="field" type="text" placeholder="Пилот" name="id_pilot" required/>
                <button class="button" type="submit" name="btn" value="add_flight">Сохранить</button>
            </form>
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