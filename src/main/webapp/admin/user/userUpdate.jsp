<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%><!DOCTYPE html>
<html>
<head>
<base href="${basePath}"/>
<meta http-equiv="X-UA-COMPATIBLE" content="IE=edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>修改用户</title>
<link rel="stylesheet" href="admin/css/bootstrap.css">
<link rel="stylesheet" href="admin/css/css.css">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript">
var userId = '${param.userId}';
$(document).ready(function(){
	//先加载权限数据
	loadData({
		url : "administration/permissionList",
		success : function(data) {
			if(data.code == 100) {
				var list = data.result;
				var str = '';
				for(var i=0; i<list.length; i++) {
					var obj = list[i];
					str += '<option value="' + obj.level + '">' + obj.level + '----' + obj.watchMovieCount + '</option>';
				}
				$("#level").html(str);
				
				//再加载用户数据
				loadData({
					url : "administration/userDetail",
					data : {
						"userId" : userId
					},
					success : function(data) {
						if(data.code == 100) {
							$("#nickName").val(data.result.nickName);
							$("#phone").val(data.result.phone);
							$("#email").val(data.result.email);
							$("#level").val(data.result.level);
						}
					}
				});
			}
		}
	});
});
var updateUser = function(){
	var passWord = $("#passWord").val();
	var nickName = $.trim($("#nickName").val());
	var phone = $.trim($("#phone").val());
	var email = $.trim($("#email").val());
	var level = $.trim($("#level").val());
	
	loadData({
		url : "administration/userUpdate",
		data : {
			"userId" : userId,
			"passWord" : passWord,
			"nickName" : nickName,
			"phone" : phone,
			"email" : email,
			"level" : level
		},
		success : function(data) {
			$("#showMsg").html(data.msg);
			if(data.code == 100) {
			} else {
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
	<jsp:param name="p" value="修改用户"/>
</jsp:include>
	<div class="right">
	<div class="right_cont">
	<div class="breadcrumb">当前位置：
		<a href="javascript:;">用户管理</a><span class="divider">/</span>
		<a href="javascript:;">修改用户</a>
	</div>
	<div class="title_right"><strong>修改用户</strong><span style="color:red;font-size:18px;padding-left:200px;" id="showMsg"></span></div>
	<div style="width:900px; margin:auto">
	<table class="table table-bordered">
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
			<td colspan="3"><input type="button" value="修改" onclick="updateUser()" class="btn btn-info" style="width:80px;"></td>
		</tr>
	</table>
	</div> 
	</div>     
	</div>
</div>
</body>
</html>