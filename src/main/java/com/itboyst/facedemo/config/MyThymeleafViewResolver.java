package com.itboyst.facedemo.config;

import java.io.IOException;
import java.util.Locale;

import org.thymeleaf.spring4.view.ThymeleafViewResolver;

public class MyThymeleafViewResolver extends ThymeleafViewResolver {

	// 由于我的freemarker 配置无法被加载所以我才写死的正确方法是 利用@Value注解获取
	private String prefix = "classpath:/templates/";
	private String suffix = ".html";

	@Override
	public org.springframework.web.servlet.View resolveViewName(final String viewName, final Locale locale)
			throws Exception {
		String resourceName = prefix + viewName + suffix;
		try {
			this.getApplicationContext().getResource(resourceName).getInputStream();
		} catch (final IOException e) {
			if (logger.isDebugEnabled()) {
				if (logger.isTraceEnabled()) {
					logger.trace("视图名：" + resourceName + "{}不存在！");
				} else {
					logger.debug("视图名：" + resourceName + "{}不存在！");
				}
			}
			return null;
		}
		return super.resolveViewName(viewName, locale);
	}

}
