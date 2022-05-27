<#import "/spring.ftl" as spring />
<#import "macros.ftlh" as fer/>
<!DOCTYPE html>
<html>
<head>
    <@fer.commonHeadPart/>
	<link href="<@spring.url '/css/algorithms.css'/>" rel="stylesheet">
	<script src="<@spring.url '/js/algorithms.js'/>"></script>
	<link href="<@spring.url '/css/account.css'/>" rel="stylesheet">
	<style>
        .error-text {
            display: flex;
            justify-content: space-around;
            align-items: center;
            font-size: 4vw;
            background-color: rgba(0, 0, 0, 0.75);
            border-radius: 5vw;
            margin-top: 1vw;
        }

        .error {
            font-size: 20vw;
            color: red;
        }
	</style>
</head>
<body>
<div class="wrapper container">
    <@fer.navigationRow ''/>
	<div class="row">
		<div class="col-1"></div>
		<div id="error-header" class="col-10 error-text">
			Произошла непредвиденная ошибка
		</div>
		<div class="col-1"></div>
		<div class="col-1"></div>
		<div class="col-10 acc-card">
			<div class="acc-header" id="acc-header">
				Статусный код ошибки
			</div>
			<div class="acc-body error" id="acc-info">
                ${status}
			</div>
		</div>
	</div>

</div>
<@fer.moderatorButton 'К странице модерации' '/AlgLearning/moderator'/>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        setTimeout(setHeight, 100);
        setHeight();
        window.addEventListener(`resize`, event => setHeight(), false);

        function setHeight() {
            let new_height = window.innerHeight - document.getElementById('navigation').offsetHeight - document.getElementById('error-header').offsetHeight
                - document.getElementById('acc-header').offsetHeight - 60;
            document.getElementById('acc-info').style.height = new_height + 'px';
        }
    })

</script>
</body>
</html>