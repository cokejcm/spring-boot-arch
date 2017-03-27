package com.demo.app.configuration.security;

import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.security.core.userdetails.User;

import com.google.common.base.Preconditions;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public final class TokenHandler {

	private final String secret;
	private final UserService userService;

	public TokenHandler(String secret, UserService userService) {
		if (secret == null || secret.trim()
		.length() <= 0) {
			throw new IllegalArgumentException();
		}
		this.secret = Base64.getEncoder()
		.encodeToString(secret.getBytes());
		this.userService = Preconditions.checkNotNull(userService);
	}

	public User parseUserFromToken(String token) {
		String username = Jwts.parser().setSigningKey(secret)
		.parseClaimsJws(token)
		.getBody()
		.getSubject();
		return userService.loadUserByUsername(username);
	}

	public String createTokenForUser(User user) {
		Date now = new Date();
		Date expiration = new Date(now.getTime() + TimeUnit.HOURS.toMillis(1l));
		return Jwts.builder().setId(UUID.randomUUID().toString())
		.setSubject(user.getUsername())
		.setIssuedAt(now)
		.setExpiration(expiration)
		.signWith(SignatureAlgorithm.HS512, secret)
		.compact();
	}
}
