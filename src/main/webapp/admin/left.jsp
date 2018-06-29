<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="left">
	<div class="sdmenu" id="my_menu">
		<div>
			<span>网站管理</span>
			<a href="admin/webSiteInfo.jsp">网站信息</a>
			<a href="administrator/admin?action=addActivityPage">添加门票</a>
			<a href="administrator/admin?action=activityListPage">门票列表</a>
			<a href="administrator/admin?action=blueberryQueryPage">蓝莓销售查询</a>
		</div>
		<!--
		<div>
			<span>用户管理</span>
			<a href="">管理员列表</a>
		</div>
		-->
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