<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><!DOCTYPE html>
<%
System.out.println(application.getRealPath("/movie"));
%>
<html>
<head>
<base href="${basePath}">
<meta http-equiv="content-type" content="text/html;charset=utf-8">
<script src="js/jquery.js"></script>
<script type="text/javascript">
var empty = function(str) {
	return str == null || str == "";
}
var addParam = function(){
	$("#paramUl").append('<li class="paramLi"><input type="text" class="paramName" placeholder="参数名" style="width:160px;">&nbsp;=&nbsp;<input type="text" class="paramValue" placeholder="参数值" style="width:160px;">&nbsp;<a href="javascript:;" style="color:red;" onclick="deleteParam(this)">删除</a></li>');
}

var deleteParam = function(e){
	$(e).parent().remove();
}

var test = function(){
	var path = $.trim($("#path").val());
	if(empty(path)) {
		alert("请输入接口地址");
		return;
	}
	
	var params = new Array();
	
	var result = true;
	
	$("li.paramLi").each(function(){
		var ts = $(this);
		var paramName = $.trim(ts.children(".paramName").val());
		var paramValue = $.trim(ts.children(".paramValue").val());
		if(empty(paramName) || empty(paramValue)) {
			result = false;
		} else {
			params.push(paramName + "|" + paramValue);
		}
	});
	
	if(result) {
		requestUrl(path, params);
	} else {
		alert("参数名和值不能为空");
	}
}

var requestUrl = function(path, params) {
	var dt = {};
	for(var i=0; i<params.length; i++) {
		var ss = params[i].split("|");
		dt[ss[0]] = ss[1];
	}
	
	$.ajax({
		type : "POST",
		cache : false,
		url : "${basePath}" + path,
		data : dt,
		dataType : "text",
		success : function(data, textStatus){
			$("#resultTd").html(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			$("#resultTd").html(textStatus);
		}
	});
}

var pathSelectChange = function(){
	var val = $("#pathSelect").val();
	pathChange(parseInt(val, 10));
}

var pathChange = function(index){
	var pt = paths[index];
	$("#path").val(pt.path);
	var paramNames = pt.paramNames;
	
	var paramUl = $("#paramUl");
	paramUl.children("li.paramLi").remove();
	for(var i=0; i<paramNames.length; i++) {
		paramUl.append('<li class="paramLi"><input type="text" class="paramName" placeholder="参数名" value="' + paramNames[i] + '" style="width:160px;">&nbsp;=&nbsp;<input type="text" class="paramValue" placeholder="参数值" style="width:160px;">&nbsp;<a href="javascript:;" style="color:red;" onclick="deleteParam(this)">删除</a></li>');
	}
}

//服务端接口定义
var paths = [
	//用户接口
	{"path":"userRegistry", "paramNames":["userName", "passWord1", "passWord2", "nickName", "email", "yzm"]},
	{"path":"userLogin", "paramNames":["loginType", "userName", "passWord"]},
	{"path":"userLogout", "paramNames":["token"]},
	
	//用户中心接口
	{"path":"user/getUserInfo", "paramNames":["token"]},
	
	//后台管理员登陆接口
	{"path":"adminUserLogin", "paramNames":["userName", "passWord"]},
	{"path":"adminUserLogout", "paramNames":[]},
	{"path":"addAdminUser", "paramNames":["userName", "passWord"]},
	
	//后台管理接口
	{"path":"administration/modifyAdminUserPassWord", "paramNames":["oldPassWord", "newPassWord"]},
	{"path":"administration/getWebsiteInfo", "paramNames":[]},
	
	//后台影片管理
	{"path":"administration/addMovie", "paramNames":["title", "tags", "externalLink", "internalLink", "filePath", "createTime"]},
	{"path":"administration/getMovie", "paramNames":["id"]},
	{"path":"administration/deleteMovie", "paramNames":["id", "deleteFile"]},
	{"path":"administration/updateMovie", "paramNames":["id", "title", "tags", "externalLink", "internalLink", "filePath", "createTime"]},
	{"path":"administration/listMovie", "paramNames":["startTime", "endTime", "pageSize", "pageNo", "showCount", "sortField", "sortType"]},
	{"path":"administration/checkFile", "paramNames":["id"]}
];

$(document).ready(function(){
	var pathSelect = $("#pathSelect");
	for(var i=0; i<paths.length; i++) {
		pathSelect.append('<option value="' + i + '">' + paths[i].path + '</option>');
	}
	if(paths.length > 0) {
		pathChange(0);
	}
});
</script>
</head>
<body>
	<table>
		<thead>
			<tr>
				<td colspan="2" style="text-align:center;">接口测试</td>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>URL:</td>
				<td>${basePath}&nbsp;<input type="text" id="path" style="width:300px;">&nbsp;<select id="pathSelect" onchange="pathSelectChange()"></select></td>
			</tr>
			<tr>
				<td>参数:</td>
				<td>
					<ul style="list-style:none;padding:0;margin:0;" id="paramUl">
						<li><a href="javascript:;" onclick="addParam()">添加</a></li>
					</ul>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="text-align:center;"><input type="button" value="测试" onclick="test()"></td>
			</tr>
			<tr>
				<td>结果:</td>
				<td id="resultTd"></td>
			</tr>
		</tbody>
	</table>
</body>
</html>