package com.yy.yellow.mapper;

import java.util.List;
import com.yy.yellow.po.Movie;
import com.yy.yellow.util.QueryCondition;

public interface MovieMapper {
	void add(Movie movie);
	void delete(String id);
	void update(Movie movie);
	Movie find(QueryCondition qc);
	Movie findById(String id);
	List<Movie> query(QueryCondition qc);
	int getCount(QueryCondition qc);
}