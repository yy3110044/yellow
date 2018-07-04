package com.yy.yellow.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.yy.yellow.po.User;
import com.yy.yellow.util.QueryCondition;

@Mapper
public interface UserMapper {
    void add(User obj);
    void delete(int id);
    void update(User obj);
    User find(QueryCondition qc);
    User findById(int id);
    List<User> query(QueryCondition qc);
    int getCount(QueryCondition qc);
}
