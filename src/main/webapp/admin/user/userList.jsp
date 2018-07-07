<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%><!DOCTYPE html>
<html>
<head>
<base href="${basePath}"/>
<meta http-equiv="X-UA-COMPATIBLE" content="IE=edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>用户列表</title>
<link rel="stylesheet" href="admin/css/bootstrap.css">
<link rel="stylesheet" href="admin/css/css.css">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="laydate/laydate.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript">
var del = function(userId, e) {
	if(confirm("确定删除？")) {
		loadData({
			url : "administration/userDelete",
			data : {
				"userId" : userId
			},
			success : function(data) {
				$("#showMsg").html(data.msg);
				if(data.code == 100) {
					$(e).parent().parent().remove();
				}
			}
		});
	}
}
var query = function(pageSize, pageNo) {
	var userId = $.trim($("#userId").val());
	var userName = $.trim($("#userName").val());
	var sortField = $.trim($("#sortField").val());
	var sortType = $.trim($("#sortType").val());
	loadData({
		url : "administration/userList",
		data : {
			"userId" : userId,
			"userName" : userName,
			"sortField" : sortField,
			"sortType" : sortType,
			"pageSize" : pageSize,
			"pageNo" : pageNo
		},
		success : function(data) {
			if(data.code == 100) {
				$("tr.contentTr").remove();
				var str = getContentStr({
					list : data.result.list,
					fields : [
						{field : "id"},
						{field : "userName"},
						{field : "nickName"},
						{field : "phone"},
						{field : "email"},
						{field : "level"},
						{field : "lastLoginIp"},
						{field : "lastLoginTime"},
						{field : "lastLoginType"},
						{field : "createTime"},
						{fn : function(obj){return '<a href="javascript:;" onclick="del(' + obj.id + ', this)">删除</a>&nbsp;<a href="admin/user/userUpdate.jsp?userId=' + obj.id + '" target="_blank">修改</a>';}}
					]
				});
				$("table.table-bordered").append(str);
				$("#pageTd").html(getPageStr(data.result.page));
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
	<jsp:param name="p" value="用户列表"/>
</jsp:include>
	<div class="right">
	<div class="right_cont">
	<div class="breadcrumb">当前位置：
		<a href="javascript:;">用户管理</a><span class="divider">/</span>
		<a href="javascript:;">用户列表</a>
	</div>
	<div class="title_right"><strong>用户列表</strong><span style="color:red;font-size:18px;padding-left:200px;" id="showMsg"></span></div>
	<table class="table table-bordered table-striped table-hover">
		<tr>
			<td colspan="99" style="padding:3px;line-height:30px;">
				<input type="text" id="id" placeholder="用户ID">
				&nbsp;&nbsp;<input type="text" id="userName" placeholder="用户名">
				&nbsp;&nbsp;排序:<select id="sortField" style="width:100px;"><option value="id">用户ID</option>
				<option value="userName">用户名</option>
				<option value="level">用户等级</option>
				<option value="lastLoginTime">最后登陆时间</option></select>
				&nbsp;&nbsp;<select id="sortType" style="width:70px;"><option value="ASC">正序</option><option value="DESC" selected="selected">倒序</option></select>
				&nbsp;&nbsp;<input type="button" value="查询" onclick="query(20, 1)">
			</td>
		</tr>
		<tr align="center">
			<td><strong>ID</strong></td>
			<td><strong>用户名</strong></td>
			<td><strong>昵称</strong></td>
			<td><strong>手机</strong></td>
			<td><strong>邮箱</strong></td>
			<td><strong>等级</strong></td>
			<td><strong>最后登陆IP</strong></td>
			<td><strong>最后登陆时间</strong></td>
			<td><strong>最后登陆类型</strong></td>
			<td><strong>创建时间</strong></td>
			<td><strong>操作</strong></td>
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