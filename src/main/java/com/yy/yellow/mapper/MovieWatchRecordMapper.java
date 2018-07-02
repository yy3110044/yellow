package com.yy.yellow.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.yy.yellow.po.MovieWatchRecord;
import com.yy.yellow.util.QueryCondition;

@Mapper
public interface MovieWatchRecordMapper {
	void add(MovieWatchRecord record);
	void delete(int id);
	void update(MovieWatchRecord record);
	MovieWatchRecord find(QueryCondition qc);
	MovieWatchRecord findById(int id);
	List<MovieWatchRecord> query(QueryCondition qc);
	int getCount(QueryCondition qc);
}