<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><div class="header">
	<div class="logo" style="color:white;font-size:26px;padding-top:25px;padding-left:15px;">后台管理</div>
	<div class="header-right">
		<!-- 
		<i class="icon-question-sign icon-white"></i>
		<a href="#">帮助</a>
		-->
		<i class="icon-off icon-white"></i>
		<a href="admin/modifyAdminUserPassWord.jsp">修改管理员密码</a>
		<i class="icon-user icon-white"></i>
		<a href="javascript:;" onclick="logout()">退出</a>
	</div>
	<script type="text/javascript">
	var logout = function(){
		if(confirm('确定退出？')) {
			loadData({
				url : "adminUserLogout",
				success : function(data){
					if(data.code == 100) {
						window.location.href = "admin/login.jsp?msg=" + encodeURI("退出成功");
					}
				}
			});
		}
	};
	</script>
</div>