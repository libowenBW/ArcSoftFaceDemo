package com.itboyst.facedemo.service.impl;

import com.itboyst.facedemo.domain.Authority;

/**
 * Authority 服务接口.
 * 
 * @since 1.0.0 2017年5月30日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
public interface AuthorityService {
	/**
	 * 根据ID查询 Authority
	 * 
	 * @param id
	 * @return
	 */
	Authority getAuthorityById(Long id);
}
