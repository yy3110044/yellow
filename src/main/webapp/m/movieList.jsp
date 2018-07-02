<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><!DOCTYPE html>
<html>
<head>
<base href="${basePath}">
<meta http-equiv="content-type" content="text/html;charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="format-detection" content="telephone=no">
<script type="text/javascript" src="js/jquery.js"></script>
<style type="text/css">
body {background-color:#eaeaea;}
.item{background-color:#ffffff;border-radius:5px;margin-bottom:10px;}
.item .showImg{width:100%;height:150px;border-top-left-radius:5px;border-top-right-radius:5px;}
.item .title{color:#000000;font-size:20px;text-align:left;padding-left:4px;padding-right:4px;}
.item .price{color:#000000;font-size:18px;text-align:left;padding-left:4px;}
.item .price span{font-weight:bold;color:red;}
.item .time{color:#999;font-size:14px;text-align:right;padding-left:4px;}
</style>
<script type="text/javascript">
$(document).ready(function(){
	
});
</script>
</head>
<body>

	<div class="item" data-id="${obj.id}">
		<div><img class="showImg" src="${obj.picUrl}"></div>
		<div class="title">有表上全脸胗脸脸色人有脸有</div>
		<div class="time">发布时间：2012-12-25</div>
	</div>

</body>
</html>