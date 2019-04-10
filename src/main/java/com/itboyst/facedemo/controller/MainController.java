package com.itboyst.facedemo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.itboyst.facedemo.domain.Authority;
import com.itboyst.facedemo.domain.User;
import com.itboyst.facedemo.service.impl.AuthorityService;
import com.itboyst.facedemo.service.impl.UserService;
import com.itboyst.facedemo.vo.FaceAddParam;

/**
 * 主页控制器.
 * 
 * @since 1.0.0 2017年5月28日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
@Controller
public class MainController {

	private static final Long ROLE_USER_AUTHORITY_ID = 2L;
	@Autowired
	private UserService userService;

	@Autowired
	private AuthorityService authorityService;

	@GetMapping("/")
	public String root() {
		return "redirect:/index";
	}

	@GetMapping("/index")
	public String index() {
		return "redirect:/blogs";
	}

	@GetMapping("/login")
	public ModelAndView login(Model model) {
		// model.addAttribute("username", "");
		// model.addAttribute("password", "");
		// return "login";
		FaceAddParam user = new FaceAddParam();
		user.setPassword("");
		user.setUsername("");
		model.addAttribute("user", user);
		return new ModelAndView("login", "blogModel", model);
	}

	@GetMapping("/faceToLogin")
	public ModelAndView faceToLogin(String username, String password, Model model) {
		// model.addAttribute("username", "");
		// model.addAttribute("password", "");
		// return "login";
		FaceAddParam user = new FaceAddParam();
		user.setPassword(username);
		user.setUsername(password);
		model.addAttribute("user", user);
		return new ModelAndView("redirect:/login", "blogModel", model);
	}
	//
	// @PostMapping("/login")
	// public String tologin() {
	// return "redirect:/login";
	// }

	@GetMapping("/login-error")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		model.addAttribute("errorMsg", "登陆失败，用户名或者密码错误！");
		return "login";
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}

	/**
	 * 注册用户
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping("/register")
	public String registerUser(User user) {
		List<Authority> authorities = new ArrayList<>();
		authorities.add(authorityService.getAuthorityById(ROLE_USER_AUTHORITY_ID));
		user.setAuthorities(authorities);

		userService.registerUser(user);
		return "redirect:/login";
	}

	@GetMapping("/registerface")
	public String registerface() {
		return "registerface";
	}
}
