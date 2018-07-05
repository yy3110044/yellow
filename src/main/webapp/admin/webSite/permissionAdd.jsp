<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%><!DOCTYPE html>
<html>
<head>
<base href="${basePath}"/>
<meta http-equiv="X-UA-COMPATIBLE" content="IE=edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>添加权限</title>
<link rel="stylesheet" href="admin/css/bootstrap.css">
<link rel="stylesheet" href="admin/css/css.css">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript">
var add = function(){
	var level = $.trim($("#level").val());
	var watchMovieCount = $.trim($("#watchMovieCount").val());
	if(empty(level) || empty(watchMovieCount)) {
		$("#showMsg").html("请输入参数");
		return;
	}
	loadData({
		url : "administration/permissionAdd",
		data : {
			"level" : level,
			"watchMovieCount" : watchMovieCount
		},
		success : function(data) {
			if(data.code == 100) {
				$("#showMsg").html(data.msg);
				$("#level").val("");
				$("#watchMovieCount").val("");
			} else {
				$("#showMsg").html(data.msg);
			}
		}
	});
};
</script>
</head>
<body>
<%@include file="/admin/header.jsp"%>
<div id="middle">
<jsp:include page="/admin/left.jsp">
	<jsp:param name="p" value="添加权限"/>
</jsp:include>
	<div class="right">
	<div class="right_cont">
	<div class="breadcrumb">当前位置：
		<a href="javascript:;">网站管理</a><span class="divider">/</span>
		<a href="javascript:;">添加权限</a>
	</div>
	<div class="title_right"><strong>添加权限</strong><span style="color:red;font-size:18px;padding-left:200px;" id="showMsg"></span></div>
	<div style="width:900px; margin:auto">
	<table class="table table-bordered">
		<tr>
			<td width="12%" align="right" nowrap="nowrap" bgcolor="#f1f1f1">用户级别：</td>
			<td><input type="text" id="level" placeholder="-1代表游客"></td>
		</tr>
		<tr>
			<td align="right" nowrap="nowrap" bgcolor="#f1f1f1">每日观影数量：</td>
			<td><input type="text" id="watchMovieCount" placeholder="请输入一个数字"></td>
		</tr>
		<tr>
			<td align="right" nowrap="nowrap" bgcolor="#f1f1f1"></td>
			<td colspan="3"><input type="button" value="添加" class="btn btn-info" style="width:80px;" onclick="add()"></td>
		</tr>
	</table>
	</div> 
	</div>     
	</div>
</div>
</body>
</html>