package com.yy.yellow;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.yy.yellow.interceptor.CheckAdminUserLoginInterceptor;
import com.yy.yellow.interceptor.CheckUserLoginInterceptor;
import com.yy.yellow.interceptor.VisitLogInterceptor;
import com.yy.yellow.service.VisitLogService;
import com.yy.yellow.util.Cache;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 启动类配置
 * 继承SpringBootServletInitializer配置启动
 * 实现WebMvcConfigurer配置一些web组件，如intercetor之类的
 * @author yy
 *
 */
@SpringBootApplication//相当于@Configuration、@ComponentScan、@EnableAutoConfiguration
public class Application extends SpringBootServletInitializer implements WebMvcConfigurer {
	/*
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	*/

	@Autowired
	private Cache cache;
	
	/**
	 * 重写此方法让应用在web容器里启动，如：tomcat
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Application.class);
	}

	@Autowired
	private VisitLogService vls;
	/**
	 * 配置interceptor
	 * 注意：经测试，spring的interceptor不会拦截Servlet和jsp，如果想拦截Servlet和jsp，请使用Filter
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new CheckAdminUserLoginInterceptor()).addPathPatterns("/administration/**");
		registry.addInterceptor(new CheckUserLoginInterceptor(cache)).addPathPatterns("/user/**");
		registry.addInterceptor(new VisitLogInterceptor(vls)).addPathPatterns("/**");
	}
	
	/**
	 * 配置redis
	 * @return
	 */
	@Bean(name="shardedJedisPool")
	public ShardedJedisPool getShardedJedisPool() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxTotal(10);//最大分配的对象数
		jedisPoolConfig.setMaxIdle(10);//最大能够保持idel状态的对象数
		jedisPoolConfig.setMinIdle(7);//最小空闲的对象数
		jedisPoolConfig.setMaxWaitMillis(1000);//当池内没有返回对象时，最大等待时间
		jedisPoolConfig.setLifo(false);//是否启用Lifo。如果不设置，默认为true。
		jedisPoolConfig.setTestOnBorrow(false);//当调用borrow Object方法时，是否进行有效性检查
		
		List<JedisShardInfo> jedisShardInfoList = new ArrayList<>();
		jedisShardInfoList.add(new JedisShardInfo("redis://localhost:6379/"));
		
		return new ShardedJedisPool(jedisPoolConfig, jedisShardInfoList);
	}
}