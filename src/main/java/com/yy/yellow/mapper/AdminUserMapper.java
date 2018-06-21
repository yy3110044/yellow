package com.yy.yellow.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.yy.yellow.po.AdminUser;

@Mapper
public interface AdminUserMapper {
	void add(AdminUser au);
}