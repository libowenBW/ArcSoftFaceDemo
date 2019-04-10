package com.itboyst.facedemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello 控制器.
 * 
 * @since 1.0.0 2017年4月19日
 * @author <a href="https://waylau.com">Way Lau</a> 
 */
@RestController
public class HelloController {
	@RequestMapping("/hello")
	public String hello() {
		return "Hello world!";
	}
}
