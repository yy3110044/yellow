<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%><!DOCTYPE html>
<html>
<head>
<base href="${basePath}"/>
<meta http-equiv="X-UA-COMPATIBLE" content="IE=edge,chrome=1"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改管理员密码</title>
<link rel="stylesheet" href="admin/css/bootstrap.css" />
<link rel="stylesheet" href="admin/css/css.css" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript">
var modifyAdminUserPassWord = function(){
	var oldPassWord = $("#oldPassWord").val();
	var newPassWord1 = $("#newPassWord1").val();
	var newPassWord2 = $("#newPassWord2").val();
	if(newPassWord1 != newPassWord2) {
		$("#showMsg").html("两次输入密码不一致");
		return;
	}
	loadData({
		url : "administration/modifyAdminUserPassWord",
		data : {
			"oldPassWord" : oldPassWord,
			"newPassWord" : newPassWord1
		},
		success : function(data){
			$("#showMsg").html(data.msg);
			alert(data.msg);
		},
		redirectUrl : "admin/login.jsp?msg=" + encodeURI("请先登录")
	});
};
</script>
</head>
<body>
<%@include file="/admin/header.jsp"%>
<div id="middle">
<jsp:include page="/admin/left.jsp">
	<jsp:param name="p" value="修改管理员密码"/>
</jsp:include>
	<div class="right">
	<div class="right_cont">
	<div class="breadcrumb">当前位置：
		<a href="javascript:;">网站管理</a><span class="divider">/</span>
		<a href="javascript:;">修改管理员密码</a>
	</div>
	<div class="title_right"><strong>修改管理员密码</strong><span style="color:red;font-size:18px;padding-left:200px;" id="showMsg"></span></div>
	<div style="width:900px; margin:auto">
	<table class="table table-bordered">
		<tr>
			<td width="12%" align="right" nowrap="nowrap" bgcolor="#f1f1f1">原密码：</td>
			<td><input type="password" id="oldPassWord"></td>
		</tr>
		<tr>
			<td align="right" nowrap="nowrap" bgcolor="#f1f1f1">新密码：</td>
			<td><input type="password" id="newPassWord1"></td>
		</tr>
		<tr>
			<td align="right" nowrap="nowrap" bgcolor="#f1f1f1">再次输入新密码：</td>
			<td><input type="password" id="newPassWord2"></td>
		</tr>
		<tr>
			<td align="right" nowrap="nowrap" bgcolor="#f1f1f1"></td>
			<td><input type="button" value="修改" class="btn btn-info" style="width:80px;" onclick="modifyAdminUserPassWord()"></td>
		</tr>
	</table>
	</div> 
	</div>     
	</div>
</div>
</body>
</html>