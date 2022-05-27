<#import "/spring.ftl" as spring />
<#import "macros.ftlh" as fer/>
<!DOCTYPE html>
<html>
<head>
    <@fer.commonHeadPart/>
	<link href="<@spring.url '/css/method-learning.css'/>" rel="stylesheet">
	<script src="<@spring.url '/js/method-learning.js'/>"></script>
	<script src="<@spring.url '/js/jquery-3.6.0.min.js'/>"></script>
</head>
<body>
<div class="wrapper container">
    <@fer.navigationRow 'learning'/>

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

		<div class="col-3 num-3-div">
			<div class="num-3">
                <span aria-label="Новых"
					  class="new <#if situation.cuberLearningSituationStatus==learningNewStatus>current-learn</#if>">${method.numberOfNew}</span>/<span
						aria-label="Забытых"
						class="forgotten <#if situation.cuberLearningSituationStatus==learningForgotStatus>current-learn</#if>">${method.numberOfForgot}</span>/<span
						aria-label="Для повторения"
						class="repeat <#if situation.cuberLearningSituationStatus==learningRepeatStatus>current-learn</#if>">${method.numberOfRepeat}</span>
			</div>
		</div>
	</div>

	<div class="row row-x" id="row-1">
		<div class="col-md-1 col-3"></div>
		<div class="col-md-3 col-6 cc">
			<div class="card" aria-label="Показать информацию" id="hidden-card">
				<div class="card-header" id="hidden-card-text">
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
				<div class="card-header">
					Алгоритмы
				</div>

				<div class="alg-body" id="alg-body-1">
                    <#if hideNumberOfAlgorithmsInLearning=='false'>
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
            <@fer.learningButton 'again' 'Снова'/>
            <@fer.learningButton 'hard' 'Трудно'/>
            <@fer.learningButton 'medium' 'Средне'/>
            <@fer.learningButton 'easy' 'Легко'/>
		</div>
	</div>


</div>
<@fer.moderatorButton 'К странице модерации' '/AlgLearning/moderator'/>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        function showHiddenCard() {
            document.getElementById('hidden-card').classList.add('removed-aria-label');
            document.getElementById('hidden-card-text').textContent = "${situation.name}";
            document.getElementById('hidden-card-img').src = "<@spring.url '${situation.image}'/>";
        }

        <#if hideSituationInLearning=='true'>
        document.getElementById('hidden-card').onclick = function () {
            showHiddenCard();
        }
        <#else>
        showHiddenCard();
        </#if>

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
    })

    function next(name) {
        let situationId = '${situation.id}'
        $.ajax({
            dataType: "json",
            url: '/AlgLearning/algorithms/method/nextLearning/' + name + '/' + situationId,
            type: 'POST',
            error: function () {
                window.location.replace("/AlgLearning/learning");
            },
            success: function (data) {
                $(".scramble").empty().append(data.scramble)
                $("#hidden-card-text").empty().append("?")
                if (data.situation.learningStatus === '${situationLearningStatus}') $(".card-header").attr('class', 'card-header learning')
                else if (data.situation.learningStatus === '${situationLearnedStatus}') $(".card-header").attr('class', 'card-header learned')
                else $(".card-header").attr('class', 'card-header')
                if (data.situation.cuberLearningSituationStatus == '${learningNewStatus}') {
                    $(".new").addClass('current-learn')
                    $(".forgotten").removeClass('current-learn')
                    $(".repeat").removeClass('current-learn')
                } else if (data.situation.cuberLearningSituationStatus == '${learningForgotStatus}') {
                    $(".forgotten").addClass('current-learn')
                    $(".new").removeClass('current-learn')
                    $(".repeat").removeClass('current-learn')
                } else {
                    $(".repeat").addClass('current-learn')
                    $(".forgotten").removeClass('current-learn')
                    $(".new").removeClass('current-learn')
                }
                $(".new").empty().append(data.numberOfNew)
                $(".forgotten").empty().append(data.numberOfForgot)
                $(".repeat").empty().append(data.numberOfRepeat)
                $("#hidden-card-img").attr('src', '/img/icons/unknown.png')

                function showHiddenCard() {
                    document.getElementById('hidden-card').classList.add('removed-aria-label');
                    document.getElementById('hidden-card-text').textContent = data.situation.name;
                    document.getElementById('hidden-card-img').src = data.situation.image;
                }

                <#if hideSituationInLearning=='true'>
                document.getElementById('hidden-card').classList.remove('removed-aria-label');
                document.getElementById('hidden-card').onclick = function () {
                    showHiddenCard();
                }
                <#else>
                showHiddenCard();
                </#if>
                $("#alg-body-1").empty()

                let html = ""
                <#if hideNumberOfAlgorithmsInLearning=='false'>
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

</script>
</body>
</html>