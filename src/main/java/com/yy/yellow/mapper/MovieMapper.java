package com.yy.yellow.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yy.yellow.po.Movie;
import com.yy.yellow.util.QueryCondition;

@Mapper
public interface MovieMapper {
    void add(Movie obj);
    void delete(String id);
    void update(Movie obj);
    Movie find(QueryCondition qc);
    Movie findById(String id);
    List<Movie> query(QueryCondition qc);
    int getCount(QueryCondition qc);
    void updateDownloadUrl(@Param("internalLink") String internalLink, @Param("externalLink") String externalLink);
}
