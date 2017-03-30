package com.demo.app.controller;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.app.configuration.ContextProvider;
import com.demo.app.configuration.security.TokenAuthenticationService;
import com.demo.app.configuration.security.User;
import com.demo.app.configuration.security.UserAuthentication;
import com.demo.app.configuration.security.UserService;
import com.demo.app.repository.MongoGenericRepository;
import com.demo.app.repository.security.UserRepository;

@Path("/")
@Component
public class LoginController {

	@Autowired
	UserService userService;
	@Autowired
	TokenAuthenticationService tokenAuthenticationService;
	//@Autowired
	//UserRepository userRepository;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/login")
	public void authenticate(@Context HttpServletResponse response, User user) {
		UserRepository u = ContextProvider.getBean(UserRepository.class);
		MongoGenericRepository m = ContextProvider.getBean(MongoGenericRepository.class);
		System.out.println(u);
		System.out.println(m);
		//boolean enabled = userRepository.validateUser(user);
		//System.out.println(enabled);
		// Validate the credentials against the Db
		org.springframework.security.core.userdetails.User userDb;
		try {
			userDb = userService.loadUserByUsername(user.getUsername());
			// Login that user
			UserAuthentication authentication = new UserAuthentication(userDb);
			tokenAuthenticationService.addAuthentication(response, authentication);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

}
