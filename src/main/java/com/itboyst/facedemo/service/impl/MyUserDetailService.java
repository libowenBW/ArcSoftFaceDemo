package com.itboyst.facedemo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itboyst.facedemo.domain.User;

@Primary
@Service("myUserDetailService")
@Transactional(readOnly = true)
public class MyUserDetailService implements UserDetailsService {
	@Autowired
	private UserService userService;

	// public void getUser(UserRepository userRepository) {
	// this.userRepository = userRepository;
	// }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.getOneByUsername(username);
		if (user != null) {
			// List<GrantedAuthority> authorities = new ArrayList<>();
			// authorities.addAll(user.getAuthorities());
			return user;
		} else {
			throw new UsernameNotFoundException("User'" + username + "' not found.");
		}
	}

}
