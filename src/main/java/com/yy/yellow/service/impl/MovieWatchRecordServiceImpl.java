package com.yy.yellow.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.yy.yellow.mapper.MovieWatchRecordMapper;
import com.yy.yellow.po.MovieWatchRecord;
import com.yy.yellow.service.MovieWatchRecordService;
import com.yy.yellow.util.Page;
import com.yy.yellow.util.QueryCondition;

@Repository("movieWatchRecordService")
@Transactional
public class MovieWatchRecordServiceImpl implements MovieWatchRecordService {
	@Autowired
	private MovieWatchRecordMapper mwrm;

	@Override
	public void add(MovieWatchRecord record) {
		mwrm.add(record);
	}

	@Override
	public void delete(int id) {
		mwrm.delete(id);
	}

	@Override
	public void update(MovieWatchRecord record) {
		mwrm.update(record);
	}

	@Override
	public MovieWatchRecord find(QueryCondition qc) {
		return mwrm.find(qc);
	}

	@Override
	public MovieWatchRecord findById(int id) {
		return mwrm.findById(id);
	}

	@Override
	public List<MovieWatchRecord> query(QueryCondition qc) {
		return mwrm.query(qc);
	}

	@Override
	public int getCount(QueryCondition qc) {
		return mwrm.getCount(qc);
	}

	@Override
	public MovieWatchRecord getLoginUserWatchRecord(int userId, String ip, String movieId, Date startTime, Date endTime) {
		return mwrm.getLoginUserWatchRecord(userId, ip, movieId, startTime, endTime);
	}

	@Override
	public int getLoginUserWatchCount(int userId, String ip, Date startTime, Date endTime) {
		return mwrm.getLoginUserWatchCount(userId, ip, startTime, endTime);
	}

	@Override
	public List<MovieWatchRecord> getLoginUserWatchMovies(int userId, String ip, Date startTime, Date endTime, Page page) {
		return mwrm.getLoginUserWatchMovies(userId, ip, startTime, endTime, page);
	}
}