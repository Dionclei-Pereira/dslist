package me.dionclei.dslist.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import me.dionclei.dslist.entities.GameUser;

@Service
public class TokenService {
	private String key = "mySecretKey";
	
	public String generateToken(GameUser user) {
			Algorithm algorithm = Algorithm.HMAC256(key);
			String token = JWT.create().withIssuer("Game").withSubject(user.getEmail()).withExpiresAt(generateDate()).sign(algorithm);
			return token;
	}
	
	public String validateToken(String token) {
		Algorithm algorithm = Algorithm.HMAC256(key);
		return JWT.require(algorithm).withIssuer("Game").build().verify(token).getSubject();
	}
	
	private Instant generateDate() {
	 return LocalDateTime.now().plusDays(5).toInstant(ZoneOffset.UTC);
	}
	
}
