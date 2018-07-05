var empty = function(str){
	return str == null || "" == str;
};

/**
 * obj.url：链接
 * obj.data：参数
 * obj.success：回功回调函数
 * obj.error：失败回调函数
 * obj.redirectCode：跳转代码，默认为200
 * obj.redirectUrl：跳转url
 */
var loadData = function(obj) {
	$.ajax({
		"type" : "POST",
		"cache" : false,
		"async" : true,
		"contentType" : "application/x-www-form-urlencoded",
		"dataType" : "json",
		"beforeSend" : function(){ //请求之前调用
			$("body").append('<div id="loadingLevel_blockOverlay" style="z-index: 2001; border: none; margin: 0px; padding: 0px; width: 100%; height: 100%; top: 0px; left: 0px; opacity: 0.4; filter: alpha(opacity=40); cursor: default; position: fixed; background-color: rgb(255, 255, 255);"></div>');
		},
		"complete" : function(){ //请求完成后调用，无论成功还是失败
			$("#loadingLevel_blockOverlay").remove();
		},
		"url" : obj.url,
		"data" : obj.data,
		"success" : function(data, textStatus) {
			if(obj.redirectCode == null) {
				obj.redirectCode = 200;
			}
			if(obj.redirectUrl != null) {
				if(obj.redirectCode == data.code) {
					window.location.href = obj.redirectUrl;
					return;
				}
			}
			obj.success(data, textStatus); //请求成功后调用
		},
		"error" : obj.error //请求失败后调用，参数：XMLHttpRequest, textStatus, errorThrown
	});
};

//填充内容
var getContentStr = function(param){
	var str = '';
	for(var i=0; i<param.list.length; i++) {
		var obj = param.list[i];
		str += '<tr align="center" class="contentTr">';
		for(var j=0; j<param.fields.length; j++) {
			var field = param.fields[j];
			var tdId = parseInt(Math.random() * 10000000000000000, 10);
			str += '<td id="' + tdId + '">';
			if(field.fn != null) { //方法不为空，执行方法，并返回
				str += field.fn(obj, tdId);
			} else { //直接返回字段值
				str += obj[field.field] == null ? '' : obj[field.field];
			}
			str += '</td>';
		}
		str += '</tr>';
	}
	return str;
};

//真充分页
var getPageStr = function(page){
	var str = '';
	str += '共&nbsp;<span style="color:red;font-weight:bold;">' + page.rowCount+ '</span>&nbsp;条记录，';
	str += '共&nbsp;<span style="color:red;font-weight:bold;">' + page.pageCount + '</span>&nbsp;页，';
	str += '每页&nbsp;<span style="color:red;font-weight:bold;">' + page.pageSize + '</span>&nbsp;条&nbsp;&nbsp;&nbsp;&nbsp;';
	str += '<span>';
	
	if(page.previous) {
		str += '<a href="javascript:;" onclick="query(' + page.pageSize + ', 1)">首页</a>&nbsp;';
		str += '<a href="javascript:;" onclick="query(' + page.pageSize + ', ' + (page.pageNo - 1) + ')">上一页</a>&nbsp;';
	}
	var prePage = page.previousPages;
	for(var i=0; i<prePage.length; i++) {
		str += '<a href="javascript:;" onclick="query(' + page.pageSize + ', ' + prePage[i] + ')">' + prePage[i] + '</a>&nbsp;';
	}
	
	str += '<span>' + page.pageNo + '</span>&nbsp;';

	var nePage = page.nextPages;
	for(var i=0; i<nePage.length; i++) {
		str += '<a href="javascript:;" onclick="query(' + page.pageSize + ', ' + nePage[i] + ')">' + nePage[i] + '</a>&nbsp;';
	}
	if(page.next) {
		str += '<a href="javascript:;" onclick="query(' + page.pageSize + ', ' + (page.pageNo + 1) + ')">下一页</a>&nbsp;';
		str += '<a href="javascript:;" onclick="query(' + page.pageSize + ', ' + page.pageCount + ')">尾页</a>&nbsp;';
	}

	str += '</span>';
	return str;
};

