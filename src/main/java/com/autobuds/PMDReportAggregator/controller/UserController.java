package com.autobuds.PMDReportAggregator.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.autobuds.PMDReportAggregator.config.TokenConfig;
import com.autobuds.PMDReportAggregator.model.User;
import com.autobuds.PMDReportAggregator.service.CustomUserDetailsService;
import com.autobuds.PMDReportAggregator.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenConfig tokenConfig;

	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
	private UserService userService ;
     
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody User user) throws Exception {

		authenticate(user.getEmail(), user.getPassword());

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(user.getEmail());

		
		final String token = tokenConfig.generateToken(userDetails);
	    Map<String, Object> response = new HashMap<>();
	    response.put("user", userService.getUser(user.getEmail()));
	    response.put("token", token);
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value = "/signUp", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User user) throws Exception {

		User newUser = userService.saveUser(user);
		return ResponseEntity.ok(newUser);
	}
	
	

	
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
