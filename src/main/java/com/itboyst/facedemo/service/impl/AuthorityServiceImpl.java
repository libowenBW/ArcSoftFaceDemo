/**
 * 
 */
package com.itboyst.facedemo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itboyst.facedemo.domain.Authority;
import com.itboyst.facedemo.repository.AuthorityRepository;

/**
 * Authority 服务接口的实现.
 * 
 * @since 1.0.0 2017年5月30日
 * @author <a href="https://waylau.com">Way Lau</a>
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {

	@Autowired
	private AuthorityRepository authorityRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.itboyst.facedemo.service.AuthorityService#getAuthorityById(java.lang.
	 * Long)
	 */
	@Override
	public Authority getAuthorityById(Long id) {
		return authorityRepository.findOne(id);
	}

}
