package com.yy.yellow;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import com.yy.yellow.po.UserLevelPermission;
import com.yy.yellow.service.UserLevelPermissionService;
import com.yy.yellow.util.Cache;
import com.yy.yellow.util.CacheKeyPre;

/**
 * 开机启动操作
 * @author yy
 *
 */
@Component
public class AppRunner implements CommandLineRunner, ApplicationRunner, Ordered {
	private static final Logger logger = LogManager.getLogger(AppRunner.class);
	
	@Autowired
	private UserLevelPermissionService ulps;
	
	@Autowired
	private Cache cache;

	//实现CommandLineRunner接口，并重写run方法，就可以在spring boot启动后执行一些操作，
	//同时在要在实现类上加上@Component注解，这里直接用
	@Override
	public void run(String... args) throws Exception {
		//把用户级别权限加载进cache
		logger.debug("加载用户权限级别");
		List<UserLevelPermission> list = this.ulps.query(null);
		for(UserLevelPermission ulp : list) {
			logger.debug(CacheKeyPre.user_level_permission + ":" + String.valueOf(ulp.getLevel()) + "    " + ulp.getWatchMovieCount());
			cache.set(CacheKeyPre.user_level_permission, String.valueOf(ulp.getLevel()), String.valueOf(ulp.getWatchMovieCount()));
		}
	}

	/**
	 * 实现Ordered接口，可以实现，不同的操作按顺序启动
	 */
	@Override
	public int getOrder() {
		return 0;
	}

	/**
	 * 实现ApplicationRunner接口，也可以实现开机启动，ApplicationArguments对参数作了进一步封装
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.debug("ApplicationRunner.run()");
	}
}
