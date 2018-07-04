package com.yy.yellow.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.yy.yellow.po.AdminUser;
import com.yy.yellow.util.QueryCondition;

@Mapper
public interface AdminUserMapper {
    void add(AdminUser obj);
    void delete(int id);
    void update(AdminUser obj);
    AdminUser find(QueryCondition qc);
    AdminUser findById(int id);
    List<AdminUser> query(QueryCondition qc);
    int getCount(QueryCondition qc);
}
