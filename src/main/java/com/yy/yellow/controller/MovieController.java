package com.yy.yellow.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.yy.yellow.po.Movie;
import com.yy.yellow.po.MovieWatchRecord;
import com.yy.yellow.po.User;
import com.yy.yellow.service.MovieService;
import com.yy.yellow.service.MovieWatchRecordService;
import com.yy.yellow.service.UserService;
import com.yy.yellow.util.Cache;
import com.yy.yellow.util.CacheKeyPre;
import com.yy.yellow.util.Page;
import com.yy.yellow.util.QueryCondition;
import com.yy.yellow.util.ResponseObject;
import com.yy.yellow.util.Util;

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
	
	@Autowired
	private Cache cache;
	
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
	public ResponseObject movieDetail(@RequestParam String movieId, HttpServletRequest req) {
		Integer userId = (Integer)req.getSession().getAttribute("userId");
		long startTime = Util.getEveryDayStartTime(System.currentTimeMillis());
		long endTime = startTime + Util._24HoursMillis - 1;
		if(userId != null) { //已登陆
			return this.isLogin(userId, req.getRemoteAddr(), movieId, new Date(startTime), new Date(endTime));
		} else { //未登陆
			return this.unLogin(movieId, req.getRemoteAddr(), new Date(startTime), new Date(endTime));
		}
	}
	private ResponseObject isLogin(int userId, String ip, String movieId, Date startTime, Date endTime) { //已登陆
		//先查看今天有没有观看过此影片，观看过，则直接返回
		MovieWatchRecord mwr = mwrs.getLoginUserWatchRecord(userId, ip, movieId, startTime, endTime);
		if(mwr != null) { //观看过，直接返回此影片
			mwr.setLastWatchTime(new Date());
			mwrs.update(mwr); //更新最后观影时间
			return new ResponseObject(100, "返回成功", ms.findById(movieId));
		} else { //未观看过
			User user = us.findById(userId);
			//用户对应级别每日可观看的影片数量
			int watchMovieCount = cache.getInt(CacheKeyPre.user_level_permission, String.valueOf(user.getLevel()));
			//今天已观看数据
			int count = mwrs.getLoginUserWatchCount(userId, ip, startTime, endTime);
			if(count < watchMovieCount) {//还未达到免费观看次数，返回影片，并添加观看记录
				Movie movie = ms.findById(movieId);
				if(movie != null) {
					mwr = new MovieWatchRecord();
					mwr.setIp(ip);
					mwr.setUserId(userId);
					mwr.setMovieId(movieId);
					mwr.setLastWatchTime(new Date());
					mwrs.add(mwr);
				}
				return new ResponseObject(100, "返回成功", movie);
			} else { //已达到观看上限次数
				return new ResponseObject(101, "已达到今日观看次数");
			}
		}
	}
	private ResponseObject unLogin(String movieId, String ip, Date startTime, Date endTime) { //未登陆
		//先查看今天有没有观看过此影片，观看过，则直接返回
		QueryCondition qc = new QueryCondition();
		qc.addCondition("ip", "=", ip);
		qc.addCondition("movieId", "=", movieId);
		qc.addCondition("lastWatchTime", ">=", startTime);
		qc.addCondition("lastWatchTime", "<=", endTime);
		MovieWatchRecord mwr = mwrs.find(qc);
		if(mwr != null) { //观看过，直接返回此影片
			mwr.setLastWatchTime(new Date());
			mwrs.update(mwr); //更新最后观影时间
			return new ResponseObject(100, "返回成功", ms.findById(movieId));
		} else { //未观看过
			qc.removeCondition("movieId", "=");
			int count = mwrs.getCount(qc);
			if(count < cache.getInt(CacheKeyPre.user_level_permission, "-1")) { //还未达到免费观看次数，返回影片，并添加观看记录
				Movie movie = ms.findById(movieId);
				if(movie != null) {
					mwr = new MovieWatchRecord();
					mwr.setIp(ip);
					mwr.setMovieId(movieId);
					mwr.setLastWatchTime(new Date());
					mwrs.add(mwr);
				}
				return new ResponseObject(100, "返回成功", movie);
			} else { //已达到免费观看次数
				return new ResponseObject(101, "已达到今日的免费观看次数");
			}
		}
	}
}