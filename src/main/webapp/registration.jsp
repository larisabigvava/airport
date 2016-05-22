<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Регистрация</title>
    <link rel="stylesheet" href="jsp/css/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="jsp/css/commonStyle.css">
    <link rel="stylesheet" href="jsp/css/registrationStyle.css">
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
    <div class="row">
        <div class="col-sm-6 col-xs-10 col-sm-offset-3 col-xs-offset-1" id="content">
            Заполните поля для регистрации:
            <form name="registration_form" action="registration.do" method="post">
                <input class="field" type="text" name="login" placeholder="Логин" required/>
                <input class="field" type="password" name="password_1" placeholder="Пароль" required/>
                <input class="field" type="password" name="password_2" placeholder="Подтвердите пароль"  required/>
                <input class="field" type="text" name="email" placeholder="e-mail" required/>
                <input class="field" type="text" name="last_name" placeholder="Фамилия" required/>
                <input class="field" type="text" name="first_name" placeholder="Имя" required/>
                <input class="field" type="text" name="patronymic" placeholder="Отчество" required/>
                <input class="field" type="text" name="passport" placeholder="Номер паспорта" required/>
                <button class="btn-default button" type="submit" name="btn" value="registration">Зарегистрироваться</button>
            </form>
        </div>
    </div>
    <footer id="footer" class="row">
        &copy;2016, Бигвава Лариса, Виноградова Анна, Игнатович Виктория
    </footer>
</div>
<script>
    function checkPassword (e) {
        var password = document.registration_form.password_1.value;
        var acceptPassword = document.registration_form.password_2.value;
        if(password != acceptPassword) {
            alert("Вы неверно подтвердили пароль!");
            e.preventDefault();
        }
    }

    document.registration_form.addEventListener("submit", checkPassword);
</script>
<script src="jsp/css/bootstrap/js/jquery.min.js"></script>
<script src="jsp/css/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>