package com.autobuds.PMDReportAggregator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.autobuds.PMDReportAggregator.model.User;
import com.autobuds.PMDReportAggregator.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo ;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	public User saveUser(User user)
	{
		user.setPassword(bcryptEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}
	
	public User getUser(String email)
	{
		return userRepo.findById(email).orElseThrow(RuntimeException::new);
	}
}
