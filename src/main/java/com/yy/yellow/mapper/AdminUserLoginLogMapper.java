package com.yy.yellow.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.yy.yellow.po.AdminUserLoginLog;
import com.yy.yellow.util.QueryCondition;

@Mapper
public interface AdminUserLoginLogMapper {
    void add(AdminUserLoginLog obj);
    void delete(int id);
    void update(AdminUserLoginLog obj);
    AdminUserLoginLog find(QueryCondition qc);
    AdminUserLoginLog findById(int id);
    List<AdminUserLoginLog> query(QueryCondition qc);
    int getCount(QueryCondition qc);
}
