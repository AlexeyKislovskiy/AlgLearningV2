<#import "/spring.ftl" as spring />
<#import "macros.ftlh" as fer/>
<!DOCTYPE html>
<html>
<head>
    <@fer.commonHeadPart/>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
	<link href="<@spring.url '/css/moderator.css'/>" rel="stylesheet">
	<script src="<@spring.url '/js/moderator.js'/>"></script>
	<script src="<@spring.url '/js/algorithm-form-validation.js'/>"></script>
	<script src="<@spring.url '/js/jquery-3.6.0.min.js'/>"></script>
</head>
<body>

<div class="wrapper container">
	<div class="row" id="header-row">
		<div class="col-2 head-mod l-head">
			Добавил
		</div>
		<div class="col-2 head-mod c-head">
			Метод
		</div>
		<div class="col-3 head-mod c-head">
			Ситуация
		</div>
		<div class="col-5 head-mod r-head">
			Алгоритм
		</div>
	</div>
	<div id="algorithms" class="row" id="content-row">
        <#list algorithms as algorithm>
			<div class="col-2 content-mod">
                ${algorithm.addCuberNickname}
			</div>
			<div class="col-2 content-mod">
                ${algorithm.methodName}
			</div>
			<div class="col-3 content-mod">
                ${algorithm.situationName}
				<img class="situation-img" src="<@spring.url '${algorithm.situationImage}'/>">
			</div>
			<div class="col-4 content-mod alg-text">
				<input type="text" form="${algorithm.id}" class="input3x3 new-alg-text" name="plus-text"
					   id="new-alg-text-1"
					   value="${algorithm.text}" autocomplete="off">
			</div>
			<div class="col-1 content-mod">
				<form id="${algorithm.id}" action="" method="post" class="plus" aria-label="Добавить"
					  onclick="verify('${algorithm.id}')">
					<img type="button" class="button-img"
						 src="<@spring.url '/img/icons/plus.png'/>">
				</form>
				<form action="" method="post" class="delete" aria-label="Удалить"
					  onclick="notVerify('${algorithm.id}')">
					<img type="button" class="button-img"
						 src="<@spring.url '/img/icons/delete.png'/>">
				</form>
			</div>
        </#list>

	</div>
</div>
<@fer.moderatorButton 'Вернуться назад' 'return'/>
<script>
    function verify(algorithmId) {
        $.ajax({
            dataType: "json",
            url: '/AlgLearning/moderator/verify/' + algorithmId,
            type: 'POST',
            success: function (data) {
                insertAlgorithms(data)
            }
        });
    }

    function notVerify(algorithmId) {
        $.ajax({
            dataType: "json",
            url: '/AlgLearning/moderator/notVerify/' + algorithmId,
            type: 'POST',
            success: function (data) {
                insertAlgorithms(data)
            }
        });
    }

    function insertAlgorithms(data) {
        let div = $("#algorithms")
        div.empty()
        $.each(data, function (i) {
            let algorithm = data[i]
            let html = "<div class='col-2 content-mod'>" + algorithm.addCuber.nickname + "</div> <div class='col-2 content-mod'>" +
                algorithm.methodName + " </div> <div class='col-3 content-mod'>" + algorithm.situationName + " <img class='situation-img' src='" + algorithm.situationImage
                + "'> </div> <div class='col-4 content-mod alg-text'> <input type='text' form='" + algorithm.id + "' class='input3x3 new-alg-text' name='plus-text' id = 'new-alg-text-1' value = '" +
                algorithm.text + "'autocomplete = 'off' > </div> <div class='col-1 content-mod'> <form id='" + algorithm.id + "' action=' method=' post' class=' plus' aria-label=' Добавить' onclick=\" verify('" + algorithm.id +
                "')\"> <img type='button' class='button-img' src='<@spring.url '/img/icons/plus.png'/>'> </form> <form action=' method=' post' class=' delete' aria-label=' Удалить' onclick=\" notVerify('" + algorithm.id +
                "')\"> <img type = 'button' class = 'button-img' src = '<@spring.url '/img/icons/delete.png'/>' > </form> </div>"
            div.append(html)
        });
    }
</script>
</body>
</html>