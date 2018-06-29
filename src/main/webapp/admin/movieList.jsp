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
		<tr align="center">
			<td><strong>id</strong></td>
			<td><strong>标题</strong></td>
			<td><strong>标签</strong></td>
			<td><strong>外部链接</strong></td>
			<td><strong>内部链接</strong></td>
			<td><strong>本地文件路径</strong></td>
			<td><strong>添加时间</strong></td>
		</tr>
	</table>
	<table class="margin-bottom-20 table no-border">
		<tr>
			<td class="text-center">
			<span>共
				<span class="red" id="rowCount"></span>
				条记录,
				<span class="red" id="pageCount"></span>
				页,每页
				<span class="red" id="pageSize"></span>
				条&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</span>
			<span id="pageTdSpan"></span>
			</td>
		</tr>
	</table>
	</div>     
	</div>
</div>
</body>
</html>