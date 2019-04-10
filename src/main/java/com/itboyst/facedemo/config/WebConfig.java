package com.itboyst.facedemo.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
//import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

/**
 * 
 * @author Wilson
 *         该类用于扫描生成Web组件所需的bean,通过实现WebMvcConfigurerAdapter对SpringMVC的配置根据自己需求进行自定义
 */
@Configuration
@EnableWebMvc // 相当于<mvc:annotation-driver/>，启用注解驱动的Spring
				// MVC,使@RequestParam、@RequestMapping等注解可以被识别
@ComponentScan(basePackages = "com.itboyst.facedemo.controller")
public class WebConfig extends WebMvcConfigurerAdapter {
	// JSP视图解析器
	/*
	 * @Bean public ViewResolver internalViewResolver(){
	 * InternalResourceViewResolver viewResolver = new
	 * InternalResourceViewResolver("/WEB-INF/views/", ".jsp");
	 * viewResolver.setExposeContextBeansAsAttributes(true); return
	 * viewResolver; }
	 */

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	/*
	 * @Bean public MessageSource messageSource() { ResourceBundleMessageSource
	 * message = new ResourceBundleMessageSource();
	 * message.setBasename("welcome"); message.setUseCodeAsDefaultMessage(true);
	 * return message; }
	 */

	// tiles文件解析器
	/*
	 * @Bean public TilesConfigurer tilesConfigurer() { TilesConfigurer tiles =
	 * new TilesConfigurer(); tiles.setDefinitions(new String[] {
	 * "/WEB-INF/layout/tiles.xml" }); // 指定tiles文件位置
	 * tiles.setCheckRefresh(true); return tiles; }
	 * 
	 * // Apache Tiles视图解析器
	 * 
	 * @Bean public ViewResolver tilesViewResolver() { return new
	 * TilesViewResolver(); }
	 */
	// @Bean // 生成视图解析器并为解析器注入模板引擎
	// public ViewResolver viewResolver(SpringTemplateEngine templateEngine) {
	// ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
	// viewResolver.setContentType("text/html; charset=utf-8");
	// viewResolver.setTemplateEngine(templateEngine); // 讲逻辑试图转为 thymeleaf模板
	// viewResolver.setOrder(1);
	// return viewResolver;
	// }

	// @Bean // 为模板引擎注入模板解析器
	// public TemplateEngine templateEngine(TemplateResolver templateResolver) {
	// SpringTemplateEngine templateEngine = new SpringTemplateEngine();
	// templateEngine.setTemplateResolver(templateResolver);
	// return templateEngine; // 生成模板引擎 对一系列模板进行处理
	// }
	//
	// @Bean // 配置生成模板解析器
	// public TemplateResolver templateResolver() {
	// // WebApplicationContext webApplicationContext =
	// // ContextLoader.getCurrentWebApplicationContext();
	// //
	// ServletContextTemplateResolver需要一个ServletContext作为构造参数，可通过WebApplicationContext
	// // 的方法获得
	// ServletContextTemplateResolver templateResolver = new
	// ServletContextTemplateResolver();
	// templateResolver.setPrefix("classpath:/templates/");
	// templateResolver.setSuffix(".html");
	// templateResolver.setCharacterEncoding("UTF-8");
	// // 设置模板模式,也可用字符串"HTML"代替,此处不建议使用HTML5,原因看下图源码
	// templateResolver.setTemplateMode("LEGACYHTML5");
	// return templateResolver; // 返回一个模板处理器
	// }

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}

	@Bean
	public SpringResourceTemplateResolver templateResolver() {
		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
		// 配置模板
		templateResolver.setPrefix("classpath:/templates/");
		templateResolver.setSuffix(".html");
		// 使用HTML的模式，也就是支持HTML5的方式，使用data-th-*的H5写法来写thymeleaf的标签语法
		templateResolver.setTemplateMode("LEGACYHTML5");
		// 之前在application.properties中看到的缓存配置
		templateResolver.setCacheable(false);

		return templateResolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine() {
		// 模板引擎增加SpringSecurityDialect，让模板能用到sec前缀，获取spring security的内容
		SpringTemplateEngine engine = new SpringTemplateEngine();
		Set<IDialect> dialects = new HashSet<>();
		// dialects.add(securityDialect);
		engine.addDialect(new SpringSecurityDialect());
		engine.setAdditionalDialects(dialects);

		engine.setTemplateResolver(templateResolver());
		// 允许在内容中使用spring EL表达式
		// engine.setEnableSpringELCompiler(true);

		return engine;
	}

	// 声明ViewResolver
	@Bean
	public ThymeleafViewResolver viewResolver() {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine());
		return viewResolver;
	}
}