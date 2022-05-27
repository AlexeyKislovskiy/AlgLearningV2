<#import "/spring.ftl" as spring />
<#macro siteLogo>
	<div aria-label="Информация о сайте" type="button" data-bs-toggle="modal"
		 data-bs-target="#siteInfo"><img id="logo" src="<@spring.url '/img/AlgLearningLogo.png'/>"
										 alt="site-logo">
	</div>
</#macro>

<#macro siteInfo>
	<div class="modal fade" id="siteInfo" tabindex="-1" aria-labelledby="siteInfoLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content" id="modal-content-site">
				<div class="modal-header" id="siteInfoHeader">
					<img id="logo-modal"
						 src="<@spring.url '/img/AlgLearningLogo.png'/>" alt="site-logo">

					<div type="button" id="closeSiteInfoDiv" data-bs-dismiss="modal" aria-label="Закрыть">
						<img id="closeSiteInfo"
							 src="<@spring.url '/img/icons/close_black.png'/>" alt="close">
					</div>
				</div>
				<div class="modal-body">
					<p>Сайт предоставляет доступ к большому числу алгоритмов для различных головоломок,
						возможности
						тренировать их и изучать новые с помощью интервальных повторений.</p>
					<p>Есть замечания или предложения? Нашли баг или знаете о методе, которого нет в базе?
						Хотите
						помочь с переводом, добавлением новых методов или проверкой пользовательских алгоритмов?
						По всем вопросам пишите на почту или в телеграм: </p>
					<div><img class="message-img"
							  src="<@spring.url '/img/icons/mail.png'/>" alt="mail-icon"><a
								href="mailto:kislovskijalexey34@gmail.com?subject=AlgLearning">kislovskijalexey34@gmail.com</a>
					</div>
					<div><img class="message-img"
							  src="<@spring.url '/img/icons/telegram.png'/>" alt="telegram-icon"><a
								href="https://t.me/fertdt" target="_blank">@fertdt</a></div>
				</div>
			</div>
		</div>
	</div>
</#macro>

<#macro navigation currentName>
	<ul class="nav nav-pills nav-fill">
		<li class="nav-item">
			<a class="nav-link <#if currentName=='learning'>current</#if>"
			   href="<@spring.url '/AlgLearning/learning'/>">Изучение</a>
		</li>
		<li class="nav-item">
			<a class="nav-link <#if currentName=='training'>current</#if>"
			   href="<@spring.url '/AlgLearning/training'/>">Тренировка</a>
		</li>
		<li class="nav-item">
			<a class="nav-link <#if currentName=='algorithms'>current</#if>"
			   href="<@spring.url '/AlgLearning/algorithms'/>">Алгоритмы</a>
		</li>
		<li class="nav-item">
			<a class="nav-link <#if currentName=='settings'>current</#if>"
			   href="<@spring.url '/AlgLearning/settings'/>">Настройки</a>
		</li>
		<li class="nav-item">
			<a class="nav-link <#if currentName=='account'>current</#if>"
			   href="<@spring.url '/AlgLearning/account'/>">Аккаунт</a>
		</li>
	</ul>
</#macro>

<#macro navigationRow currentName>
	<div class="row row-x" id="navigation">
		<div class="col-3">
            <@siteLogo/>

            <@siteInfo/>
		</div>
		<div class="col-9">
            <@navigation currentName/>
		</div>
	</div>
</#macro>

<#macro cubeIcons>
	<div class="row" id="icons">
		<div class="col-12">
			<form id="cubeIconsForm" action="" method="get">
				<label aria-label="Показать все">
					<input class="cubeRadio" type="radio" id="all" name="cube" value="all"
                           <#if !selectedCube??>checked</#if>/>
					<img class="cubeIcons" src="<@spring.url '/img/cubeIcons/All.png'/>"
						 alt="all">
				</label>
                <#list cubes as cube>
					<label aria-label="${cube.description}">
						<input class="cubeRadio" type="radio" id="${cube.name}" name="cube" value="${cube.name}"
                               <#if selectedCube?? && cube.name==selectedCube.name>checked</#if>/>
						<img class="cubeIcons" src="<@spring.url '${cube.image}'/>" alt="${cube.name}">
					</label>
                </#list>
			</form>
		</div>
	</div>
</#macro>

<#macro algorithmsRow link>
	<div class="row scrolling" id="methods">

        <#list methods as method>
			<div class="col-6 col-md-4">
				<a href="<@spring.url '${link}${method.name}'/>">
					<div class="card <#if method.learningStatus==methodLearningStatus>learning <#elseif method.learningStatus==methodLearnedStatus>learned</#if>">
						<div class="card-header">
							<span>${method.name}</span>
						</div>
						<img class="card-img-top"
							 src="<@spring.url '${method.image}'/>" alt="${method.name}">
					</div>
				</a>
			</div>
        </#list>

	</div>
</#macro>

<#macro backgroundImage>
	<style>
        body {
            color: #fff;
            background: #000 url(<#if background??><@spring.url '${background}'/><#else>/img/background.jpg</#if>) fixed;
            min-height: 100%;
            background-size: cover;
            overflow: hidden;
        }
	</style>
</#macro>

<#macro commonHeadPart>
	<title>AlgLearning</title>
	<meta charset="utf-8">
	<meta name="viewport"
		  content="width-device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
	<link rel="icon" href="<@spring.url '/img/icons/Icon180.png'/>" type="image/png">
	<link href="<@spring.url '/css/bootstrap.min.css'/>" rel="stylesheet">
	<script src="<@spring.url '/js/bootstrap.min.js'/>"></script>
    <@backgroundImage/>
</#macro>

<#macro methodAllAction name description methodId>
	<div class="wrap">
		<div type="button" id="${name}-div" aria-label="${description}"
			 onclick="getData('${methodId}','${name}'+'All')">
			<img id="${name}-all" src="<@spring.url '/img/icons/${name}.png'/>">
		</div>
	</div>
</#macro>

<#macro methodAction name description situationId>
	<div type="button" class="img-alg-div" aria-label="${description}" onclick="getData('${situationId}','${name}')">
		<img id="${situationId}" class="img-alg"
			 src="<@spring.url '/img/icons/${name}_grey.png'/>">
	</div>
</#macro>

<#macro searchInfo>
	<div class="modal fade" id="searchModal" tabindex="-1" aria-labelledby="searchModalLabel"
		 aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
			<div class="modal-content">
				<div class="modal-header" id="searchModalHeader">
					<div id="search-info-text">Выражения для поиска</div>
					<div type="button" class="img-alg-div-1" data-bs-dismiss="modal" aria-label="Закрыть">
						<img class="img-alg"
							 src="<@spring.url '/img/icons/close.png'/>">
					</div>
				</div>
				<div class="modal-body alg-scrolling">
					<p>Все примеры указаны для Pll</p>
					<p>Отдельная строка: название содержит указанную строку, не учитывая регистра. Запрос: 'а',
						результат: Aa, Ab, Ga, Ja, Na, Ra, Ua</p>
					<p>Символ '^' перед началом строки: название начинается с указанной строки. Запрос: '^a',
						результат: Aa, Ab</p>
					<p>Символ '$' после конца строки: название заканчивается на указанную строку. Запрос: 'a$',
						результат: Aa, Ga, Ja, Na, Ra, Ua</p>
					<p>Символ '|': разделитель между частями запроса. Запрос: '^a|b$', результат: Aa, Ab, Gb,
						Jb, Nb, Rb, Ub</p>
					<p>Символ '~': исключение строк, соответствующих данному запросу. Запрос: 'a|g|j|~b$',
						результат: Aa, Ga, Gc, Gd, Ja, Na, Ra, Ua</p>
				</div>
			</div>
		</div>
	</div>
</#macro>

<#macro methodInfo>
	<div class="modal fade" id="infoModal" tabindex="-1" aria-labelledby="infoModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header" id="infoModalHeader">
					<div class="alg-card">
						<div id="alg-card-text">${method.name}</div>
						<img id="alg-card-img"
							 src="<@spring.url '${method.image}'/>">
					</div>

					<div type="button" class="img-alg-div-1" data-bs-dismiss="modal" aria-label="Закрыть">
						<img id="closeInfo" class="img-alg"
							 src="<@spring.url '/img/icons/close.png'/>">
					</div>
				</div>
				<div class="modal-body">
					<p>${method.description}</p>
					<p>Количество ситуаций: ${method.numberOfSituations}</p>
					<p><span class="learning-info">Изучающих:</span> <span
								id="numberOfLearning">${method.numberOfLearning}</span></p>
					<p><span class="learned-info">Изучивших:</span> <span
								id="numberOfLearned">${method.numberOfLearned}</span></p>
				</div>
			</div>
		</div>
	</div>
</#macro>

<#macro anotherSituation anotherSituation situation>
	<div class="col-4">
		<div class="${anotherSituation.id} card
			<#if anotherSituation.learningStatus==situationLearningStatus>learning
			<#elseif anotherSituation.learningStatus==situationLearnedStatus>learned</#if>"
             <#if anotherSituation.id!=situation.id>type="button" data-bs-toggle="modal"
			 data-bs-target="#algModal${anotherSituation.name}"</#if>>
			<div class="card-header">
				<span>${anotherSituation.name}</span>
			</div>
			<img class="card-img-top"
				 src="<@spring.url '${anotherSituation.image}'/>">
		</div>
	</div>
</#macro>

<#macro icons name description>
	<div id="${name}-div" type="button" class="img-alg-div"
		 aria-label="${description}">
		<img id="clearChoose" class="img-alg ia" <#if name=='close'>data-bs-dismiss="modal"</#if>
			 src="<@spring.url '/img/icons/${name}.png'/>">
	</div>
</#macro>

<#macro learningButton name description>
	<form class="${name} col-3" action="" method="post">
		<div id="${name}" type="button" onclick="next('${name}')">
            ${description}
		</div>
	</form>
</#macro>

<#macro moderatorButton description into>
    <#if moderator>
		<div id="moderator-div" aria-label="${description}"><img
					id="moderator-img" type="button"
					src="<@spring.url '/img/icons/moderator.png'/>"></div>
		<script>
            document.addEventListener('DOMContentLoaded', function () {
                intoModeratorPage();

                function intoModeratorPage() {
                    document.getElementById('moderator-div').addEventListener('click', ev => {
                        <#if into=='return'>window.location.replace(document.referrer);
                        <#else>window.location = '<@spring.url '${into}'/>'
                        </#if>
                    })
                }
            })
		</script>
    </#if>
</#macro>

<#macro wcaCompetitions>
	<div class="modal fade" id="wcaCompetitions" tabindex="-1" aria-labelledby="wcaCompetitionsLabel"
		 aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered">
			<div class="modal-content" id="modal-content-site">
				<div class="modal-header" id="wcaCompetitionsHeader">
					<div id="wcaCompetitionsHeader">
						5 ближайших к вам WCA соревнований
					</div>
					<div type="button" id="closeWcaCompetitionsDiv" data-bs-dismiss="modal" aria-label="Закрыть">
						<img id="closeWcaCompetitions"
							 src="<@spring.url '/img/icons/close.png'/>" alt="close">
					</div>
				</div>
				<div id="wcaCompetitionsContent" class="modal-body row">
					<div id="wca-img-wrapper" class="col-6 wcaCompetition-img-wrapper" aria-label="Ссылка">
						<img class="wcaCompetition-img" src="<@spring.url '/img/icons/wca.png'/>">
					</div>
					<div class="col-3 wcaCompetition-img-wrapper" aria-label="Расстояние по прямой">
						<img class="wcaCompetition-img" src="<@spring.url '/img/icons/distance.png'/>">
					</div>
					<div class="col-3 wcaCompetition-img-wrapper" aria-label="Расстояние на автомобиле">
						<img class="wcaCompetition-img" src="<@spring.url '/img/icons/car.png'/>">
					</div>
					<div id="loading" class="col-12 wcaCompetition-img-wrapper">
						<img id="loading-img" src="<@spring.url '/img/icons/loading.gif'/>">
					</div>
				</div>

			</div>
		</div>
	</div>
</#macro>
