package com.autobuds.PMDReportAggregator.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

//	@Autowired
//	private UserDao userDao;

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    
		// need to edit after db gets added
//		//DAOUser user = userDao.findByUsername(username);
//		if (user == null) {
//			throw new UsernameNotFoundException("User not found with username: " + username);
//		}
		return new org.springframework.security.core.userdetails.User( "user.getUsername()"," user.getPassword() ",
		new ArrayList<>());
		
	}
}
