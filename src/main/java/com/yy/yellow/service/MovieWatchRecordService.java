package com.yy.yellow.service;

import java.util.List;
import com.yy.yellow.po.MovieWatchRecord;
import com.yy.yellow.util.QueryCondition;

public interface MovieWatchRecordService {
	void add(MovieWatchRecord record);
	void delete(int id);
	void update(MovieWatchRecord record);
	MovieWatchRecord find(QueryCondition qc);
	MovieWatchRecord findById(int id);
	List<MovieWatchRecord> query(QueryCondition qc);
	int getCount(QueryCondition qc);
}