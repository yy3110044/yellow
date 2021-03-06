package com.yy.yellow.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.yy.yellow.po.UserLoginLog;
import com.yy.yellow.util.QueryCondition;

@Mapper
public interface UserLoginLogMapper {
    void add(UserLoginLog obj);
    void delete(int id);
    void update(UserLoginLog obj);
    UserLoginLog find(QueryCondition qc);
    UserLoginLog findById(int id);
    List<UserLoginLog> query(QueryCondition qc);
    int getCount(QueryCondition qc);
}
