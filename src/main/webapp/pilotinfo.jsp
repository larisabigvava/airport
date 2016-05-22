<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Seagull
  Date: 20.05.2016
  Time: 23:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Личная информация пилота</title>
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
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" data-target="#navbarCollapse" data-toggle="collapse" class="navbar-toggle">
                <span class="sr-only">Навигация</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>
        <!-- Collection of nav links, forms, and other content for toggling -->
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
            <form action="pilot.do" method="post">
                <span>Введите данные о пилоте:</span>
                <input class="field" type="text" placeholder="Имя" name="first_name" required/>
                <input class="field" type="text" placeholder="Фамилия" name="last_name" required/>
                <input class="field" type="text" placeholder="Отчество" name="patronymic"required/>
                <input class="field" type="text" placeholder="Опыт работы" name="experience" required/>
                <input class="field" type="text" placeholder="ИИН" name="iin" required/>
                <input class="field" type="text" placeholder="Логин" name="login" required/>
                <input class="field" type="text" placeholder="Пароль" name="password" required/>
                <button class="button" type="submit" name="btn" value="add_pilot">Сохранить</button>
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
