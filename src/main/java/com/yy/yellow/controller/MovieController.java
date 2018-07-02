package com.yy.yellow.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.yy.yellow.po.Movie;
import com.yy.yellow.po.MovieWatchRecord;
import com.yy.yellow.service.MovieService;
import com.yy.yellow.service.MovieWatchRecordService;
import com.yy.yellow.service.UserService;
import com.yy.yellow.util.Page;
import com.yy.yellow.util.QueryCondition;
import com.yy.yellow.util.ResponseObject;

/**
 * 用户movie controller
 * @author yy
 *
 */
@RestController
@RequestMapping(method=RequestMethod.POST)
public class MovieController {
	@Autowired
	private MovieService ms;
	
	@Autowired
	private MovieWatchRecordService mwrs;
	
	@Autowired
	private UserService us;
	
	@Value("{web.config.freeMovieCount}")
	private int freeMovieCount;//每天能免费观看的影片数量
	
	@RequestMapping("/movieList")
	public ResponseObject movieList(@RequestParam(defaultValue="15") int pageSize,
						            @RequestParam(defaultValue="1") int pageNo,
						            @RequestParam(defaultValue="5") int showCount,
						            @RequestParam(defaultValue="createTime") String sortField,
						            @RequestParam(defaultValue="DESC") QueryCondition.SortType sortType) {
		QueryCondition qc = new QueryCondition();
		qc.setPage(new Page(pageSize, pageNo));
		qc.addSort(sortField, sortType);
		List<Movie> list = ms.query(qc);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("list", list);
		result.put("page", qc.getPage().setRowCount(ms.getCount(qc)));
		return new ResponseObject(100, "返回成功", result);
	}
	
	@RequestMapping("/movieDetail")
	public ResponseObject movieDetail(@RequestParam String id, HttpSession session) {
		Integer userId = (Integer)session.getAttribute("userId");
		return new ResponseObject(100, "返回成功");
	}
}