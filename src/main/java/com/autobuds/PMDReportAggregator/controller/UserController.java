package com.autobuds.PMDReportAggregator.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.autobuds.PMDReportAggregator.config.TokenConfig;
import com.autobuds.PMDReportAggregator.exception.InvalidCredentialsException;
import com.autobuds.PMDReportAggregator.model.Org;
import com.autobuds.PMDReportAggregator.model.User;
import com.autobuds.PMDReportAggregator.service.CustomUserDetailsService;
import com.autobuds.PMDReportAggregator.service.SFOrgService;
import com.autobuds.PMDReportAggregator.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("api/auth")
public class UserController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenConfig tokenConfig;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private SFOrgService sfOrgService ;

	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody User user) {

		Map<String, Object> response = new HashMap<>();
		try {
			authenticate(user.getEmail(), user.getPassword());

			final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
			final String token = tokenConfig.generateToken(userDetails);
			response.put("user", userService.getUser(user.getEmail()));
			response.put("token", token);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			throw new InvalidCredentialsException("Invalid Username/Password. Try Again!!");
		}
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User user) throws Exception {
       
		try {
		Map<String, Object> response = new HashMap<>();
		userService.saveUser(user);
		response.put("message","Registered Successfully");
		return ResponseEntity.ok(response);
		}
		catch(Exception ex)
		{
			throw new InvalidCredentialsException("Invalid User details. Try Again!!");	
		}
	}
	@GetMapping(value = "/callback")
	public RedirectView registerOrg(@RequestParam String code,@RequestParam String state) throws Exception {
           System.out.println(code +" "+ state);
		RedirectView redirectView = new RedirectView();
		 sfOrgService.saveOrg(code, state);
		 redirectView.setUrl("http://localhost:4200/user profile?message=success");
		 return redirectView ;
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
