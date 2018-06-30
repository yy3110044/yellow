<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%><!DOCTYPE html>
<html>
<head>
<base href="${basePath}"/>
<meta http-equiv="X-UA-COMPATIBLE" content="IE=edge,chrome=1"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>影片列表</title>
<link rel="stylesheet" href="admin/css/bootstrap.css" />
<link rel="stylesheet" href="admin/css/css.css" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="laydate/laydate.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript">
var del = function(id, e){
	var deleteFile = confirm("是否同时删除服务器文件？");
	if(confirm("确定删除吗？删除后无法恢复")) {
		loadData({
			url : "administration/deleteMovie",
			data : {
				"id" : id,
				"deleteFile" : deleteFile
			},
			success : function(data){
				if(data.code == 100) {
					$("#showMsg").html("删除成功");
					$(e).parent().parent().remove();
				}
			}
		});
	}
};
var query = function(pageSize, pageNo){
	var startTime = $("#startTime").val();
	var endTime = $("#endTime").val();
	loadData({
		url : "administration/listMovie",
		data : {
			"startTime" : startTime,
			"endTime" : endTime,
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
						{field : "title"},
						{field : "tags"},
						{field : "externalLink"},
						{field : "internalLink"},
						{field : "filePath"},
						{field : "createTime"},
						{fn : function(obj){
							var str = '<a href="javascript:;" onclick="del(\'' + obj.id + '\', this)">删除</a>';
							return str;
						}}
					]
				});
				$("table.table-bordered").append(str);
				
				$("#pageTd").html(getPageStr(data.result.page));
			} else {
				$("#showMsg").html(data.msg);
			}
		},
		redirectUrl : "admin/login.jsp?msg=" + encodeURI("请先登录")
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
	<jsp:param name="p" value="影片列表"/>
</jsp:include>
	<div class="right">
	<div class="right_cont">
	<div class="breadcrumb">当前位置：
		<a href="javascript:;">网站管理</a><span class="divider">/</span>
		<a href="javascript:;">影片列表</a>
	</div>
	<div class="title_right"><strong>影片列表</strong><span style="color:red;font-size:18px;padding-left:200px;" id="showMsg"></span></div>
	<table class="table table-bordered table-striped table-hover">
		<tr>
			<td colspan="99" style="padding:3px;line-height:30px;">
				<input type="text" id="startTime" class="laydate-icon" onclick="laydate({istime:true,format:'YYYY-MM-DD hh:mm:ss'});" style="width:140px;cursor:pointer;" readonly="readonly" placeholder="开始时间">
				-
				<input type="text" id="endTime" class="laydate-icon" onclick="laydate({istime:true,format:'YYYY-MM-DD hh:mm:ss'});" style="width:140px;cursor:pointer;" readonly="readonly" placeholder="结束时间">
				&nbsp;&nbsp;<input type="button" value="查询" onclick="query(20, 1)">
			</td>
		</tr>
		<tr align="center">
			<td><strong>ID</strong></td>
			<td><strong>标题</strong></td>
			<td><strong>标签</strong></td>
			<td><strong>外部链接</strong></td>
			<td><strong>内部链接</strong></td>
			<td><strong>本地文件路径</strong></td>
			<td><strong>添加时间</strong></td>
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