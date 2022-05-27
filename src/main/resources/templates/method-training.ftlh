<#import "/spring.ftl" as spring />
<#import "macros.ftlh" as fer/>
<!DOCTYPE html>
<html>
<head>
    <@fer.commonHeadPart/>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
	<link href="<@spring.url '/css/method-training.css'/>" rel="stylesheet">
	<script src="<@spring.url '/js/method-training.js'/>"></script>
	<script src="<@spring.url '/js/method-learning.js'/>"></script>
	<script src="<@spring.url '/js/jquery-3.6.0.min.js'/>"></script>
</head>
<body>
<div class="wrapper container">
    <@fer.navigationRow 'training'/>

	<div class="row row-x" id="top-row">
		<div class=col-3>
			<a id="arrow-a" type="button" aria-label="Назад" onclick="window.location.replace(document.referrer);">
				<img id="back-arrow" src="<@spring.url '/img/icons/backArrow.png'/>">
			</a>

			<a href="<@spring.url '/AlgLearning/algorithms/method/${method.name}'/>" id="a-page"
			   aria-label="Посмотреть метод в алгоритмах">
				<img id="page" src="<@spring.url '/img/icons/page.png'/>">
			</a>

		</div>

		<div class="col-6 scramble-div">
			<div class="scramble">
                ${scramble}
			</div>
		</div>

		<div class="col-3">
			<div id="choose-div" aria-label="Выбрать показываемые ситуации" type="button" class="wrap"
				 data-bs-toggle="modal"
				 data-bs-target="#chooseModal">
				<img id="choose" src="<@spring.url '/img/icons/choose.png'/>">
			</div>

			<div class="modal fade" id="chooseModal" tabindex="-1" aria-labelledby="chooseModalLabel"
				 aria-hidden="true">
				<div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
					<div class="modal-content">
						<div class="modal-header" id="alg-modal-header">
							<div class="alg-card <#if method.learningStatus==methodLearningStatus>learning<#elseif method.learningStatus==methodLearnedStatus>learned</#if>"
								 id="alg-card">
								<div id="alg-card-text">${method.name}</div>
								<img id="alg-card-img"
									 src="<@spring.url '${method.image}'/>">
							</div>
							<div class="header-wrap">
								<div class="inner-header">

                                    <@fer.icons 'clearChoose' 'Отметить по умолчанию'/>

                                    <@fer.icons 'chooseAll' 'Отметить все ситуации'/>

                                    <@fer.icons 'unchooseAll' 'Снять все отметки'/>

                                    <@fer.icons 'close' 'Закрыть'/>

								</div>
								<div class="input">
									<form id="search-form" action="" method="get">
										<input id="search-input" name="search" placeholder="Поиск по названию"
											   type="search"
											   autocomplete="off">
										<button type="submit"></button>
									</form>
								</div>

							</div>


						</div>
						<div class="modal-body alg-scrolling">
							<div class="row">
								<form class="row" id="using-form" action="" method="post">
                                    <#list situations as situation>
										<div class="col-4 checkbox-wrap">
											<label for="${situation.id}">
												<div class="sit-card <#if situation.learningStatus==situationLearningStatus>learning<#elseif situation.learningStatus==situationLearnedStatus>learned</#if>">
													<div class="sit-card-text">${situation.name}</div>
													<img class="sit-card-img"
														 src="<@spring.url '${situation.image}'/>">
												</div>
											</label>
											<input type="checkbox"
                                                   <#if situation.trainingStatus==situationTrainingStatus>checked</#if>
												   class="using-check" id="${situation.id}" name="${situation.id}"
												   value="${situation.id}" onchange="train('${situation.id}')">
										</div>
                                    </#list>

								</form>

							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>

	<div class="row row-x" id="row-1">
		<div class="col-md-1 col-3"></div>
		<div class="col-md-3 col-6 cc">
			<div class="card" aria-label="Показать информацию" id="hidden-card">
				<div class="card-header <#if situation.learningStatus==situationLearningStatus>learning<#elseif situation.learningStatus==situationLearnedStatus>learned</#if>"
					 id="hidden-card-text">
					?
				</div>
				<img class="card-img-top" id="hidden-card-img"
					 src="<@spring.url '/img/icons/unknown.png'/>">
			</div>
		</div>
		<div class="col-md-1 col-3"></div>
		<div class="col-md-1 col-3"></div>
		<div class="col-md-5 col-6 cc">
			<div class="card" id="card-alg" aria-label="Показать информацию">
				<div class="card-header <#if situation.learningStatus==situationLearningStatus>learning<#elseif situation.learningStatus==situationLearnedStatus>learned</#if>">
					<span>Алгоритмы</span>
				</div>

				<div class="alg-body" id="alg-body-1">
                    <#if hideNumberOfAlgorithmsInTraining=='false'>
                        <#list situation.algorithms as algorithm>
							<div class="alg-border">
								? ? ? ? ? ? ? ? ? ?
							</div>
                        </#list>
                    <#else>
                        <#list situation.algorithms as algorithm>
                            <#if algorithm?index==0>
								<div class="alg-border">
									? ? ? ? ? ? ? ? ? ?
								</div>
                            <#else>
								<div class="alg-border" style="display: none">
									? ? ? ? ? ? ? ? ? ?
								</div>
                            </#if>
                        </#list>
                    </#if>
                    <#if situation.algorithms?size==0>
						<div class="alg-border">
							? ? ? ? ? ? ? ? ? ?
						</div>
                    </#if>
				</div>

			</div>
		</div>
	</div>

	<div class="row row-x" id="row-2">
		<div class="col-3"></div>
		<div class="col-6 level">
			<div class="next" type="button" id="next">
				Дальше
			</div>
		</div>
	</div>
    <@fer.moderatorButton 'К странице модерации' '/AlgLearning/moderator'/>
</div>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        function showHiddenCard() {
            document.getElementById('hidden-card').classList.add('removed-aria-label');
            document.getElementById('hidden-card-text').textContent = "${situation.name}";
            document.getElementById('hidden-card-img').src = "<@spring.url '${situation.image}'/>";
        }

        <#if hideSituationInTraining=='true'>
        document.getElementById('hidden-card').onclick = function () {
            showHiddenCard();
        }
        <#else>
        showHiddenCard();
        </#if>

        document.getElementById('next').addEventListener('click', ev => {
            next()
        })
        document.addEventListener('keypress', keypressListener)

        function keypressListener() {
            let keyName = event.key;
            if (keyName === 'Enter' || keyName === ' ') next()
        }

        document.getElementById('card-alg').onclick = function () {
            let elements = document.getElementsByClassName('alg-border');
            let list = [];
            <#list situation.algorithms as algorithm>
            list.push("${algorithm?index+1}) <#noautoesc>${algorithm.text}</#noautoesc>");
            </#list>
            <#if situation.algorithms?size==0>
            list.push("Для этой ситуации не выбрано ни одного алгоритма в качестве используемого");
            elements[0].style.textDecoration = "none";
            </#if>
            for (let i = 0; i < elements.length; i++) {
                elements[i].textContent = list[i];
                elements[i].style.display = "block";
            }
            document.getElementById('card-alg').classList.add('removed-aria-label');
        }

        document.getElementById('chooseModal').addEventListener('hidden.bs.modal', ev => {
            next()
            document.addEventListener('keypress', keypressListener)
        })

        document.getElementById('chooseModal').addEventListener('show.bs.modal', ev => {
            document.removeEventListener('keypress', keypressListener)
        })

        document.getElementById('clearChoose-div').addEventListener('click', ev => {
            let el = document.getElementsByClassName('using-check');
            let cards = document.getElementsByClassName('sit-card');
            for (let i = 0; i < el.length; i++) {
                if ('${defaultIncludeNotLearningInTraining}' === 'true' && !cards[i].classList.contains("learning") && !cards[i].classList.contains("learned") ||
                    '${defaultIncludeLearningInTraining}' === 'true' && cards[i].classList.contains("learning") ||
                    '${defaultIncludeLearnedInTraining}' === 'true' && cards[i].classList.contains("learned")) {
                    if (el[i].checked === false) {
                        el[i].checked = true;
                        let event = new Event('change');
                        el[i].dispatchEvent(event);
                    }
                } else if (el[i].checked === true) {
                    el[i].checked = false;
                    let event = new Event('change');
                    el[i].dispatchEvent(event);
                }
            }
        })
    })

    function next() {
        let methodId = '${method.id}'
        $.ajax({
            dataType: "json",
            url: '/AlgLearning/algorithms/method/nextTraining/' + methodId,
            type: 'POST',
            success: function (data) {
                $(".scramble").empty().append(data.scramble)
                $("#hidden-card-text").empty().append("?")
                if (data.situation.learningStatus === '${situationLearningStatus}') $(".card-header").attr('class', 'card-header learning')
                else if (data.situation.learningStatus === '${situationLearnedStatus}') $(".card-header").attr('class', 'card-header learned')
                else $(".card-header").attr('class', 'card-header')
                $("#hidden-card-img").attr('src', '/img/icons/unknown.png')

                function showHiddenCard() {
                    document.getElementById('hidden-card').classList.add('removed-aria-label');
                    document.getElementById('hidden-card-text').textContent = data.situation.name;
                    document.getElementById('hidden-card-img').src = data.situation.image;
                }

                <#if hideSituationInTraining=='true'>
                document.getElementById('hidden-card').classList.remove('removed-aria-label');
                document.getElementById('hidden-card').onclick = function () {
                    showHiddenCard();
                }
                <#else>
                showHiddenCard();
                </#if>
                $("#alg-body-1").empty()

                let html = ""
                <#if hideNumberOfAlgorithmsInTraining=='false'>
                $.each(data.situation.algorithms, function (i) {
                    html += "<div class='alg-border'> ? ? ? ? ? ? ? ? ? ? </div>"
                })
                <#else>
                $.each(data.situation.algorithms, function (i) {
                    if (data.situation.algorithms[i] === data.situation.algorithms[0]) {
                        html += "<div class = 'alg-border' > ? ? ? ? ? ? ? ? ? ? </div>"
                    } else {
                        html += "<div class='alg-border' style='display: none'> ? ? ? ? ? ? ? ? ? ? </div>"
                    }
                })
                </#if>
                if (data.situation.algorithms.length == 0) {
                    html += "<div class='alg-border'> ? ? ? ? ? ? ? ? ? ? </div>"
                }
                $("#alg-body-1").append(html)
                document.getElementById('card-alg').onclick = function () {
                    let elements = document.getElementsByClassName('alg-border');
                    let list = [];
                    $.each(data.situation.algorithms, function (i) {
                        list.push((i + 1) + ") " + data.situation.algorithms[i].text);
                    })
                    if (data.situation.algorithms.length == 0) {
                        list.push("Для этой ситуации не выбрано ни одного алгоритма в качестве используемого");
                        elements[0].style.textDecoration = "none";
                    }
                    for (let i = 0; i < elements.length; i++) {
                        elements[i].textContent = list[i];
                        elements[i].style.display = "block";
                    }
                    document.getElementById('card-alg').classList.add('removed-aria-label');
                }
                console.log(data)
            }
        });
    }

    function train(situationId) {
        $.ajax({
            url: '/AlgLearning/algorithms/situation/train/' + situationId,
            type: 'POST'
        })
    }

    $("#search-input").on('input', function () {
        search()
    })

    $("#search-form").submit(function (e) {
        return false
    })

    function search() {
        let searchExpression = $("#search-input").val()
        let methodId = '${method.id}'
        $.ajax({
            dataType: "json",
            url: '/AlgLearning/api/situation/search/' + methodId + '/' + searchExpression,
            type: 'GET',
            success: function (data) {
                console.log(data)
                $("#using-form").empty()
                let html = ""
                $.each(data, function (i) {
                    let situation = data[i]
                    html += " <div class='col-4 checkbox-wrap'> <label for='" + situation.id + "'> <div class='sit-card "
                    if (situation.learningStatus === '${situationLearningStatus}') html += "learning"
                    else if (situation.learningStatus === '${situationLearnedStatus}') html += "learned"
                    html += "'> <div class='sit-card-text'>" + situation.name + "</div> <img class='sit-card-img' src='" + situation.image + "'/> </div> </label> <input type='checkbox' "
                    if (situation.trainingStatus === '${situationTrainingStatus}') html += "checked "
                    html += "class='using-check' id='" + situation.id + "'onchange=\"train('" + situation.id + "')\"> </div>"
                })
                $("#using-form").append(html)
            }
        });
    }
</script>

</body>
</html>