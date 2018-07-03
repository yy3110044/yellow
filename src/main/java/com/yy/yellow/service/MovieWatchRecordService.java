package com.yy.yellow.service;

import java.util.Date;
import java.util.List;
import com.yy.yellow.po.MovieWatchRecord;
import com.yy.yellow.util.Page;
import com.yy.yellow.util.QueryCondition;

public interface MovieWatchRecordService {
	void add(MovieWatchRecord record);
	void delete(int id);
	void update(MovieWatchRecord record);
	MovieWatchRecord find(QueryCondition qc);
	MovieWatchRecord findById(int id);
	List<MovieWatchRecord> query(QueryCondition qc);
	int getCount(QueryCondition qc);
	
	//查看登陆用户的是否观看了某个影片
	MovieWatchRecord getLoginUserWatchRecord(int userId, String ip, String movieId, Date startTime, Date endTime);
	//得到登陆用户的每个观看数量
	int getLoginUserWatchCount(int userId, String ip, Date startTime, Date endTime);
	//返回登陆用户观盾的每日所有影片
	List<MovieWatchRecord> getLoginUserWatchMovies(int userId, String ip, Date startTime, Date endTime, Page page);
}