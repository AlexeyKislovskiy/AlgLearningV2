<#import "/spring.ftl" as spring />
<#import "macros.ftlh" as fer/>
<!DOCTYPE html>
<html>
<head>
    <@fer.commonHeadPart/>
	<link href="<@spring.url '/css/settings.css'/>" rel="stylesheet">
	<script src="<@spring.url '/js/settings.js'/>"></script>
	<script src="<@spring.url '/js/jquery-3.6.0.min.js'/>"></script>
</head>
<body>
<div class="wrapper container">
    <@fer.navigationRow 'settings'/>

	<div class="row scrolling" id="setting-scrolling">
		<div class="col-1"></div>
		<div class="col-10 setting-header">
			<div type="button" class="reset-all" aria-label="Сбросить все настройки"><img class="setting-img"
																						  src="<@spring.url '/img/icons/settings.png'/>"
																						  onclick="resetSettings('ALL')">
			</div>
			Изучение
			<div type="button" id="reset-learning" class="img-r" aria-label="Сбросить все настройки в блоке"><img
						class="setting-img"
						src="<@spring.url '/img/icons/learning.png'/>" onclick="resetSettings('LEARNING')">
			</div>
		</div>
		<div class="col-1"></div>

		<div class="col-1"></div>
		<div class="col-10 setting-body">
			<div class="col-12 setting-body-header">Скрывать информацию о ситуации по умолчанию</div>
			<form class="col-12 setting-body-content" action="" method="post">
				<div><label for="hideSituationInLearningFalse">Нет</label><input type="radio"
																				 name="hideSituationInLearning"
																				 id="hideSituationInLearningFalse"
                                                                                 <#if hideSituationInLearning=='false'>checked</#if>
																				 onchange="changeSetting('hideSituationInLearning','false',this)">
				</div>
				<div><label for="hideSituationInLearningTrue">Да</label><input type="radio"
																			   name="hideSituationInLearning"
																			   id="hideSituationInLearningTrue"
                                                                               <#if hideSituationInLearning=='true'>checked</#if>
																			   onchange="changeSetting('hideSituationInLearning','true',this)">
				</div>
			</form>

			<div class="col-12 setting-body-header">Скрывать количество алгоритмов до открытия</div>
			<form class="col-12 setting-body-content" action="" method="post">
				<div><label for="hideNumberOfAlgorithmsInLearningFalse">Нет</label><input type="radio"
																						  name="hideNumberOfAlgorithmsInLearning"
																						  id="hideNumberOfAlgorithmsInLearningFalse"
                                                                                          <#if hideNumberOfAlgorithmsInLearning=='false'>checked</#if>
																						  onchange="changeSetting('hideNumberOfAlgorithmsInLearning','false',this)">
				</div>
				<div><label for="hideNumberOfAlgorithmsInLearningTrue">Да</label><input type="radio"
																						name="hideNumberOfAlgorithmsInLearning"
																						id="hideNumberOfAlgorithmsInLearningTrue"
                                                                                        <#if hideNumberOfAlgorithmsInLearning=='true'>checked</#if>
																						onchange="changeSetting('hideNumberOfAlgorithmsInLearning','true',this)">
				</div>
			</form>

			<div class="col-12 setting-body-header">Время до следующего повторения относительно последнего промежутка
			</div>
			<div class="col-12 setting-body-content">

				<div>Трудно: X
					<input type="text" class="time-multi" name="hardMultiplier" id="hardMultiplierText"
						   value="${hardMultiplier}" autocomplete="off"
						   onchange="changeSetting('hardMultiplier',this.value,this)"
					>
				</div>

				<div>Средне: X
					<input type="text" class="time-multi" name="mediumMultiplier" id="mediumMultiplierText"
						   value="${mediumMultiplier}"
						   autocomplete="off" onchange="changeSetting('mediumMultiplier',this.value,this)"
					>
				</div>

				<div>Легко: X
					<input type="text" class="time-multi" name="easyMultiplier" id="easyMultiplierText"
						   value="${easyMultiplier}"
						   autocomplete="off" onchange="changeSetting('easyMultiplier',this.value,this)"
					>
				</div>

			</div>

			<div class="col-12 setting-body-header">Полностью сбрасывать при забывании
			</div>
			<div class="col-12 setting-body-content">


				<div><label for="resetWhenForgotFalse">Нет</label><input type="radio" name="resetWhenForgot"
																		 id="resetWhenForgotFalse"
                                                                         <#if resetWhenForgot=='false'>checked</#if>
																		 onchange="changeSetting('resetWhenForgot','false',this)">
					X
					<input type="text" class="time-multi" name="againMultiplier" id="againMultiplierText"
						   value="${againMultiplier}"
						   autocomplete="off" onchange="changeSetting('againMultiplier',this.value,this)"
                           <#if resetWhenForgot=='true'>disabled</#if>
					>
				</div>
				<div><label for="resetWhenForgotTrue">Да</label><input type="radio" name="resetWhenForgot"
																	   id="resetWhenForgotTrue"
                                                                       <#if resetWhenForgot=='true'>checked</#if>
																	   onchange="changeSetting('resetWhenForgot','true',this)">
				</div>

			</div>

			<div class="col-12 setting-body-header">Базовое время первого повторения(в днях)
			</div>
			<div class="col-12 setting-body-content">

				<div>
					<input type="text" class="time-multi int-multi" name="firstInterval" id="firstIntervalText"
						   value="${firstInterval}" autocomplete="off"
						   onchange="changeSetting('firstInterval',this.value,this)"
					>
				</div>

			</div>


		</div>
		<div class="col-1"></div>


		<div class="col-1"></div>
		<div class="col-10 setting-header">
			<div type="button" class="reset-all" aria-label="Сбросить все настройки"><img class="setting-img"
																						  src="<@spring.url '/img/icons/settings.png'/>"
																						  onclick="resetSettings('ALL')">
			</div>
			Тренировка
			<div type="button" id="reset-training" class="img-r" aria-label="Сбросить все настройки в блоке"><img
						class="setting-img"
						src="<@spring.url '/img/icons/training.png'/>" onclick="resetSettings('TRAINING')">
			</div>
		</div>
		<div class="col-1"></div>

		<div class="col-1"></div>
		<div class="col-10 setting-body">
			<div class="col-12 setting-body-header">Скрывать информацию о ситуации по умолчанию</div>
			<form class="col-12 setting-body-content" action="" method="post">
				<div><label for="hideSituationInTrainingFalse">Нет</label><input type="radio"
																				 name="hideSituationInTraining"
																				 id="hideSituationInTrainingFalse"
                                                                                 <#if hideSituationInTraining=='false'>checked</#if>
																				 onchange="changeSetting('hideSituationInTraining','false',this)">
				</div>
				<div><label for="hideSituationInTrainingTrue">Да</label><input type="radio"
																			   name="hideSituationInTraining"
																			   id="hideSituationInTrainingTrue"
                                                                               <#if hideSituationInTraining=='true'>checked</#if>
																			   onchange="changeSetting('hideSituationInTraining','true',this)">
				</div>
			</form>


			<div class="col-12 setting-body-header">Скрывать количество алгоритмов до открытия</div>
			<form class="col-12 setting-body-content" action="" method="post">
				<div><label for="hideNumberOfAlgorithmsInTrainingFalse">Нет</label><input type="radio"
																						  name="hideNumberOfAlgorithmsInTraining"
																						  id="hideNumberOfAlgorithmsInTrainingFalse"
                                                                                          <#if hideNumberOfAlgorithmsInTraining=='false'>checked</#if>
																						  onchange="changeSetting('hideNumberOfAlgorithmsInTraining','false',this)">
				</div>
				<div><label for="hideNumberOfAlgorithmsInTrainingTrue">Да</label><input type="radio"
																						name="hideNumberOfAlgorithmsInTraining"
																						id="hideNumberOfAlgorithmsInTrainingTrue"
                                                                                        <#if hideNumberOfAlgorithmsInTraining=='true'>checked</#if>
																						onchange="changeSetting('hideNumberOfAlgorithmsInTraining','true',this)">
				</div>
			</form>

			<div class="col-12 setting-body-header">Включать по умолчанию</div>
			<div class="col-12 setting-body-content">
				<div><label for="defaultIncludeNotLearningInTraining">Неизученные</label><input type="checkbox"
																								id="defaultIncludeNotLearningInTraining"
                                                                                                <#if defaultIncludeNotLearningInTraining=='true'>checked</#if>
																								onchange="changeSetting('defaultIncludeNotLearningInTraining',this.checked,this)">
				</div>
				<div><label for="defaultIncludeLearningInTraining">Изучаемые</label><input type="checkbox"
																						   id="defaultIncludeLearningInTraining"
                                                                                           <#if defaultIncludeLearningInTraining=='true'>checked</#if>
																						   onchange="changeSetting('defaultIncludeLearningInTraining',this.checked,this)">
				</div>
				<div><label for="defaultIncludeLearnedInTraining">Изученные</label><input type="checkbox"
																						  id="defaultIncludeLearnedInTraining"
                                                                                          <#if defaultIncludeLearnedInTraining=='true'>checked</#if>
																						  onchange="changeSetting('defaultIncludeLearnedInTraining',this.checked,this)">
				</div>
			</div>


		</div>
		<div class="col-1"></div>


		<div class="col-1"></div>
		<div class="col-10 setting-header">
			<div type="button" class="reset-all" aria-label="Сбросить все настройки"><img class="setting-img"
																						  src="<@spring.url '/img/icons/settings.png'/>"
																						  onclick="resetSettings('ALL')">
			</div>
			Алгоритмы
			<div type="button" id="reset-algorithms" class="img-r" aria-label="Сбросить все настройки в блоке"><img
						class="setting-img"
						src="<@spring.url '/img/icons/algorithms.png'/>" onclick="resetSettings('ALGORITHMS')">
			</div>
		</div>
		<div class="col-1"></div>

		<div class="col-1"></div>
		<div class="col-10 setting-body">

			<div class="col-12 setting-body-header">Сортировка по умолчанию</div>
			<form class="col-12 setting-body-content" action="" method="post">
				<div><label for="defaultSortingPopularity">По популярности</label><input type="radio"
																						 name="defaultSorting"
																						 id="defaultSortingPopularity"
                                                                                         <#if defaultSorting=='popularity'>checked</#if>
																						 onchange="changeSetting('defaultSorting','popularity',this)">
				</div>
				<div><label for="defaultSortingLength">По длине</label><input type="radio" name="defaultSorting"
																			  id="defaultSortingLength"
                                                                              <#if defaultSorting=='length'>checked</#if>
																			  onchange="changeSetting('defaultSorting','length',this)">
				</div>
			</form>

			<div class="col-12 setting-body-header">Показывать сначала используемые алгоритмы</div>
			<div class="col-12 setting-body-content">
				<div><label for="showUsingAlgorithmsFirstFalse">Нет</label><input type="radio"
																				  name="showUsingAlgorithmsFirst"
																				  id="showUsingAlgorithmsFirstFalse"
                                                                                  <#if showUsingAlgorithmsFirst=='false'>checked</#if>
																				  onchange="changeSetting('showUsingAlgorithmsFirst','false',this)"
					></div>
				<div><label for="showUsingAlgorithmsFirstTrue">Да</label><input type="radio"
																				name="showUsingAlgorithmsFirst"
																				id="showUsingAlgorithmsFirstTrue"
                                                                                <#if showUsingAlgorithmsFirst=='true'>checked</#if>
																				onchange="changeSetting('showUsingAlgorithmsFirst','true',this)">
				</div>
			</div>

			<div class="col-12 setting-body-header">Учитывать перехваты при расчете длины</div>
			<form class="col-12 setting-body-content" action="" method="post">
				<div><label for="interceptionAsMoveFalse">Нет</label><input type="radio" name="interceptionAsMove"
																			id="interceptionAsMoveFalse"
                                                                            <#if interceptionAsMove=='false'>checked</#if>
																			onchange="changeSetting('interceptionAsMove','false',this)">
				</div>
				<div><label for="interceptionAsMoveTrue">Да</label><input type="radio" name="interceptionAsMove"
																		  id="interceptionAsMoveTrue"
                                                                          <#if interceptionAsMove=='true'>checked</#if>
																		  onchange="changeSetting('interceptionAsMove','true',this)">
				</div>
			</form>

			<div class="col-12 setting-body-header">Учитывать двойные движения как два хода</div>
			<form class="col-12 setting-body-content" action="" method="post">
				<div><label for="doubleMoveAs2MovesFalse">Нет</label><input type="radio" name="doubleMoveAs2Moves"
																			id="doubleMoveAs2MovesFalse"
                                                                            <#if doubleMoveAs2Moves=='false'>checked</#if>
																			onchange="changeSetting('doubleMoveAs2Moves','false',this)">
				</div>
				<div><label for="doubleMoveAs2MovesTrue">Да</label><input type="radio" name="doubleMoveAs2Moves"
																		  id="doubleMoveAs2MovesTrue"
                                                                          <#if doubleMoveAs2Moves=='true'>checked</#if>
																		  onchange="changeSetting('doubleMoveAs2Moves','true',this)">
				</div>
			</form>

			<div class="col-12 setting-body-header">Добавить все ситуации метода в изученные</div>
			<div class="col-12 setting-body-content">
				<div><label for="checkAllAll">Все</label><input type="radio" name="checkAll" id="checkAllAll"
                                                                <#if checkAll=='all'>checked</#if>
																onchange="changeSetting('checkAll','all',this)"></div>
				<div><label for="checkAllLearning">Только изучаемые</label><input type="radio" name="checkAll"
																				  id="checkAllLearning"
                                                                                  <#if checkAll=='learning'>checked</#if>
																				  onchange="changeSetting('checkAll','learning',this)">
				</div>
				<div><label for="checkAllNotLearning">Только неизученные</label><input type="radio" name="checkAll"
																					   id="checkAllNotLearning"
                                                                                       <#if checkAll=='notLearning'>checked</#if>
																					   onchange="changeSetting('checkAll','notLearning',this)">
				</div>
			</div>

			<div class="col-12 setting-body-header">Добавить все ситуации метода в изучаемые</div>
			<div class="col-12 setting-body-content">
				<div><label for="plusAllAll">Все</label><input type="radio" name="plusAll"
															   id="plusAllAll"
                                                               <#if plusAll=='all'>checked</#if>
															   onchange="changeSetting('plusAll','all',this)"></div>
				<div><label for="plusAllLearned">Только изученные</label><input type="radio" name="plusAll"
																				id="plusAllLearned"
                                                                                <#if plusAll=='learned'>checked</#if>
																				onchange="changeSetting('plusAll','learned',this)">
				</div>
				<div><label for="plusAllNotLearning">Только неизученные</label><input type="radio" name="plusAll"
																					  id="plusAllNotLearning"
                                                                                      <#if plusAll=='notLearning'>checked</#if>
																					  onchange="changeSetting('plusAll','notLearning',this)">
				</div>
			</div>

			<div class="col-12 setting-body-header">Удалить все ситуации метода</div>
			<div class="col-12 setting-body-content">
				<div><label for="deleteAllAll">Все</label><input type="radio" name="deleteAll"
																 id="deleteAllAll"
                                                                 <#if deleteAll=='all'>checked</#if>
																 onchange="changeSetting('deleteAll','all',this)"
					></div>
				<div><label for="deleteAllLearned">Только изученные</label><input type="radio" name="deleteAll"
																				  id="deleteAllLearned"
                                                                                  <#if deleteAll=='learned'>checked</#if>
																				  onchange="changeSetting('deleteAll','learned',this)">
				</div>
				<div><label for="deleteAllLearning">Только изучаемые</label><input type="radio" name="deleteAll"
																				   id="deleteAllLearning"
                                                                                   <#if deleteAll=='learning'>checked</#if>
																				   onchange="changeSetting('deleteAll','learning',this)">
				</div>
			</div>

			<div class="col-12 setting-body-header">Отображать неподтвержденные пользовательские алгоритмы</div>
			<div class="col-12 setting-body-content">
				<div><label for="showUnverifiedAlgorithmsFalse">Нет</label><input type="radio"
																				  name="showUnverifiedAlgorithms"
																				  id="showUnverifiedAlgorithmsFalse"
                                                                                  <#if showUnverifiedAlgorithms=='false'>checked</#if>
																				  onchange="changeSetting('showUnverifiedAlgorithms','false',this)">
				</div>
				<div><label for="showUnverifiedAlgorithmsTrue">Да</label><input type="radio"
																				name="showUnverifiedAlgorithms"
																				id="showUnverifiedAlgorithmsTrue"
                                                                                <#if showUnverifiedAlgorithms=='true'>checked</#if>
																				onchange="changeSetting('showUnverifiedAlgorithms','true',this)">
				</div>
			</div>

			<div class="col-12 setting-body-header">Спрашивать подтверждение действий добавления</div>
			<div class="col-12 setting-body-content">
				<div><label for="add-ask1">Нет</label><input type="radio" name="add-ask" id="add-ask1"
															 checked></div>
				<div><label for="add-ask2">Да</label><input type="radio" name="add-ask" id="add-ask2">
				</div>
			</div>

			<div class="col-12 setting-body-header">Спрашивать подтверждение действий удаления</div>
			<div class="col-12 setting-body-content">
				<div><label for="delete-ask1">Нет</label><input type="radio" name="delete-ask" id="delete-ask1" checked
					></div>
				<div><label for="delete-ask2">Да</label><input type="radio" name="delete-ask" id="delete-ask2"
					>
				</div>
			</div>

			<div class="col-12 setting-body-header">Отмечать добавленный алгоритм как используемый</div>
			<div class="col-12 setting-body-content">
				<div><label for="useAddedAlgorithmFalse">Нет</label><input type="radio" name="useAddedAlgorithm"
																		   id="useAddedAlgorithmFalse"
                                                                           <#if useAddedAlgorithm=='false'>checked</#if>
																		   onchange="changeSetting('useAddedAlgorithm','false',this)">
				</div>
				<div><label for="useAddedAlgorithmTrue">Да</label><input type="radio" name="useAddedAlgorithm"
																		 id="useAddedAlgorithmTrue"
                                                                         <#if useAddedAlgorithm=='true'>checked</#if>
																		 onchange="changeSetting('useAddedAlgorithm','true',this)">
				</div>
			</div>
		</div>
		<div class="col-1"></div>


		<div class="col-1"></div>
		<div class="col-10 setting-header">
			<div type="button" class="reset-all" aria-label="Сбросить все настройки"><img class="setting-img"
																						  src="<@spring.url '/img/icons/settings.png'/>"
																						  onclick="resetSettings('ALL')">
			</div>
			Аккаунт
			<div type="button" id="reset-account" class="img-r" aria-label="Сбросить все настройки в блоке"><img
						class="setting-img"
						src="<@spring.url '/img/icons/account.png'/>" onclick="resetSettings('ACCOUNT')">
			</div>
		</div>
		<div class="col-1"></div>

		<div class="col-1"></div>
		<div class="col-10 setting-body">
			<div class="col-12 setting-body-header">Установить фоновое изображение</div>
			<form id="background-form" class="col-12 setting-body-content" action="/AlgLearning/files/upload"
				  method="post"
				  enctype="multipart/form-data">
				<input type="file" name="background-img" accept="image/*">
				<input type="submit" class="sub-button">
			</form>

			<div class="col-12 setting-body-header">Поменять никнейм</div>
			<div class="col-12 setting-body-content">

				<div>
					<input type="text" class="login-change" name="login-change" id="login-change"
						   placeholder="Введите новый никнейм" autocomplete="off"
					>
				</div>

			</div>

			<div class="col-12 setting-body-header">Поменять пароль</div>
			<div class="col-12 setting-body-content">

				<div>
					<input type="password" class="password-change" name="old-password" id="old-password"
						   placeholder="Введите старый пароль" autocomplete="off"
					>
				</div>

				<div>
					<input type="password" class="password-change" name="new-password" id="new-password"
						   placeholder="Введите новый пароль" autocomplete="off" disabled
					>
				</div>

			</div>

			<div class="col-12 setting-body-header">Добавить/сменить электронную почту</div>
			<div class="col-12 setting-body-content">

				<div>
					<input type="email" name="email" id="email"
						   placeholder="Введите адрес электронной почты" autocomplete="off"
					>
				</div>

			</div>

		</div>
		<div class="col-1"></div>


	</div>
</div>
<@fer.moderatorButton 'К странице модерации' '/AlgLearning/moderator'/>
<script>
    function changeSetting(name, value, element) {
        if ($(element).is(':radio') && !$(element).is(':checked')) return
        if ($(element).is(':text')) {
            setTimeout(function () {
                value = $(element).val()
                sendAjax(name, value)
            }, 100)
        } else sendAjax(name, value)
    }

    function resetSettings(block) {
        $.ajax({
            url: '/AlgLearning/settings/' + block,
            type: 'POST',
            success: function () {
                if (block === 'ALL' || block === 'LEARNING') {
                    $("#hideSituationInLearningTrue").prop("checked", true)
                    $("#hideNumberOfAlgorithmsInLearningFalse").prop("checked", true)
                    $("#hardMultiplierText").prop("value", 1)
                    $("#mediumMultiplierText").prop("value", 1.5)
                    $("#easyMultiplierText").prop("value", 2)
                    $("#againMultiplierText").prop("value", 0.1).prop("disabled", true)
                    $("#resetWhenForgotTrue").prop("checked", true)
                    $("#firstIntervalText").prop("value", 1)
                }
                if (block === 'ALL' || block === 'TRAINING') {
                    $("#hideSituationInTrainingTrue").prop("checked", true)
                    $("#hideNumberOfAlgorithmsInTrainingFalse").prop("checked", true)
                    $("#defaultIncludeNotLearningInTraining").prop("checked", false)
                    $("#defaultIncludeLearningInTraining").prop("checked", true)
                    $("#defaultIncludeLearnedInTraining").prop("checked", true)
                }
                if (block === 'ALL' || block === 'ALGORITHMS') {
                    $("#defaultSortingPopularity").prop("checked", true)
                    $("#showUsingAlgorithmsFirstFalse").prop("checked", true)
                    $("#interceptionAsMoveFalse").prop("checked", true)
                    $("#doubleMoveAs2MovesFalse").prop("checked", true)
                    $("#checkAllAll").prop("checked", true)
                    $("#plusAllNotLearning").prop("checked", true)
                    $("#deleteAllAll").prop("checked", true)
                    $("#showUnverifiedAlgorithmsFalse").prop("checked", true)
                }

                if (block === 'ALL' || block === 'ACCOUNT') {
                    $('body').css("background", "#000 url(/img/background.jpg) fixed").css("min-height", "100%")
                        .css("background-size", "cover").css("overflow", "hidden").css("color", "#fff")
                }
            }
        });
    }

    function sendAjax(name, value) {
        let setting = {
            name: name,
            value: value
        }
        $.ajax({
            url: '/AlgLearning/settings',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(setting)
        });
    }

    $("#background-form").submit(function (e) {

        e.preventDefault();

        let form = $(this);
        let actionUrl = form.attr('action');

        $.ajax({
            type: "POST",
            url: actionUrl,
            data: new FormData(this),
            processData: false,
            contentType: false,
            dataType: "json",
            success: function (data) {
                sendAjax('background', data.link)
                $('body').css("background", "#000 url(" + data.link + ") fixed").css("min-height", "100%")
                    .css("background-size", "cover").css("overflow", "hidden").css("color", "#fff")
            }
        });

    });
</script>
</body>
</html>