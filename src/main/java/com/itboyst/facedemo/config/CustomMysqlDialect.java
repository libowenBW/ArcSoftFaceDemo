package com.itboyst.facedemo.config;

import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
public class CustomMysqlDialect extends MySQL5InnoDBDialect {
	// @Override
	// public String getTableTypeString() {
	// return " ENGINE=InnoDB";
	// }
	@Override
	public String getTableTypeString() {
		return "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_unicode_ci";
	}
}