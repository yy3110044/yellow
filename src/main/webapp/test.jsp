<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><!DOCTYPE html>
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
	{"path":"movieList", "paramNames":["pageSize", "pageNo", "showCount", "sortField", "sortType"], "desc":"返回影片列表"},
	{"path":"movieDetail", "paramNames":["token", "movieId"], "desc":"返回某个影片的信息"},
	
	//用户接口
	{"path":"userRegistry", "paramNames":["userName", "passWord", "phone", "nickName", "email", "yzm"], "desc":"用户注册"},
	{"path":"userLogin", "paramNames":["loginType", "userName", "passWord"], "desc":"用户登陆"},
	{"path":"userLogout", "paramNames":["token"], "desc":"用户退出"},
	
	//用户中心接口
	{"path":"user/getUserInfo", "paramNames":["token"], "desc":"返回用户信息"},
	
	//后台管理员登陆接口
	{"path":"adminUserLogin", "paramNames":["userName", "passWord"], "desc":"后台管理员登陆"},
	{"path":"adminUserLogout", "paramNames":[], "desc":"后台管理员退出"},
	{"path":"addAdminUser", "paramNames":["userName", "passWord"], "desc":"添加一个管理员"},
	
	//后台管理接口
	{"path":"administration/modifyAdminUserPassWord", "paramNames":["oldPassWord", "newPassWord"], "desc":"修改管理员密码"},
	{"path":"administration/getWebsiteInfo", "paramNames":[], "desc":"返回网站相关信息"},
	
	//后台影片管理
	{"path":"administration/addMovie", "paramNames":["title", "tags", "imgUrl", "externalLink", "internalLink", "filePath", "createTime"], "desc":"添加一个影片"},
	{"path":"administration/getMovie", "paramNames":["id"], "desc":"返回一个影片的信息"},
	{"path":"administration/deleteMovie", "paramNames":["id", "deleteFile"], "desc":"删除一个影片"},
	{"path":"administration/updateMovie", "paramNames":["id", "title", "tags", "imgUrl", "externalLink", "internalLink", "filePath", "createTime"], "desc":"更新一个影片"},
	{"path":"administration/listMovie", "paramNames":["startTime", "endTime", "pageSize", "pageNo", "showCount", "sortField", "sortType"], "desc":"分页查询影片"},
	{"path":"administration/checkFile", "paramNames":["id"], "desc":"检查影片是否存在"}
];

$(document).ready(function(){
	var pathSelect = $("#pathSelect");
	for(var i=0; i<paths.length; i++) {
		pathSelect.append('<option value="' + i + '">' + paths[i].path + ' - ' + paths[i].desc + '</option>');
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