<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Администратор</title>
    <link rel="stylesheet" href="jsp/css/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="jsp/css/commonStyle.css">
    <link rel="stylesheet" href="jsp/css/userStyle.css">
</head>
<body>
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
            </form>
        </ul>
    </div>
</nav>
    <div class="container-fluid" id="content">
        <div class="row">
            <div class="col-xs-10 col-md-5 col-xs-offset-1 col-md-offset-3" id="user-data">
                Заполните поля для регистрации авиакомпании:
                <form action="add_airline.do" method="post">
                    <input class="field" type="text" placeholder="Имя" name="name" required/>
                    <input class="field" type="text" placeholder="Логин" name="login" required/>
                    <input class="field" type="password" placeholder="Пароль" name="password"required/>
                    <button type="submit" name="btn" value="add_airline">Зарегистрировать</button>
                </form>
            </div>
        </div>
        <div class="row airlines-list">
            <div class="col-xs-12 col-sm-5 col-sm-offset-3">
                <ul>
                    <c:forEach items="${airlines}" var="elem">
                        <li>
                            <form action="delete_airline.do" method="post">
                                <input name="id" type="hidden" value="${elem.id}"/>
                                <input name="login" type="hidden" value="${elem.credential.login}"/>
                            <fieldset>
                                <legend>Название авиалинии: ${elem.name}</legend>
                                Логин: ${elem.credential.login}<br>
                                Пароль: ${elem.credential.password}<br>
                                <button class="button" type="submit" name="btn" value="delete_airline">Удалить</button>
                            </fieldset>
                            </form>
                        </li>
                    </c:forEach>
                </ul>
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
