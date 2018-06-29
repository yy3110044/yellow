<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%><!DOCTYPE html>
<html>
<head>
<base href="${basePath}"/>
<meta http-equiv="X-UA-COMPATIBLE" content="IE=edge,chrome=1"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>网站信息</title>
<link rel="stylesheet" href="admin/css/bootstrap.css" />
<link rel="stylesheet" href="admin/css/css.css" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	loadData({
		url : "administration/getWebsiteInfo",
		success : function(data){
			if(data.code == 100) {
				$("#adminUserName").html(data.result.adminUserName);
				$("#adminUserLastLoginTime").html(data.result.adminUserLastLoginTime);
				$("#adminUserLastLoginIp").html(data.result.adminUserLastLoginIp);
				$("#os").html(data.result.os);
				$("#serverName").html(data.result.serverName);
				$("#container").html(data.result.container);
				$("#userAgent").html(data.result.userAgent);
			}
		},
		redirectUrl : "admin/login.jsp?msg=" + encodeURI("您还未登陆，请先登陆")
	});
});
</script>
</head>
<body>
<%@include file="/admin/header.jsp"%>
<div id="middle">
<jsp:include page="/admin/left.jsp">
	<jsp:param name="p" value="网站信息"/>
</jsp:include>
	<div class="right">
	<div class="right_cont">
	<div class="breadcrumb">当前位置：
		<a href="javascript:;">网站管理</a><span class="divider">/</span>
		<a href="javascript:;">网站信息</a>
	</div>
	<div class="title_right"><strong>网站信息</strong><span style="color:red;font-size:18px;padding-left:200px;" id="showMsg"></span></div>
	<div style="width:900px; margin:auto">
	<table class="table table-bordered">
		<tr>
			<td width="12%" align="right" nowrap="nowrap" bgcolor="#f1f1f1">管理员：</td>
			<td width="38%" id="adminUserName"></td>
			<td width="12%" align="right" bgcolor="#f1f1f1">最后登陆时间：</td>
			<td id="adminUserLastLoginTime"></td>
		</tr>
		<tr>
			<td align="right" nowrap="nowrap" bgcolor="#f1f1f1">最后登陆IP：</td>
			<td id="adminUserLastLoginIp"></td>
			<td align="right" bgcolor="#f1f1f1">服务器操作系统：</td>
			<td id="os"></td>
		</tr>
		<tr>
			<td align="right" nowrap="nowrap" bgcolor="#f1f1f1">登入域名：</td>
			<td id="serverName"></td>
			<td align="right" bgcolor="#f1f1f1">服务器软件：</td>
			<td id="container"></td>
		</tr>
		<tr>
			<td align="right" nowrap="nowrap" bgcolor="#f1f1f1">使用浏览器：</td>
			<td colspan="3" id="userAgent"></td>
		</tr>
	</table>
	</div> 
	</div>     
	</div>
</div>
</body>
</html>