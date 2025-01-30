package me.dionclei.dslist.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import me.dionclei.dslist.entities.GameUser;

public interface UserRepository extends JpaRepository<GameUser, UUID>{
	
	UserDetails findByEmail(String email);
	
}
