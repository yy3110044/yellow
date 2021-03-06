<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%><!DOCTYPE html>
<html>
<head>
<base href="${basePath}"/>
<meta http-equiv="X-UA-COMPATIBLE" content="IE=edge,chrome=1"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加影片</title>
<link rel="stylesheet" href="admin/css/bootstrap.css" />
<link rel="stylesheet" href="admin/css/css.css" />
<script src="js/jquery.js"></script>
<script src="js/jquery.ui.widget.js"></script>
<script src="js/jquery.fileupload.js"></script>
<script src="js/jquery.iframe-transport.js"></script>
<script src="js/common.js"></script>

<script type="text/javascript">
var addMovie = function(){
	var title = $.trim($("#title").val());
	var tags = $.trim($("#tags").val());
	var imgUrl = $.trim($("#imgUrl").val());
	var externalLink = $.trim($("#externalLink").val());
	var internalLink = $.trim($("#internalLink").val());
	var filePath = $.trim($("#filePath").val());
	loadData({
		url : "administration/addMovie",
		data : {
			"title" : title,
			"tags" : tags,
			"imgUrl" : imgUrl,
			"externalLink" : externalLink,
			"internalLink" : internalLink
		},
		success : function(data){
			$("#showMsg").html(data.msg);
			if(data.code == 100) {
				$("#title").val("");
				$("#tags").val("");
				$("#externalLink").val("");
				$("#internalLink").val("");
			}
		},
		redirectUrl : "admin/login.jsp?msg=" + encodeURI("请先登陆")
	});
}

$(document).ready(function(){
	addUploadEvent({
		"inputId" : "uploadFileInput",
		"url" : "<%=com.yy.yellow.util.Util.getProperty("web.config.staticSource.uploadUrl", application)%>",
		"fileTypes" : ["image/gif", "image/jpeg", "image/jpg", "image/png"],
		"progressall" : function(e, data) {
			//data.loaded 已上传字节
	        //data.total 文件总字节
	        var rate = parseInt(data.loaded / data.total * 100, 10);
	        $("#progress").html(rate + "%");
		},
		"done" : function(e, data) {
			if(data.result.code == 100) { //上传成功
	            $("#imgUrl").val(data.result.result.serverUrl);
	            $("#uploadImg").attr("src", data.result.result.serverUrl);
	            $("#progress").html("上传成功");
	        } else { //上传失败
	            $("#showMsg").html(data.result.msg);
	        }
		}
	});
});
</script>
</head>
<body>
<%@include file="/admin/header.jsp"%>
<div id="middle">
<jsp:include page="/admin/left.jsp">
	<jsp:param name="p" value="添加影片"/>
</jsp:include>
	<div class="right">
	<div class="right_cont">
	<div class="breadcrumb">当前位置：
		<a href="javascript:;">影片管理</a><span class="divider">/</span>
		<a href="javascript:;">添加影片</a>
	</div>
	<div class="title_right"><strong>添加影片</strong><span style="color:red;font-size:18px;padding-left:200px;" id="showMsg"></span></div>
	<div style="width:900px; margin:auto">
	<table class="table table-bordered">
		<tr>
			<td width="12%" align="right" nowrap="nowrap" bgcolor="#f1f1f1">标题：</td>
			<td><input type="text" id="title" style="width:350px;"></td>
		</tr>
		<tr>
			<td width="12%" align="right" nowrap="nowrap" bgcolor="#f1f1f1">缩略图：</td>
			<td>
				<div><img id="uploadImg" alt="上传图片" src="images/default.jpg" style="width:400px;height:300px;"></div>
				<div>
					<input type="hidden" id="imgUrl" value="images/default.jpg">
					<input id="uploadFileInput" type="file" name="file">
					<span id="progress" style="color:green;"></span>
				</div>
			</td>
		</tr>
		<tr>
			<td align="right" nowrap="nowrap" bgcolor="#f1f1f1">标签：</td>
			<td><input type="text" id="tags" style="width:350px;"></td>
		</tr>
		<tr>
			<td align="right" nowrap="nowrap" bgcolor="#f1f1f1">外部链接：</td>
			<td><input type="text" id="externalLink" style="width:350px;"></td>
		</tr>
		<tr>
			<td align="right" nowrap="nowrap" bgcolor="#f1f1f1">内部链接：</td>
			<td><input type="text" id="internalLink" style="width:350px;"></td>
		</tr>
		<tr>
			<td align="right" nowrap="nowrap" bgcolor="#f1f1f1"></td>
			<td colspan="3"><input type="button" value="添加" class="btn btn-info" style="width:80px;" onclick="addMovie()"></td>
		</tr>
	</table>
	</div> 
	</div>     
	</div>
</div>
</body>
</html>