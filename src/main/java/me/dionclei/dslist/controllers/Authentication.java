package me.dionclei.dslist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import me.dionclei.dslist.dto.LoginRequest;
import me.dionclei.dslist.dto.RegisterRequest;
import me.dionclei.dslist.entities.GameUser;
import me.dionclei.dslist.entities.enums.UserRole;
import me.dionclei.dslist.repositories.UserRepository;

@RestController
@RequestMapping("/auth")
public class Authentication {
	
	@Autowired
	UserRepository repository;
	
	@Autowired
	private AuthenticationManager manager;
	
	@PostMapping("/login")
	public ResponseEntity<Void> login(@RequestBody LoginRequest request) {
		var usernamepassword = new UsernamePasswordAuthenticationToken(request.email(), request.password());
		var auth = manager.authenticate(usernamepassword);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequest request) {
		if(repository.findByEmail(request.email()) != null) return ResponseEntity.badRequest().build();
		if(request.password().length() < 6 || request.password().length() > 16) return ResponseEntity.badRequest().build();
		GameUser u = new GameUser(request.name(), request.email(), request.password(), UserRole.USER);
		repository.save(u);
		return ResponseEntity.ok().build();
	}
	
}
