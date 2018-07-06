<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="left">
	<div class="sdmenu" id="my_menu">
		<div>
			<span>网站管理</span>
			<a href="admin/webSite/webSiteInfo.jsp">网站信息</a>
			<a href="admin/webSite/permissionList.jsp">权限列表</a>
			<a href="admin/webSite/permissionAdd.jsp">添加权限</a>
		</div>
		<div>
			<span>影片管理</span>
			<a href="admin/movie/movieList.jsp">影片列表</a>
			<a href="admin/movie/movieAdd.jsp">添加影片</a>
		</div>
		<div>
			<span>用户管理</span>
			<a href="admin/user/userList.jsp">用户列表</a>
			<a href="admin/user/userAdd.jsp">添加用户</a>
		</div>
		<div>
			<span>日志管理</span>
			<a href="admin/log/visitLogList.jsp">访问日志</a>
			<a href="admin/log/loginLogList.jsp">登录日志</a>
			<a href="admin/log/movieWatchRecordList.jsp">观影记录</a>
		</div>
	</div>
</div>
<div class="Switch"></div>
<script type="text/javascript">
$(document).ready(function(){
    $(".Switch").click(function(){
		$(".left").toggle();
	});
    
    $("#my_menu div span").click(function(){
    	$(this).parent().toggleClass("collapsed");
    });
    
    var p = "${param.p}";
    $("#my_menu div a").each(function(){
    	if(p == $(this).html()) {
    		$(this).addClass("current");
    	}
    });
});
</script>