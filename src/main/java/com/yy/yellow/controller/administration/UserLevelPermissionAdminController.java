package com.yy.yellow.controller.administration;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.yy.yellow.po.UserLevelPermission;
import com.yy.yellow.service.UserLevelPermissionService;
import com.yy.yellow.util.Cache;
import com.yy.yellow.util.CacheKeyPre;
import com.yy.yellow.util.QueryCondition;
import com.yy.yellow.util.ResponseObject;
import com.yy.yellow.util.QueryCondition.SortType;

/**
 * 用户等级权限管理
 * @author yy
 *
 */
@RestController
@RequestMapping(value="/administration", method=RequestMethod.POST)
public class UserLevelPermissionAdminController {
	@Autowired
	private UserLevelPermissionService ulps;
	
	@Autowired
	private Cache cache;
	
	@RequestMapping("/permissionList")
	public ResponseObject permissionList() {
		List<UserLevelPermission> list = ulps.query(new QueryCondition().addSort("level", SortType.ASC));
		return new ResponseObject(100, "success", list);
	}
	
	@RequestMapping("/permissionAdd")
	public ResponseObject permissionAdd(@RequestParam int level, @RequestParam int watchMovieCount) {
		UserLevelPermission per = ulps.find(new QueryCondition().addCondition("level", "=", level));
		if(per != null) {
			return new ResponseObject(101, "此用户级别已存在");
		}
		per = new UserLevelPermission();
		per.setLevel(level);
		per.setWatchMovieCount(watchMovieCount);
		ulps.add(per); //添加到数据库
		cache.set(CacheKeyPre.yellow_user_level_permission, String.valueOf(per.getLevel()), String.valueOf(per.getWatchMovieCount()));//添加到缓存
		return new ResponseObject(100, "success");
	}
	
	@RequestMapping("/permissionDelete")
	public ResponseObject permissionDelete(@RequestParam int perId) {
		ulps.delete(perId);//删除数据库
		cache.delete(CacheKeyPre.yellow_user_level_permission, String.valueOf(perId));//删除缓存
		return new ResponseObject(100, "success");
	}
}