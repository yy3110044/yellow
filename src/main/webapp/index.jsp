<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><!DOCTYPE html>
<html>
<head>
<base href="${basePath}">
<meta http-equiv="content-type" content="text/html;charset=utf-8">
<link href="css/video-js.min.css" rel="stylesheet">
<script src="js/video.min.js"></script>
<script src="https://unpkg.com/vue/dist/vue.js"></script>
<script>
var v = null;
var data = {d :  [{id : 23, name : "杨"},{id : 34, name : "李"}]};
var load = function(){
	v = new Vue({
        el : "#app",
        data : data
    });
};

var change = function(){
    alert(v.$data.message);
};
</script>
</head>
<body onload="load()">
<!-- 
	<video id="my-video" class="video-js" controls preload="auto" width="640" height="264" poster="https://www.baidu.com/img/bd_logo1.png">
		<source src="http://185.38.13.159//mp43/271102.mp4?st=ugHWqC2KgR0QPCWg9BHwbQ&e=1530532907" type="video/mp4">
	</video>
-->
<div>
    <a href="test.jsp">test</a>
    <a href="javascript:change();">change</a>
</div>

<div id="app">
    <p v-for="(key, value) in d">{{key}}&nbsp;{{value}}</p>
</div>
</body>
</html>