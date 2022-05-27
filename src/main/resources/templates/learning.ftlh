<#import "/spring.ftl" as spring />
<#import "macros.ftlh" as fer/>
<!DOCTYPE html>
<html>
<head>
    <@fer.commonHeadPart/>
	<link href="<@spring.url '/css/learning.css'/>" rel="stylesheet">
	<script src="<@spring.url '/js/algorithms.js'/>"></script>
</head>
<body>
<div class="wrapper container">
    <@fer.navigationRow 'learning'/>

    <@fer.cubeIcons/>

	<div class="row row-x scrolling" id="methods">
		<#list methods as method>
			<div class="col-6 col-md-4">
				<a href="<@spring.url '/AlgLearning/learning/method/${method.name}'/>">
					<div class="card">
						<div class="card-header">
							<div class="row">
								<div class="col-6">
									<span>${method.name}</span>
								</div>
								<div class="col-6 info-3-num-div">
                                <span><span aria-label="Новых" class="new">${method.numberOfNew}</span>/<span
											aria-label="Забытых"
											class="forgotten">${method.numberOfForgot}</span>/<span
											aria-label="Для повторения" class="repeat">${method.numberOfRepeat}</span></span>
								</div>
							</div>
						</div>
						<img class="card-img-top" src="<@spring.url '${method.image}'/>">
					</div>
				</a>
			</div>
		</#list>

	</div>
</div>
<@fer.moderatorButton 'К странице модерации' '/AlgLearning/moderator'/>
</body>
</html>