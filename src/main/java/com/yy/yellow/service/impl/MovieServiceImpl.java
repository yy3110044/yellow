package com.yy.yellow.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.yy.yellow.mapper.MovieMapper;
import com.yy.yellow.po.Movie;
import com.yy.yellow.service.MovieService;
import com.yy.yellow.util.QueryCondition;

@Repository("movieService")
@Transactional
public class MovieServiceImpl implements MovieService {
	@Autowired
	private MovieMapper mm;

	@Override
	public void add(Movie movie) {
		mm.add(movie);
	}

	@Override
	public void delete(String id) {
		mm.delete(id);
	}

	@Override
	public void update(Movie movie) {
		mm.update(movie);
	}

	@Override
	public Movie find(QueryCondition qc) {
		return mm.find(qc);
	}

	@Override
	public Movie findById(String id) {
		return mm.findById(id);
	}

	@Override
	public List<Movie> query(QueryCondition qc) {
		return mm.query(qc);
	}

	@Override
	public int getCount(QueryCondition qc) {
		return mm.getCount(qc);
	}
}