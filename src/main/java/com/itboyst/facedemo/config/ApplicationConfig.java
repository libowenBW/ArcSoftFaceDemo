package com.itboyst.facedemo.config;

import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

public class ApplicationConfig {

	public static FreeMarkerViewResolver freeMarkerViewResolver() {
		FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
		resolver.setPrefix("");
		resolver.setSuffix(".ftl");
		resolver.setContentType("text/html; charset=UTF-8");
		resolver.setRequestContextAttribute("request");
		resolver.setExposeContextBeansAsAttributes(true);
		resolver.setExposeRequestAttributes(true);
		resolver.setExposeSessionAttributes(true);
		return resolver;
	}

}
