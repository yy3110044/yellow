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
    private MovieMapper mapper;

    @Override
    public void add(Movie obj) {
        mapper.add(obj);
    }

    @Override
    public void delete(String id) {
        mapper.delete(id);
    }

    @Override
    public void update(Movie obj) {
        mapper.update(obj);
    }

    @Override
    public Movie find(QueryCondition qc) {
        return mapper.find(qc);
    }

    @Override
    public Movie findById(String id) {
        return mapper.findById(id);
    }

    @Override
    public List<Movie> query(QueryCondition qc) {
        return mapper.query(qc);
    }

    @Override
    public int getCount(QueryCondition qc) {
        return mapper.getCount(qc);
    }
}
