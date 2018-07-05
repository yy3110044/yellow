<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%><!DOCTYPE html>
<html>
<head>
<base href="${basePath}"/>
<meta http-equiv="X-UA-COMPATIBLE" content="IE=edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>登录日志</title>
<link rel="stylesheet" href="admin/css/bootstrap.css">
<link rel="stylesheet" href="admin/css/css.css">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="laydate/laydate.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript">
var query = function(pageSize, pageNo) {
	var userType = $("#userType").val();
	var userId = $.trim($("#userId").val());
	var userName = $.trim($("#userName").val());
	var startTime = $.trim($("#startTime").val());
	var endTime = $.trim($("#endTime").val());
	loadData({
		url : "administration/loginLogList",
		data : {
			"userType" : userType,
			"userId" : userId,
			"userName" : userName,
			"startTime" : startTime,
			"endTime" : endTime,
			"pageSize" : pageSize,
			"pageNo" : pageNo
		},
		success : function(data) {
			if(data.code == 100) {
				$("tr.contentTr").remove();
				var str = "";
				if("user" == userType) { //用户
					str = getContentStr({
						list : data.result.list,
						fields : [
							{field : "id"},
							{field : "userId"},
							{field : "userName"},
							{field : "loginIp"},
							{field : "loginTime"},
							{field : "loginType"},
							{field : "userAgent"},
							{field : "createTime"}
						]
					});
				} else if("adminUser" == userType) { //管理员
					str = getContentStr({
						list : data.result.list,
						fields : [
							{field : "id"},
							{field : "adminUserId"},
							{field : "adminUserName"},
							{field : "loginIp"},
							{field : "loginTime"},
							{fn : function(){return "web";}},
							{field : "userAgent"},
							{field : "createTime"}
						]
					});
				}
				$("table.table-bordered").append(str);
				$("#pageTd").html(getPageStr(data.result.page));
			} else {
				$("#showMsg").html(data.msg);
			}
		}
	});
};
$(document).ready(function(){
	query(20, 1);
});
</script>
</head>

<body>
<%@include file="/admin/header.jsp"%>
<div id="middle">
<jsp:include page="/admin/left.jsp">
	<jsp:param name="p" value="登录日志"/>
</jsp:include>
	<div class="right">
	<div class="right_cont">
	<div class="breadcrumb">当前位置：
		<a href="javascript:;">日志管理</a><span class="divider">/</span>
		<a href="javascript:;">登录日志</a>
	</div>
	<div class="title_right"><strong>登录日志</strong><span style="color:red;font-size:18px;padding-left:200px;" id="showMsg"></span></div>
	<table class="table table-bordered table-striped table-hover">
		<tr>
			<td colspan="99" style="padding:3px;line-height:30px;">
				<select id="userType" style="width:70px;">
					<option value="user">用户</option>
					<option value="adminUser">管理员</option>
				</select>
				&nbsp;&nbsp;<input type="text" id="userId" placeholder="用户id">
				&nbsp;&nbsp;<input type="text" id="userName" placeholder="用户名">
				&nbsp;&nbsp;<input type="text" id="startTime" placeholder="开始时间" class="laydate-icon" onclick="laydate({istime:true,format:'YYYY-MM-DD hh:mm:ss'});" style="width:140px;cursor:pointer;" readonly="readonly">
				&nbsp;&nbsp;<input type="text" id="endTime" placeholder="结束时间" class="laydate-icon" onclick="laydate({istime:true,format:'YYYY-MM-DD hh:mm:ss'});" style="width:140px;cursor:pointer;" readonly="readonly">
				&nbsp;&nbsp;<input type="button" value="查询" onclick="query(20, 1)">
			</td>
		</tr>
		<tr align="center">
			<td><strong>ID</strong></td>
			<td><strong>用户ID</strong></td>
			<td><strong>用户名</strong></td>
			<td><strong>登录IP</strong></td>
			<td><strong>登录时间</strong></td>
			<td><strong>登录类型</strong></td>
			<td><strong>userAgent</strong></td>
			<td><strong>createTime</strong></td>
		</tr>
	</table>
	<table class="margin-bottom-20 table no-border">
		<tr>
			<td class="text-center" id="pageTd"></td>
		</tr>
	</table>
	</div>     
	</div>
</div>
</body>
</html>