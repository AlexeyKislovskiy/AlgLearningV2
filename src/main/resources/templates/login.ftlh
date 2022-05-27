<#import "/spring.ftl" as spring />
<#import "macros.ftlh" as fer/>
<!DOCTYPE html>
<html>
<head>
    <@fer.commonHeadPart/>
	<link href="<@spring.url '/css/algorithms.css'/>" rel="stylesheet">
	<link href="<@spring.url '/css/login.css'/>" rel="stylesheet">
	<script src="<@spring.url '/js/algorithms.js'/>"></script>
	<script src="<@spring.url '/js/login.js'/>"></script>
	<script src="<@spring.url '/js/jquery-3.6.0.min.js'/>"></script>
	<style>
		<#--		<#if emailConfirmationCode==true>-->
		<#--        #reg-semi-header, #reg-semi-body {-->
		<#--            display: none;-->
		<#--        }-->

		<#--        #mail-semi-header, #mail-semi-body {-->
		<#--            display: block;-->
		<#--        }-->
		<#--		</#if>-->
		<#--		<#if recoveryCode==true>-->
		<#--        #forgot-semi-header, #forgot-semi-body, #reg-semi-header, #reg-semi-body {-->
		<#--            display: none;-->
		<#--        }-->

		<#--        #mail2-semi-header, #mail2-semi-body {-->
		<#--            display: block;-->
		<#--        }-->
		<#--		</#if>-->
	</style>
</head>
<body>
<div class="modal fade" id="login" data-bs-backdrop="static" data-ba-keyboarf="false" tabindex="-1"
	 aria-labelledby="siteInfoLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
		<div class="modal-content" id="login-content">
			<div class="modal-header" id="login-header">
				<div class="login-registration rb active-header" id="reg-button" type="button">
					Регистрация
				</div>
				<div class="login-registration lb" id="login-button" type="button">
					Вход в аккаунт
				</div>
			</div>
			<div class="modal-body login-scrolling" id="login-body">
				<div class="semi-header" id="reg-semi-header">Зарегистрировать новый аккаунт</div>
				<div class="semi-body" id="reg-semi-body">
					<form id="register-form" method="post" action="/AlgLearning/register">
						<input id="nickname" name="nickname" class="login-input" placeholder="Введите никнейм"
							   type="text"
							   autocomplete="off" required>
						<div id="nicknameMatch" class="exception" style="display: none">Пользователь с таким никнеймом
							уже существует
						</div>
						<input id="email" name="email" class="login-input" placeholder="*Введите электронную почту"
							   type="email">
						<div id="emailMatch" class="exception" style="display: none">Эта почта уже привязана к аккаунту
							другого пользователя
						</div>
                        <#--						<c:if test="${exception=='incorrectEmail'}">-->
                        <#--							<div class="exception">Некорректный адрес почты</div>-->
                        <#--						</c:if>-->
                        <#--						<c:if test="${exception=='sendEmailProblem'}">-->
                        <#--							<div class="exception">Проблема с отправкой письма, попробуйте привязать почту позднее</div>-->
                        <#--						</c:if>-->
						<input id="password" name="password" class="login-input incorrect-password"
							   placeholder="Введите пароль" type="password"
							   autocomplete="off" required>

						<div id="weakPassword" class="exception">Пароль должен быть не менее 8 символов в длину и
							содержать строчную,
							заглавную букву и цифру
						</div>

						<input id="confirmPassword" name="confirmPassword" class="login-input"
							   placeholder="Повторите пароль"
							   type="password"
							   autocomplete="off" required>

						<div id="passwordMatch" class="exception">Пароли не совпадают</div>

						<button type="submit" class="login-button">Зарегистрировать</button>
                        <#--						<c:if test="${exception=='dataBaseProblem'}">-->
                        <#--							<div class="exception">Проблема с базой данных, попробуйте зарегистрироваться позднее</div>-->
                        <#--						</c:if>-->
					</form>
					<p>*Необязательное поле, но даст возможность обратной связи и восстановления никнейма/пароля. Можно
						указать позднее</p>
				</div>

				<div class="semi-header" id="login-semi-header">Войти в существующий аккаунт</div>
				<div class="semi-body" id="login-semi-body">
					<form id="login-form" method="post" action="/AlgLearning/login">
						<div id="registrationDone" class="good" style="display: none">Регистрация прошла успешно.
							Зайдите в созданный аккаунт
						</div>
						<input id="nickname-login" name="nickname" class="login-input"
							   placeholder="Введите никнейм" type="text"
							   autocomplete="off" required>
						<div id="noSuchUser" class="exception" style="display: none">Пользователя с таким никнеймом не
							существует
						</div>
						<input id="password-login" name="password" class="login-input"
							   placeholder="Введите пароль" type="password"
							   autocomplete="off" required>
						<div id="incorrectPassword" class="exception" style="display: none">Неправильный пароль
						</div>
						<div><label for="remember">Запомнить меня</label><input type="checkbox"
																				id="remember" name="rememberMe"></div>
						<button type="submit" class="login-button">Войти</button>
						<button type="button" id="forgot-password" class="login-button">Забыли никнейм/пароль</button>
                        <#--						<c:if test="${exception=='dataBaseProblem'}">-->
                        <#--							<div class="exception">Проблема с базой данных, попробуйте зарегистрироваться позднее</div>-->
                        <#--						</c:if>-->
					</form>
				</div>

				<div class="semi-header" id="forgot-semi-header">Восстановление никнейма/пароля</div>
				<div class="semi-body" id="forgot-semi-body">
					<form method="post" action="">
						<input id="forgot-mail" name="forgot-mail" class="login-input"
							   placeholder="Введите электронную почту" type="email"
							   autocomplete="off" required>
                        <#--						<c:if test="${exception=='emailMatch-r'}">-->
                        <#--							<div class="exception">Эта почта не привязана ни к одному аккаунту</div>-->
                        <#--						</c:if>-->
                        <#--						<c:if test="${exception=='incorrectEmail-r'}">-->
                        <#--							<div class="exception">Некорректный адрес почты</div>-->
                        <#--						</c:if>-->
                        <#--						<c:if test="${exception=='sendEmailProblem-r'}">-->
                        <#--							<div class="exception">Проблема с отправкой письма, попробуйте восстановить аккаунт-->
                        <#--								позднее-->
                        <#--							</div>-->
                        <#--						</c:if>-->
						<button type="submit" class="login-button">Отправить письмо</button>
                        <#--						<c:if test="${exception=='dataBaseProblem-r'}">-->
                        <#--							<div class="exception">Проблема с базой данных, попробуйте восстановить аккаунт позднее-->
                        <#--							</div>-->
                        <#--						</c:if>-->
					</form>
				</div>

				<div class="semi-header" id="mail-semi-header">На указанную почту было отправлено письмо с кодом
					подтверждения
				</div>
				<div class="semi-body" id="mail-semi-body">
					<form action="" method="post">
						<input id="confirm-mail" name="confirm-mail" class="login-input"
							   placeholder="Введите код из письма" type="text"
							   autocomplete="off" required>
                        <#--						<c:if test="${exception=='incorrectCode'}">-->
                        <#--							<div class="exception">Неправильный код</div>-->
                        <#--						</c:if>-->
						<button type="submit" class="login-button">Отправить</button>
                        <#--						<c:if test="${exception=='dataBaseProblem'}">-->
                        <#--							<div class="exception">Проблема с базой данных, попробуйте зарегистрироваться позднее</div>-->
                        <#--						</c:if>-->
					</form>
				</div>

				<div class="semi-header" id="mail2-semi-header">На указанную почту было отправлено письмо с кодом
					для входа в аккаунт
				</div>
				<div class="semi-body" id="mail2-semi-body">
					<form action="" method="post">
						<input id="confirm-mail2" name="confirm-mail2" class="login-input"
							   placeholder="Введите код из письма" type="text"
							   autocomplete="off" required>
                        <#--						<c:if test="${exception=='incorrectCode-r'}">-->
                        <#--							<div class="exception">Неправильный код</div>-->
                        <#--						</c:if>-->
						<button type="submit" class="login-button">Войти</button>
                        <#--						<c:if test="${exception=='dataBaseProblem'}">-->
                        <#--							<div class="exception">Проблема с базой данных, попробуйте зарегистрироваться позднее</div>-->
                        <#--						</c:if>-->
					</form>
				</div>

			</div>
		</div>
	</div>
</div>
<div class="wrapper container">
    <@fer.navigationRow 'algorithms'/>

    <@fer.cubeIcons/>

    <@fer.algorithmsRow '/AlgLearning/algorithms/method/'/>
</div>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        let modal = new bootstrap.Modal(document.getElementById('login'), {keyboard: false});
        modal.show();
    })

    function hideElements() {
        document.getElementById('reg-semi-header').style.display = "none";
        document.getElementById('reg-semi-body').style.display = "none";
        document.getElementById('mail-semi-header').style.display = "none";
        document.getElementById('mail-semi-body').style.display = "none";
        document.getElementById('mail2-semi-header').style.display = "none";
        document.getElementById('mail2-semi-body').style.display = "none";
    }

    <#--<c:if test="${intoLogin==true}">-->
    <#--    <fer:loginHide login="block" forgot="none"/>-->
    <#--</c:if>-->
    <#--<c:if test="${intoRecovery==true}">-->
    <#--    <fer:loginHide login="none" forgot="block"/>-->
    <#--</c:if>-->

    function loginHide(login, forgot) {
        let loginButton = document.getElementById('login-button');
        let regButton = document.getElementById('reg-button');
        loginButton.classList.add('active-header');
        regButton.classList.remove('active-header');
        document.getElementById('login-semi-header').style.display = login;
        document.getElementById('login-semi-body').style.display = login;
        document.getElementById('forgot-semi-header').style.display = forgot;
        document.getElementById('forgot-semi-body').style.display = forgot;
        hideElements();
    }

    $("#register-form").submit(function (e) {

        e.preventDefault();
        setDisplayNone()

        let form = $(this);
        let actionUrl = form.attr('action');

        $.ajax({
            type: "POST",
            url: actionUrl,
            data: form.serialize(),
            success: function (data) {
                $.each(data, function (i) {
                    $("#" + data[i].name).show()
                })
                if (!$.trim(data)) {
                    $("#registrationDone").show()
                    loginHide('block', 'none')
                }
            }
        });

    });

    $("#login-form").submit(function (e) {

        e.preventDefault();
        setDisplayNone()

        let form = $(this);
        let actionUrl = form.attr('action') + "/attempt";

        $.ajax({
            type: "POST",
            url: actionUrl,
            data: form.serialize(),
            success: function (data) {
                console.log(data)
                $.each(data, function (i) {
                    $("#" + data[i].name).show()
                })
                if (!$.trim(data)) {
                    form.off()
                    form.submit()
                }
            }
        });

    });

    function setDisplayNone() {
        $("#nicknameMatch").hide()
        $("#emailMatch").hide()
        $("#noSuchUser").hide()
        $("#incorrectPassword").hide()
    }
</script>
</body>
</html>