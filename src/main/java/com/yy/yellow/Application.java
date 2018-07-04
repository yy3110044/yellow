package com.yy.yellow;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.yy.yellow.interceptor.CheckAdminUserLoginInterceptor;
import com.yy.yellow.interceptor.CheckUserLoginInterceptor;
import com.yy.yellow.util.Cache;

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

	/**
	 * 配置interceptor
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new CheckAdminUserLoginInterceptor()).addPathPatterns("/administration/**");
		registry.addInterceptor(new CheckUserLoginInterceptor(cache)).addPathPatterns("/user/**");
	}
	
	/**
	 * 配置图片验证码工具类
	 * @return
	 */
	@Value("${kaptcha.image.width:95}") private String kaptchaWidth;
	@Value("${kaptcha.image.height:35}") private String kaptchaHeight;
	@Value("${kaptcha.textproducer.font.size:32}") private String kaptchaFontSize;
	@Value("${kaptcha.textproducer.char.length:4}") private String kaptchaCharLenght;
	@Value("${kaptcha.border:yes}") private String kaptchaBorder;
	@Value("${kaptcha.border.color:105,179,90}") private String kaptchaBorderColor;
	@Value("${kaptcha.textproducer.font.color:blue}") private String kaptchaFontColor;
	@Value("${kaptcha.textproducer.font.names:宋体,楷体,微软雅黑}") private String kaptchaFontNames;
	@Bean(name="defaultKaptcha")
	public DefaultKaptcha getKaptchaBean() {
		DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
		Properties prop = new Properties();
		prop.setProperty("kaptcha.border", this.kaptchaBorder);
		prop.setProperty("kaptcha.border.color", this.kaptchaBorderColor);
		prop.setProperty("kaptcha.textproducer.font.color", this.kaptchaFontColor);
		prop.setProperty("kaptcha.image.width", this.kaptchaWidth);
		prop.setProperty("kaptcha.image.height", this.kaptchaHeight);
		prop.setProperty("kaptcha.textproducer.font.size", this.kaptchaFontSize);
        prop.setProperty("kaptcha.textproducer.char.length", this.kaptchaCharLenght);
        prop.setProperty("kaptcha.textproducer.font.names", this.kaptchaFontNames);
        Config config = new Config(prop);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
	}
}