<#import "/spring.ftl" as spring />
<#import "macros.ftlh" as fer/>
<!DOCTYPE html>
<html>
<head>
    <@fer.commonHeadPart/>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
	<link href="<@spring.url '/css/account.css'/>" rel="stylesheet">
	<script src="<@spring.url '/js/account.js'/>"></script>
	<script src="<@spring.url '/js/jquery-3.6.0.min.js'/>"></script>
</head>
<body>
<div class="wrapper container">
    <@fer.navigationRow 'account'/>

    <@fer.wcaCompetitions />

	<div class="row row-x" id="account-header">
		<div class="col-1"></div>
		<div class="col-10 account-header">
			<div class="img-wrapper" id="wca" aria-label="Ближайшие к вам соревнования"><img class="account-img"
																							 type="button"
																							 data-bs-toggle="modal"
																							 data-bs-target="#wcaCompetitions"
																							 src="<@spring.url '/img/icons/wca.png'/>"
																							 onclick="getCompetitions()">
			</div>
            ${cuber.nickname}
			<div>
				<span class="img-wrapper" id="delete" aria-label="Удалить аккаунт"><img class="account-img"
																						type="button"
																						src="<@spring.url '/img/icons/delete_grey.png'/>"
																						onclick="deleteAccount()">
				</span>
				<span class="img-wrapper" id="exit" aria-label="Выйти из аккаунта"><img class="account-img"
																						type="button"
																						src="<@spring.url '/img/icons/exit.png'/>"
																						onclick="logout()">
				</span>
			</div>
		</div>
		<div class="col-1"></div>
	</div>

	<div class="row row-x">
		<div class="col-1"></div>
		<div class="col-md-3 col-10 acc-card">
			<div class="acc-header" id="acc-header">
				Информация
			</div>
			<div class="acc-body" id="acc-info">
				<div>Дата регистрации: ${cuber.registrationDate}</div>
				<div>Заходили дней: ${cuber.visitedDays}</div>
				<div>Заходили подряд дней: ${cuber.visitedDaysRow}</div>
				<div><span class="learning">Ситуаций в изучаемых:</span> ${cuber.numberOfLearning}</div>
				<div><span class="learned">Ситуаций в изученных:</span> ${cuber.numberOfLearned}</div>
			</div>
		</div>
		<div class="col-1"></div>
		<div class="col-1" id="col-1-insert"></div>
		<div class="col-md-6 col-10 acc-card">
			<div class="acc-header" id="stat-header">
				Статистика
			</div>
			<div class="header-2-wrap" id="header-3">
				<div class="acc-body-header rb">
					Изучение
					<img class="acc-img" src="<@spring.url '/img/icons/learning.png'/>">
				</div>
				<div class="acc-body-header lb">
					Тренировка
					<img class="acc-img" src="<@spring.url '/img/icons/training.png'/>">
				</div>
			</div>

			<div class="acc-body scrolling" id="stat-info">
				<div class="header-2" id="header-2">

                    <#list cuber.statistics as statistics>
						<div class="semi-header">${statistics.name}</div>
						<div class="acc-content rb">
							<div><span class="new">Новых:</span> ${statistics.numberOfNew}</div>
							<div><span class="forgot">Забытых:</span> ${statistics.numberOfForgot}</div>
							<div><span class="repeat">Повторенных:</span> ${statistics.numberOfRepeat}</div>
						</div>
						<div class="acc-content lb">
							<div><span class="solved">Собранных:</span> ${statistics.numberOfTrained}</div>
						</div>
                    </#list>

				</div>

			</div>
		</div>
	</div>
    <@fer.moderatorButton 'К странице модерации' '/AlgLearning/moderator'/>
</div>
<script>
    function logout() {
        window.location.replace("/AlgLearning/logout");
    }

    function deleteAccount() {
        $.ajax({
            url: '/AlgLearning/delete',
            type: 'POST',
            success: function () {
                window.location.replace("/AlgLearning/login");
            }
        });
    }

    function getCompetitions() {
        let wcaCompetitions = $("#wcaCompetitionsContent");
        if (wcaCompetitions.html().endsWith("</div> </div>")) return
        navigator.geolocation.getCurrentPosition(function (data) {
            let coordinates = data.coords.longitude + "," + data.coords.latitude
            $.ajax({
                url: '/AlgLearning/wcaCompetition/' + coordinates,
                type: 'GET',
                dataType: "json",
                success: function (data) {
                    let html = ""
                    $.each(data, function (i) {
                        let competition = data[i];
                        html += "<div class='col-6 wcaCompetition-img-wrapper'> <a href='" + competition.link + "'target='_blank'>" +
                            competition.name + "</a> </div> <div class='col-3 wcaCompetition-img-wrapper'> <div>" + competition.distance +
                            " км</div> </div> <div class='col-3 wcaCompetition-img-wrapper'> <div>" + competition.distanceByCar + " км</div> </div>"
                    })
                    $("#loading").remove()
                    wcaCompetitions.append(html)
                }
            });
        }, function () {
            $("#loading").remove()
            let html = "<div class='col-12 wcaCompetition-img-wrapper forbid'> Вы запретили доступ к вашему географическому положению </div>"
            wcaCompetitions.append(html)
        })

    }

</script>
</body>
</html>