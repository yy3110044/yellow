<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%><!DOCTYPE html>
<html>
<head>
<base href="${basePath}"/>
<meta http-equiv="X-UA-COMPATIBLE" content="IE=edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>添加用户</title>
<link rel="stylesheet" href="admin/css/bootstrap.css">
<link rel="stylesheet" href="admin/css/css.css">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	loadData({
		url : "administration/permissionList",
		success : function(data) {
			if(data.code == 100) {
				var list = data.result;
				var str = '';
				for(var i=0; i<list.length; i++) {
					var obj = list[i];
					if(obj.level != -1) {
						str += '<option value="' + obj.level + '">' + obj.level + '----' + obj.watchMovieCount + '</option>';
					}
				}
				$("#level").html(str);
			}
		}
	});
});
var addUser = function(){
	var userName = $.trim($("#userName").val());
	var passWord = $("#passWord").val();
	var nickName = $.trim($("#nickName").val());
	var phone = $.trim($("#phone").val());
	var email = $.trim($("#email").val());
	var level = $.trim($("#level").val());
	
	loadData({
		url : "administration/userAdd",
		data : {
			"userName" : userName,
			"passWord" : passWord,
			"nickName" : nickName,
			"phone" : phone,
			"email" : email,
			"level" : level
		},
		success : function(data) {
			if(data.code == 100) {
				$("#showMsg").html(data.msg);
				$("#userName").val("");
				$("#passWord").val("");
				$("#nickName").val("");
				$("#phone").val("");
				$("#email").val("");
				$("#level").val("");
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
	<jsp:param name="p" value="添加用户"/>
</jsp:include>
	<div class="right">
	<div class="right_cont">
	<div class="breadcrumb">当前位置：
		<a href="javascript:;">用户管理</a><span class="divider">/</span>
		<a href="javascript:;">添加用户</a>
	</div>
	<div class="title_right"><strong>添加用户</strong><span style="color:red;font-size:18px;padding-left:200px;" id="showMsg"></span></div>
	<div style="width:900px; margin:auto">
	<table class="table table-bordered">
		<tr>
			<td width="12%" align="right" nowrap="nowrap" bgcolor="#f1f1f1">用户名：</td>
			<td><input type="text" id="userName"></td>
		</tr>
		<tr>
			<td align="right" nowrap="nowrap" bgcolor="#f1f1f1">密码：</td>
			<td><input type="text" id="passWord"></td>
		</tr>
		<tr>
			<td align="right" nowrap="nowrap" bgcolor="#f1f1f1">昵称：</td>
			<td><input type="text" id="nickName"></td>
		</tr>
		<tr>
			<td align="right" nowrap="nowrap" bgcolor="#f1f1f1">电话：</td>
			<td><input type="text" id="phone"></td>
		</tr>
		<tr>
			<td align="right" nowrap="nowrap" bgcolor="#f1f1f1">邮箱：</td>
			<td><input type="text" id="email"></td>
		</tr>
		<tr>
			<td align="right" nowrap="nowrap" bgcolor="#f1f1f1">等级：</td>
			<td><select id="level" style="width:90px;"></select></td>
		</tr>
		<tr>
			<td align="right" nowrap="nowrap" bgcolor="#f1f1f1"></td>
			<td colspan="3"><input type="button" value="添加" onclick="addUser()" class="btn btn-info" style="width:80px;"></td>
		</tr>
	</table>
	</div> 
	</div>     
	</div>
</div>
</body>
</html>