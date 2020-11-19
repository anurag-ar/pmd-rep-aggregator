package com.autobuds.PMDReportAggregator.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.autobuds.PMDReportAggregator.model.User;
import com.autobuds.PMDReportAggregator.repository.UserRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    
		// need to edit after db gets added
        User user = userRepo.findById(email).orElse(null);
		if (user == null) {
		throw new UsernameNotFoundException("User not found with username: " + email);
	}
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),
		new ArrayList<>());
	    
	}
}
