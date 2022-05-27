<#import "/spring.ftl" as spring />
<#import "macros.ftlh" as fer/>
<!DOCTYPE html>
<html>
<head>
    <@fer.commonHeadPart/>
	<link href="<@spring.url '/css/algorithms.css'/>" rel="stylesheet">
	<script src="<@spring.url '/js/algorithms.js'/>"></script>
</head>
<body>
<div class="wrapper container">
    <@fer.navigationRow 'algorithms'/>
    <@fer.cubeIcons/>
    <@fer.algorithmsRow '/AlgLearning/algorithms/method/'/>
    <@fer.moderatorButton 'К странице модерации' '/AlgLearning/moderator'/>
</div>
</body>
</html>