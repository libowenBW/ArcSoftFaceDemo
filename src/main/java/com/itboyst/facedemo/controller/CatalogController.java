package com.itboyst.facedemo.controller;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itboyst.facedemo.domain.Catalog;
import com.itboyst.facedemo.domain.User;
import com.itboyst.facedemo.service.impl.CatalogService;
import com.itboyst.facedemo.util.ConstraintViolationExceptionHandler;
import com.itboyst.facedemo.vo.CatalogVO;
import com.itboyst.facedemo.vo.Response;

/**
 * 分类 控制器.
 * 
 * @since 1.0.0 2017年4月10日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
@Controller
@RequestMapping("/catalogs")
public class CatalogController {

	@Autowired
	private CatalogService catalogService;

	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * 获取分类列表
	 * 
	 * @param username
	 * @param model
	 * @return
	 */
	@GetMapping
	public String listComments(@RequestParam(value = "username", required = true) String username, Model model) {
		User user = (User) userDetailsService.loadUserByUsername(username);
		List<Catalog> catalogs = catalogService.listCatalogs(user);

		// 判断操作用户是否是分类的所有者
		boolean isOwner = false;
		User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal != null && user.getUsername().equals(principal.getUsername())) {
			isOwner = true;
		}
		// if (SecurityContextHolder.getContext().getAuthentication() != null
		// &&
		// SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
		// && !SecurityContextHolder
		// .getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser"))
		// {
		//
		// }

		model.addAttribute("isCatalogsOwner", isOwner);
		model.addAttribute("catalogs", catalogs);
		return "userspace/personal :: #catalogRepleace";
	}

	/**
	 * 创建分类
	 * 
	 * @param catalogVO
	 * @return
	 */
	@PostMapping
	@PreAuthorize("authentication.name.equals(#catalogVO.username)") // 指定用户才能操作方法
	public ResponseEntity<Response> create(@RequestBody CatalogVO catalogVO) {

		String username = catalogVO.getUsername();
		Catalog catalog = catalogVO.getCatalog();

		User user = (User) userDetailsService.loadUserByUsername(username);

		try {
			catalog.setUser(user);
			catalogService.saveCatalog(catalog);
		} catch (ConstraintViolationException e) {
			return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}

		return ResponseEntity.ok().body(new Response(true, "处理成功", null));
	}

	/**
	 * 删除分类
	 * 
	 * @param username
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("authentication.name.equals(#username)") // 指定用户才能操作方法
	public ResponseEntity<Response> delete(String username, @PathVariable("id") Long id) {
		try {
			catalogService.removeCatalog(id);
		} catch (ConstraintViolationException e) {
			return ResponseEntity.ok().body(new Response(false, ConstraintViolationExceptionHandler.getMessage(e)));
		} catch (Exception e) {
			return ResponseEntity.ok().body(new Response(false, e.getMessage()));
		}

		return ResponseEntity.ok().body(new Response(true, "处理成功", null));
	}

	/**
	 * 获取分类编辑界面
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/edit")
	public String getCatalogEdit(Model model) {
		Catalog catalog = new Catalog(null, null);
		model.addAttribute("catalog", catalog);
		return "userspace/catalogedit";
	}

	/**
	 * 根据 Id 获取编辑界面
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/edit/{id}")
	public String getCatalogById(@PathVariable("id") Long id, Model model) {
		Catalog catalog = catalogService.getCatalogById(id);
		model.addAttribute("catalog", catalog);
		return "userspace/catalogedit";
	}

}
