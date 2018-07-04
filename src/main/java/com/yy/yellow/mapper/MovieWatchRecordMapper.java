package com.yy.yellow.mapper;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.yy.yellow.po.MovieWatchRecord;
import com.yy.yellow.util.Page;
import com.yy.yellow.util.QueryCondition;

@Mapper
public interface MovieWatchRecordMapper {
    void add(MovieWatchRecord obj);
    void delete(int id);
    void update(MovieWatchRecord obj);
    MovieWatchRecord find(QueryCondition qc);
    MovieWatchRecord findById(int id);
    List<MovieWatchRecord> query(QueryCondition qc);
    int getCount(QueryCondition qc);
    
  //查看登陆用户的是否观看了某个影片
  	MovieWatchRecord getLoginUserWatchRecord(@Param("userId") int userId, @Param("ip") String ip, @Param("movieId") String movieId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
  	//得到登陆用户的每个观看数量
  	int getLoginUserWatchCount(@Param("userId") int userId, @Param("ip") String ip, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
  	//返回登陆用户观盾的每日所有影片
  	List<MovieWatchRecord> getLoginUserWatchMovies(@Param("userId") int userId, @Param("ip") String ip, @Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("page") Page page);
}
