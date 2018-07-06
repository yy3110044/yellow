<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%><!DOCTYPE html>
<html>
<head>
<base href="${basePath}"/>
<meta http-equiv="X-UA-COMPATIBLE" content="IE=edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>权限列表</title>
<link rel="stylesheet" href="admin/css/bootstrap.css">
<link rel="stylesheet" href="admin/css/css.css">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="laydate/laydate.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript">
var del = function(perId) {
	if(confirm("确定删除？")) {
		loadData({
			url : "administration/permissionDelete",
			data : {
				"perId" : perId
			},
			success : function(data) {
				if(data.code == 100) {
					query();
				} else {
					$("#showMsg").html(data.msg);
				}
			}
		});
	}
};

var query = function(){
	loadData({
		url : "administration/permissionList",
		success : function(data){
			if(data.code == 100) {
				$("tr.contentTr").remove();
				var str = getContentStr({
					list : data.result,
					fields : [
						{fn : function(obj){
							if(obj.level == -1) {
								return obj.level + "(游客)";
							} else {
								return obj.level;
							}
						}},
						{field : "watchMovieCount"},
						{fn : function(obj, tdId){
							loadData({
								url : "administration/getPerByCache",
								data : {
									"level" : obj.level
								},
								success : function(data) {
									if(data.code == 100) {
										$("#" + tdId).html(data.result);
									}
								}
							});
							return "";
						}},
						{field : "createTime"},
						{fn : function(obj){
							return '<a href="javascript:;" onclick="del(' + obj.id + ')">删除</a>';
						}}
					]
				});
				$("table.table-bordered").append(str);
			} else {
				$("#showMsg").html(data.msg);
			}
		}
	});
};
$(document).ready(function(){
	query();
});
</script>
</head>

<body>
<%@include file="/admin/header.jsp"%>
<div id="middle">
<jsp:include page="/admin/left.jsp">
	<jsp:param name="p" value="权限列表"/>
</jsp:include>
	<div class="right">
	<div class="right_cont">
	<div class="breadcrumb">当前位置：
		<a href="javascript:;">网站管理</a><span class="divider">/</span>
		<a href="javascript:;">权限列表</a>
	</div>
	<div class="title_right"><strong>权限列表</strong><span style="color:red;font-size:18px;padding-left:200px;" id="showMsg"></span></div>
	<table class="table table-bordered table-striped table-hover">
		<tr align="center">
			<td><strong>用户等级</strong></td>
			<td><strong>每日可观影数量</strong></td>
			<td><strong>每日可观影数量(cache)</strong></td>
			<td><strong>createTime</strong></td>
			<td><strong>操作</strong></td>
		</tr>
	</table>
	</div>     
	</div>
</div>
</body>
</html>