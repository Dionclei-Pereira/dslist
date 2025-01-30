package me.dionclei.dslist;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain config(HttpSecurity config) throws Exception {
    	return config.csrf(c -> c.disable())
    			.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    			.authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.POST, "/games").hasRole("ADMIN")
    					.requestMatchers(HttpMethod.POST, "/auth").permitAll()
    					.anyRequest().authenticated())
    			.build();
    }
    
    @Bean
    PasswordEncoder password() {
    	return new BCryptPasswordEncoder();
    }
    
    @Bean
    AuthenticationManager manager(AuthenticationConfiguration config) throws Exception {
    	return config.getAuthenticationManager();
    }
	
}
