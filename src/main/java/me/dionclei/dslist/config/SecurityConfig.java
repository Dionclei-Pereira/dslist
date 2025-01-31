package me.dionclei.dslist.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	SecurityFilter filter;
	
    @Bean
    SecurityFilterChain config(HttpSecurity config) throws Exception {
    	return config.csrf(c -> c.disable())
    			.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    			.authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.POST, "/games").hasRole("ADMIN")
    					.requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
    					.requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
    					.anyRequest().authenticated())
    			.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
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
