package com.yy.yellow.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.yy.yellow.po.UserLevelPermission;
import com.yy.yellow.util.QueryCondition;

@Mapper
public interface UserLevelPermissionMapper {
    void add(UserLevelPermission obj);
    void delete(int id);
    void update(UserLevelPermission obj);
    UserLevelPermission find(QueryCondition qc);
    UserLevelPermission findById(int id);
    List<UserLevelPermission> query(QueryCondition qc);
    int getCount(QueryCondition qc);
}
