package com.itboyst.facedemo.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//@Configuration
//@EnableWebMvc
// @ComponentScan("spitter.web")
public class MvcConfig extends WebMvcConfigurerAdapter {

	// @Autowired
	// private SpringResourceTemplateResolver thymeleafViewResolver;
	//
	// //
	// // // @Bean
	// // // public ThymeleafViewResolver thymeleafViewResolverBean() {
	// // // thymeleafViewResolver.setOrder(1);
	// // // return thymeleafViewResolver;
	// // // }
	// //
	// @Bean
	// public SpringResourceTemplateResolver thymeleafViewResolver() {
	// MyThymeleafViewResolver resolver = new MyThymeleafViewResolver();
	// // resolver.setPrefix(null);
	// // resolver.setSuffix(".ftl");
	// // resolver.setContentType("text/html; charset=UTF-8");
	// // resolver.setRequestContextAttribute("request");
	// // resolver.setExposeContextBeansAsAttributes(true);
	// // resolver.setExposeRequestAttributes(true);
	// // resolver.setExposeSessionAttributes(true);
	// BeanUtils.copyProperties(thymeleafViewResolver, resolver);
	// resolver.setOrder(1);
	// return resolver;
	// }

	// @Override
	// public void
	// configureDefaultServletHandling(DefaultServletHandlerConfigurer
	// configurer) {
	// configurer.enable();
	// }

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}
}
