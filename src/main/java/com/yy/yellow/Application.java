package com.yy.yellow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.yy.yellow.interceptor.CheckAdminUserLoginInterceptor;

/**
 * 启动类配置
 * 继承SpringBootServletInitializer配置启动
 * 实现WebMvcConfigurer配置一些web组件，如intercetor之类的
 * @author yy
 *
 */
@SpringBootApplication//相当于@Configuration、@ComponentScan、@EnableAutoConfiguration
public class Application extends SpringBootServletInitializer implements WebMvcConfigurer {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * 重写此方法让应用在web容器里启动，如：tomcat
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Application.class);
	}
	
	/**
	 * 配置interceptor
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new CheckAdminUserLoginInterceptor()).addPathPatterns("/administration/**");
	}
}