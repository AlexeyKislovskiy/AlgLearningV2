<#import "/spring.ftl" as spring />
<#import "macros.ftlh" as fer/>
<!DOCTYPE html>
<html>
<head>
    <@fer.commonHeadPart/>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
	<link href="<@spring.url '/css/method.css'/>" rel="stylesheet">
	<script src="<@spring.url '/js/method.js'/>"></script>
	<script src="<@spring.url '/js/algorithm-form-validation.js'/>"></script>
	<script src="<@spring.url '/js/jquery-3.6.0.min.js'/>"></script>
</head>
<body>
<div class="wrapper container">
    <@fer.navigationRow 'algorithms'/>

	<div class="row" id="row-1">
		<div class="col-6">
			<a id="arrow-a" type="button" aria-label="Назад" onclick="window.location.replace(document.referrer);">
				<img id="back-arrow" src="<@spring.url '/img/icons/backArrow.png'/>">
			</a>

			<div id="info-div" aria-label="Информация о методе" type="button" class="wrap" data-bs-toggle="modal"
				 data-bs-target="#infoModal">
				<img id="info" src="<@spring.url '/img/icons/info.png'/>">
			</div>

            <@fer.methodInfo/>

            <@fer.methodAllAction 'check' 'Добавить все ситуации в изученные' method.id/>
            <@fer.methodAllAction 'plus' 'Добавить все ситуации в изучаемые' method.id/>
            <@fer.methodAllAction 'delete' 'Удалить все ситуации' method.id/>

		</div>

		<div class="col-6 input">
			<div class="card card-stat">
				<div><span aria-label="Неизученных" id="numberOfNotLearningSituations"
						   class="unlearned">${method.numberOfNotLearningSituations}</span>/<span aria-label="Изучаемых"
																								  id="numberOfLearningSituations"
																								  class="learning">${method.numberOfLearningSituations}</span>/<span
							aria-label="Изученных" id="numberOfLearnedSituations"
							class="learned">${method.numberOfLearnedSituations}</span></div>
			</div>
			<form id="search-form" action="" method="get">
				<div id="search-div" aria-label="Выражения для поиска" type="button" data-bs-toggle="modal"
					 data-bs-target="#searchModal"><img id="search-info"
														src="<@spring.url '/img/icons/info_white.png'/>">
				</div>
				<input id="search-input" name="search" placeholder="Поиск по названию" type="search" autocomplete="off">
				<button type="submit"></button>
			</form>

            <@fer.searchInfo/>

		</div>

	</div>


	<div class="row scrolling" id="methods">

        <#list method.situations as situation>
			<div class="col-4 col-md-3">
				<form action="" method="get">
					<div class="${situation.id} card <#if situation.learningStatus==situationLearningStatus>learning
					<#elseif situation.learningStatus==situationLearnedStatus>learned</#if>" type="button"
						 data-bs-toggle="modal"
						 data-bs-target="#algModal${situation.name}">
						<div class="card-header">
							<span>${situation.name}</span>
						</div>
						<img class="card-img-top" src="<@spring.url '${situation.image}'/>">

					</div>
				</form>
			</div>

			<div class="modal fade" id="algModal${situation.name}" tabindex="-1"
				 aria-labelledby="algModalLabel${situation.name}" aria-hidden="true">
				<div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
					<div class="modal-content learned-x">
						<div class="modal-header alg-modal-body">
							<div class="modal-title col-6 alg-h2 algModalLabel" id="algModalLabel${situation.name}">
								<div class="${situation.id} card <#if situation.learningStatus==situationLearningStatus>learning
					<#elseif situation.learningStatus==situationLearnedStatus>learned</#if>">
									<div class="card-header">
										<span>${situation.name}</span>
									</div>
									<img class="card-img-top" src="<@spring.url '${situation.image}'/>">

								</div>
							</div>

                            <@fer.methodAction 'check' 'Добавить ситуацию в изученные' situation.id/>
                            <@fer.methodAction 'plus' 'Добавить ситуацию в изучаемые' situation.id/>
                            <@fer.methodAction 'delete' 'Удалить ситуацию' situation.id/>


							<div type="button" class="img-alg-div close-div" data-bs-dismiss="modal"
								 aria-label="Закрыть">
								<img id="close" class="img-alg"
									 src="<@spring.url '/img/icons/close.png'/>">
							</div>
						</div>
						<div class="modal-body alg-scrolling ">
							<div class="alg-modal-body">
								<div class="col-4 an-alg-h">Зеркальная ситуация</div>
								<div class="col-4 an-alg-h">Обратная ситуация</div>
								<div class="col-4 an-alg-h">Зеркальная и обратная ситуация</div>
							</div>
							<div class="alg-modal-body an-alg">
                                <@fer.anotherSituation situation.mirror situation/>
                                <@fer.anotherSituation situation.reverse situation/>
                                <@fer.anotherSituation situation.mirrorReverse situation/>

							</div>

							<div class="radioButton alg-modal-body">
								<form class="row" action="" method="post">
									<div class="col-7" onclick="sorting('popularity','${method.id}')"><label id="l1"
																											 for="r1">По
											популярности</label><input
												id="r1"
												class="radio popularity"
												type="radio"
												name="sort"
                                                <#if sorting=='popularity'>checked</#if>
										/>
									</div>
									<div class="col-4 length-div" onclick="sorting('length','${method.id}')"><label
												id="l2" for="r2">По
											длине</label><input
												id="r2"
												class="radio length"
												type="radio"
												name="sort"
                                                <#if sorting=='length'>checked</#if>
										/>
									</div>
								</form>
							</div>


							<div class="alg-modal-body">
								<div class="col-8">
									<span class="alg-h">Алгоритм</span>
								</div>
								<div class="col-3">
									<span class="alg-h">Используют</span>
								</div>
							</div>

							<div id="divAlgorithms-${situation.id}">
                                <#list situation.algorithms as algorithm>
									<div class="alg-modal-body alg_border <#if algorithm.verified==false>unchecked</#if>">
										<div class="col-8">
											<label for="${algorithm.id}">${algorithm.text}</label>
										</div>
										<div class="col-3">
											<label for="${algorithm.id}">${algorithm.numberOfUses}</label>
										</div>

										<div class="col-1 alg-check just_end"
											 onclick="useAlgorithm('${algorithm.id}')"
											 aria-label="<#if algorithm.usingStatus==algorithmNotUsingStatus>Отметить как используемый<#else>Снять отметку использования</#if>">
											<input type="checkbox" name="use"
												   value="${algorithm.id}"
												   id="${algorithm.id}"
                                                   <#if algorithm.usingStatus==algorithmUsingStatus>checked</#if>>
										</div>
									</div>
                                </#list>
							</div>

							<form class="alg-modal-body new-alg-form" name="new-alg" action="" method="post"
								  onsubmit="addAlgorithm('${situation.id}')">
								<input type="text" class="input3x3 new-alg-text" name="new-alg"
									   id="new-alg-text-${situation.id}"
									   placeholder="Введите алгоритм"
									   autocomplete="off">
								<div type="submit"
									 class="img-alg-div new-alg-div"
									 id="new-alg-div-1" aria-label="Добавить новый алгоритм">
									<img id="new-alg-img-${situation.id}" class="img-alg plus-new-alg"
										 onclick="addAlgorithm('${situation.id}')"
										 src="<@spring.url '/img/icons/plus_grey.png'/>">
								</div>
							</form>


						</div>
					</div>
				</div>
			</div>
        </#list>


	</div>
</div>
<@fer.moderatorButton 'К странице модерации' '/AlgLearning/moderator'/>

</body>
<script type="text/javascript">
    function getData(id, name) {
        let url
        if (name.endsWith('All')) url = '/AlgLearning/algorithms/method/' + name + '/' + id
        else url = '/AlgLearning/algorithms/situation/' + name + '/' + id
        $.ajax({
            dataType: "json",
            url: url,
            type: 'POST',
            success: function (data) {
                $("#numberOfNotLearningSituations").empty().append(data.numberOfNotLearningSituations)
                $("#numberOfLearningSituations").empty().append(data.numberOfLearningSituations)
                $("#numberOfLearnedSituations").empty().append(data.numberOfLearnedSituations)
                $("#numberOfLearned").empty().append(data.numberOfLearned)
                $("#numberOfLearning").empty().append(data.numberOfLearning)
                $.each(data.situations, function (index) {
                    if (data.situations[index].learningStatus === '${situationLearningStatus}') $("." + data.situations[index].id).attr('class', data.situations[index].id + ' card learning')
                    else if (data.situations[index].learningStatus === '${situationLearnedStatus}') $("." + data.situations[index].id).attr('class', data.situations[index].id + ' card learned')
                    else $("." + data.situations[index].id).attr('class', data.situations[index].id + ' card');
                });
            }
        });
    }

    function useAlgorithm(id) {
        let url = '/AlgLearning/algorithms/algorithm/use/' + id
        $.ajax({
            dataType: "json",
            url: url,
            type: 'POST',
            success: function (data) {
                insertAlgorithms(data)
                if ('${sorting}' === 'popularity') {
                    $(".popularity").prop('checked', true)
                    $(".length").prop('checked', false)
                } else {
                    url = '/AlgLearning/api/method/length/' + id
                    $(".length").prop('checked', true)
                    $(".popularity").prop('checked', false)
                }
            }
        });
    }

    function sorting(sortingType, id) {
        let url
        if (sortingType === 'popularity') {
            url = '/AlgLearning/api/method/popularity/' + id
            $(".popularity").prop('checked', true)
            $(".length").prop('checked', false)
        } else {
            url = '/AlgLearning/api/method/length/' + id
            $(".length").prop('checked', true)
            $(".popularity").prop('checked', false)
        }

        $.ajax({
            dataType: "json",
            url: url,
            type: 'GET',
            success: function (data) {
                insertAlgorithms(data)
            }
        });
    }

    function insertAlgorithms(data) {
        $.each(data.situations, function (i) {
            let divAlgorithms = $("#" + "divAlgorithms-" + data.situations[i].id)
            divAlgorithms.empty()
            $.each(data.situations[i].algorithms, function (j) {
                let algorithm = data.situations[i].algorithms[j]
                let html = "<div class='alg-modal-body alg_border"
                if (algorithm.verified === false) html += " unchecked"
                html += "'> <div class='col-8'> <label for='" + algorithm.id + "'>" + algorithm.text + "</label> </div> <div class='col-3'> <label for='" +
                    algorithm.id + "'>" + algorithm.numberOfUses + "</label> </div> <div class='col-1 alg-check just_end' onclick=\"useAlgorithm('" + algorithm.id +
                    "')\" aria-label='"
                if (algorithm.usingStatus === '${algorithmNotUsingStatus}') html += "Отметить как используемый";
                else html += "Снять отметку использования"
                html += "'> <input type='checkbox' name='use'id='" + algorithm.id + "'"
                if (algorithm.usingStatus === '${algorithmUsingStatus}') html += "checked"
                html += "> </div> </div>"
                divAlgorithms.append(html)
            });
        });
    }

    $("#search-input").on('input', function () {
        search()
    })

    $("#search-form").submit(function (e) {
        e.preventDefault()
    })

    $(".new-alg-form").submit(function (e) {
        e.preventDefault()
    })


    function addAlgorithm(situationId) {
        let text = $("#new-alg-text-" + situationId).val()
        $.ajax({
            dataType: "json",
            url: '/AlgLearning/algorithms/algorithm/add/' + situationId + "/" + text,
            type: 'POST',
            success: function (data) {
                insertAlgorithms(data)
            }
        });
    }

    function search() {
        let searchExpression = $("#search-input").val()
        let methodId = '${method.id}'
        $.ajax({
            dataType: "json",
            url: '/AlgLearning/api/situation/search/' + methodId + '/' + searchExpression,
            type: 'GET',
            success: function (data) {
                let divSituations = $("#methods")
                divSituations.empty()
                $.each(data, function (i) {
                    let situation = data[i]
                    let html = "<div class='col-4 col-md-3'> <form action='' method='get'> <div class='" + situation.id + " card "
                    if (situation.learningStatus === '${situationLearningStatus}') html += "learning"
                    else if (situation.learningStatus === '${situationLearnedStatus}') html += "learned"
                    html += "' type='button'data-bs-toggle='modal'data-bs-target='#algModal" + situation.name + "'> <div class='card-header'> <span>" +
                        situation.name + "</span> </div> <img class='card-img-top' src='" + situation.image + "'> </div> </form> </div> <div class='modal fade' id='algModal" +
                        situation.name + "' tabindex='-1'aria-labelledby='algModalLabel" + situation.name + "' aria-hidden='true'> <div class='modal-dialog modal-dialog-centered modal-dialog-scrollable'> <div class='modal-content learned-x'> <div class='modal-header alg-modal-body'> <div class='modal-title col-6 alg-h2 algModalLabel' id='algModalLabel" +
                        situation.name + "'> <div class='" + situation.id + " card "
                    if (situation.learningStatus === '${situationLearningStatus}') html += " learning"
                    else if (situation.learningStatus === '${situationLearnedStatus}') html += " learned"
                    html += "'> <div class='card-header'> <span>" + situation.name + "</span> </div> <img class='card-img-top' src='" + situation.image + "'> </div> </div>"
                    html += "<div type='button' class='img-alg-div' aria-label='Добавить ситуацию в изученные' onclick=\"getData('" + situation.id + "','check')\"> <img id='" + situation.id + "' class='img-alg'src='/img/icons/check_grey.png'> </div>"
                    html += "<div type='button' class='img-alg-div' aria-label='Добавить ситуацию в изучаемые' onclick=\"getData('" + situation.id + "','plus')\"> <img id='" + situation.id + "' class='img-alg'src='/img/icons/plus_grey.png'> </div>"
                    html += "<div type='button' class='img-alg-div' aria-label='Удалить ситуацию' onclick=\"getData('" + situation.id + "','delete')\"> <img id='" + situation.id + "' class='img-alg'src='/img/icons/delete_grey.png'> </div>"
                    html += " <div type='button' class='img-alg-div close-div' data-bs-dismiss='modal'aria-label='Закрыть'> <img id='close' class='img-alg'src='/img/icons/close.png'> </div> </div>"
                    html += " <div class='modal-body alg-scrolling '> <div class='alg-modal-body'> <div class='col-4 an-alg-h'>Зеркальная ситуация</div> <div class='col-4 an-alg-h'>Обратная ситуация</div> <div class='col-4 an-alg-h'>Зеркальная и обратная ситуация</div> </div> <div class='alg-modal-body an-alg'>"
                    html += "<div class='col-4'> <div class='" + situation.mirror.id + " card "
                    if (situation.mirror.learningStatus === '${situationLearningStatus}') html += " learning"
                    else if (situation.mirror.learningStatus === '${situationLearnedStatus}') html += " learned"
                    html += "'"
                    if (situation.mirror.id !== situation.id) html += "type='button' data-bs-toggle='modal'data-bs-target='#algModal" + situation.mirror.name + "'"
                    html += "> <div class='card-header'> <span>" + situation.mirror.name + "</span> </div> <img class='card-img-top' src='" + situation.mirror.image + "'> </div> </div>"
                    html += "<div class='col-4'> <div class='" + situation.reverse.id + " card"
                    if (situation.reverse.learningStatus === '${situationLearningStatus}') html += " learning"
                    else if (situation.reverse.learningStatus === '${situationLearnedStatus}') html += " learned"
                    html += "'"
                    if (situation.reverse.id !== situation.id) html += "type='button' data-bs-toggle='modal'data-bs-target='#algModal" + situation.reverse.name + "'"
                    html += "> <div class='card-header'> <span>" + situation.reverse.name + "</span> </div> <img class='card-img-top' src='" + situation.reverse.image + "'> </div> </div>"
                    html += "<div class='col-4'> <div class='" + situation.mirrorReverse.id + " card"
                    if (situation.mirrorReverse.learningStatus === '${situationLearningStatus}') html += " learning"
                    else if (situation.mirrorReverse.learningStatus === '${situationLearnedStatus}') html += " learned"
                    html += "'"
                    if (situation.mirrorReverse.id !== situation.id) html += "type='button' data-bs-toggle='modal'data-bs-target='#algModal" + situation.mirrorReverse.name + "'"
                    html += "> <div class='card-header'> <span>" + situation.mirrorReverse.name + "</span> </div> <img class='card-img-top' src='" + situation.mirrorReverse.image + "'> </div> </div>"
                    html += " </div>"
                    html += " <div class='radioButton alg-modal-body'> <form class='row' action='' method='post'> <div class='col-7' onclick=\"sorting('popularity','" + methodId + "')\"><label id='l1'for='r1'>По популярности</label><input id='r1'class='radio popularity'type='radio'name='sort'"
                    if ('${sorting}' === 'popularity') html += "checked"
                    html += " /> </div> <div class='col-4 length-div' onclick=\"sorting('length','" + methodId + "')\"><label id='l2' for='r2'>По длине</label><input id='r2'class='radio length'type='radio'name='sort'"
                    if ('${sorting}' === 'length') html += "checked"
                    html += " /> </div> </form> </div> <div class='alg-modal-body'> <div class='col-8'> <span class='alg-h'>Алгоритм</span> </div> <div class='col-3'> <span class='alg-h'>Используют</span> </div> </div>"
                    html += " <div id='divAlgorithms-" + situation.id + "'>"
                    $.each(data[i].algorithms, function (j) {
                        let algorithm = data[i].algorithms[j]
                        html += "<div class='alg-modal-body alg_border"
                        if (algorithm.verified === false) html += " unchecked"
                        html += "'> <div class='col-8'> <label for='" + algorithm.id + "'>" + algorithm.text + "</label> </div> <div class='col-3'> <label for='" +
                            algorithm.id + "'>" + algorithm.numberOfUses + "</label> </div> <div class='col-1 alg-check just_end' onclick=\"useAlgorithm('" + algorithm.id +
                            "')\" aria-label='"
                        if (algorithm.usingStatus === '${algorithmNotUsingStatus}') html += "Отметить как используемый";
                        else html += "Снять отметку использования"
                        html += "'> <input type='checkbox' name='use'id='" + algorithm.id + "'"
                        if (algorithm.usingStatus === '${algorithmUsingStatus}') html += "checked"
                        html += "> </div> </div>"
                    });
                    html += " </div>"
                    html += "<form id='new-alg-form' class='alg-modal-body' name='new-alg' action='' method='post' onsubmit=\"addAlgorithm('" + situation.id + "')\"> <input type='text' class='input3x3 new-alg-text' name='new-alg' id='new-alg-text-1' placeholder='Введите алгоритм' autocomplete='off'> <div type='submit' onclick=\"addAlgorithm('" +
                        situation.id + "')\" class='img-alg-div new-alg-div' id='new-alg-div-1' aria-label='Добавить новый алгоритм'> <img id='plus-new-alg-1' class='img-alg plus-new-alg' src='/img/icons/plus_grey.png'> </div> </form>"

                    html += " </div> </div> </div> </div>"
                    divSituations.append(html)
                    $.getScript("/js/algorithm-form-validation.js", function () {
                        input3x3()
                    });
                })
            }
        });
    }
</script>
</html>