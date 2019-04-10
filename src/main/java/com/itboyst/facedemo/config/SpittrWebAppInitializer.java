package com.itboyst.facedemo.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/*
 * 在Servlet3.0以上的环境中，容器会在类路径中查找实现javax.servlet.ServletContainerInitializer接口的类，如果发现则用它来配置
 * Servlet容器，Spring提供了这个接口的实现名为SpringServletContainerInitializer,这个类反过来又会查找实现了
 * WebApplicationInitializer的类并把配置任务交给它们来完成,AbstractAnnotationConfigDispatcherServletInitializer的祖先类已
 * 对该接口进行了实现
 */
public class SpittrWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	/**
	 * 该方法用于配置ContextLoaderListener创建的应用上下文的bean，相当于web.xml配置中的
	 * <listener>org.springframework.web.ContextLoaderListener</listener> 差异：
	 * 注解配置需要添加的是配置类<br>
	 * 文件配置ContextLoaderListener在创建时自动查找WEB-INF下的applicationContext.xml文件，当文件不止1个时需通过设置
	 * 上下文参数(context-param)配置contextConfigLocation的值
	 * 
	 * @return 带有@Configuration注解的类(这些类将会用来配置ContextLoaderListener创建的应用上下文的bean)
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { RootConfig.class };
	}

	/**
	 * 该方法用于配置DispatcherServlet所需bean，配置类一般用于生成控制层的bean(因Controller中一般包含对参数的设置及数据的返回)
	 * 相当于web.xml对Spring
	 * MVC的配置…<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>…<br>
	 * 配置类如WebConfig.class相当于DispatcherServlet中contetConfigLocation参数对应的配置文件
	 * 
	 * @return 带有@Configuration注解的类(这些类将会用来配置DispatcherServlet应用上下文中的bean)
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebConfig.class };
	}

	/**
	 * DispatcherServlet默认映射路径
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[] { ("/") };
	}

}