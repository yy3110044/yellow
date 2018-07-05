<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%><!DOCTYPE html>
<html>
<head>
<base href="${basePath}"/>
<meta http-equiv="X-UA-COMPATIBLE" content="IE=edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>标题</title>
<link rel="stylesheet" href="admin/css/bootstrap.css">
<link rel="stylesheet" href="admin/css/css.css">
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/common.js"></script>
</head>
<body>
<%@include file="/admin/header.jsp"%>
<div id="middle">
<jsp:include page="/admin/left.jsp">
	<jsp:param name="p" value=""/>
</jsp:include>
	<div class="right">
	<div class="right_cont">
	<div class="breadcrumb">当前位置：
		<a href="javascript:;">网站管理</a><span class="divider">/</span>
		<a href="javascript:;"></a>
	</div>
	<div class="title_right"><strong></strong><span style="color:red;font-size:18px;padding-left:200px;" id="showMsg"></span></div>
	<div style="width:900px; margin:auto">
	<table class="table table-bordered">
		<tr>
			<td width="12%" align="right" nowrap="nowrap" bgcolor="#f1f1f1"></td>
			<td width="38%"></td>
			<td width="12%" align="right" bgcolor="#f1f1f1"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right" nowrap="nowrap" bgcolor="#f1f1f1"></td>
			<td></td>
			<td align="right" bgcolor="#f1f1f1"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right" nowrap="nowrap" bgcolor="#f1f1f1"></td>
			<td colspan="3"><input type="button" value="修改" class="btn btn-info" style="width:80px;"></td>
		</tr>
	</table>
	<table class="margin-bottom-20 table no-border">
		<tr>
			<td class="text-center"><input type="button" value="修改" class="btn btn-info" style="width:80px;"></td>
		</tr>
 	</table>
	</div> 
	</div>     
	</div>
</div>
</body>
</html>