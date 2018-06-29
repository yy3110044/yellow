<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><!DOCTYPE html>
<html>
<head>
<base href="${basePath}"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>管理员登陆</title>
<link rel="stylesheet" href="admin/css/bootstrap.css" />
<style type="text/css">
body{ background:#0066A8;}
.tit{ margin:auto; margin-top:170px; text-align:center; width:350px; padding-bottom:20px;}
.login-wrap{ width:220px; padding:30px 50px 0 330px; height:220px; background:#fff url(admin/img/20150212154319.jpg) no-repeat 30px 40px; margin:auto; overflow: hidden;}
.login_input{ display:block;width:210px;}
.login_user{ background: url(admin/img/input_icon_1.png) no-repeat 200px center; font-family: "Lucida Sans Unicode", "Lucida Grande", sans-serif}
.login_password{ background: url(admin/img/input_icon_2.png) no-repeat 200px center; font-family:"Courier New", Courier, monospace}
.btn-login{ background:#40454B; box-shadow:none; text-shadow:none; color:#fff; border:none;height:35px; line-height:26px; font-size:14px; font-family:"microsoft yahei";}
.btn-login:hover{ background:#333; color:#fff;}
.copyright{ margin:auto; margin-top:10px; text-align:center; width:370px; color:#CCC}
@media (max-height: 700px) {.tit{ margin:auto; margin-top:100px; }}
@media (max-height: 500px) {.tit{ margin:auto; margin-top:50px; }}
</style>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript">
var login = function(){
	var userName = $.trim($("#userName").val());
	var passWord = $("#passWord").val();
	loadData({
		url : "adminUserLogin",
		data : {
			"userName" : userName,
			"passWord" : passWord,
		},
		success : function(data){
			$("#msg").html(data.msg);
			if(data.code == 100) {
				window.location.href = "${basePath}admin/webSiteInfo.jsp";
			}
		}
	});
};
</script>
</head>

<body>
<div class="tit" style="color:white;font-size:26px;">后台管理登陆</div>
<div class="login-wrap" style="height:270px;">
	<table style="width:100%;">
		<tr>
			<td height="25" valign="bottom">用户名：</td>
		</tr>
		<tr>
			<td><input type="text" id="userName" class="login_input login_user"/></td>
		</tr>
		<tr>
	    	<td height="35" valign="bottom">密  码：</td>
		</tr>
		<tr>
	    	<td><input type="password" id="passWord" class="login_input login_password"/></td>
		</tr>
		<!-- 
		<tr>
			<td height="25" valign="bottom">验证码：</td>
		</tr>
		 -->
		<tr>
			<td height="60" valign="bottom"><input type="button" onclick="login()" value="登陆" class="btn btn-block btn-login"/></td>
		</tr>
		<tr><td style="color:red;" id="msg"><%=com.yy.yellow.util.Util.urlDecode(request.getParameter("msg"))%></td></tr>
	</table>
</div>
<div class="copyright">建议使用IE8以上版本或谷歌浏览器</div>
</body>
</html>