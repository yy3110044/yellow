package com.yy.yellow.controller.administration;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.yy.yellow.po.MovieWatchRecord;
import com.yy.yellow.service.MovieWatchRecordService;
import com.yy.yellow.util.Page;
import com.yy.yellow.util.QueryCondition;
import com.yy.yellow.util.ResponseObject;
import com.yy.yellow.util.Util;
import com.yy.yellow.util.QueryCondition.SortType;

/**
 * 观影记录
 * @author yy
 *
 */
@RestController
@RequestMapping(value="/administration", method=RequestMethod.POST)
public class MovieWatchRecordAdminController {
	@Autowired
	private MovieWatchRecordService mwrs;
	
	@RequestMapping("/movieWatchRecordList")
	public ResponseObject movieWatchRecordList(Integer userId,
			                                    String ip,
			                                    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date startTime,
				                                @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date endTime,
												@RequestParam(defaultValue="20") int pageSize,
										        @RequestParam(defaultValue="1") int pageNo,
										        @RequestParam(defaultValue="5") int showCount) {
		QueryCondition qc = new QueryCondition();
		if(userId != null) {
			qc.addCondition("userId", "=", userId);
		}
		if(!Util.empty(ip)) {
			qc.addCondition("ip", "=", ip);
		}
		if(startTime != null) {
			qc.addCondition("createTime", ">=", startTime);
		}
		if(endTime != null) {
			qc.addCondition("createTime", "<=", endTime);
		}
		qc.setPage(new Page(pageSize, pageNo, showCount));
		qc.addSort("id", SortType.DESC);
		List<MovieWatchRecord> list = mwrs.query(qc);
		
		Map<String, Object> result = new HashMap<>();
		result.put("list", list);
		result.put("page", qc.getPage().setRowCount(mwrs.getCount(qc)));
		return new ResponseObject(100, "success", result);
	}
}