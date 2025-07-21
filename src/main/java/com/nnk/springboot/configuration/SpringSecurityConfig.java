package com.nnk.springboot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nnk.springboot.services.CustomUserDetailsService;

/**
 * Configuration de la sécurité Spring Security de l'application.
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	/**
	 * Déclaration du filtre de sécurité principal.
	 * 
	 * @param http l'objet HttpSecurity configuré par Spring
	 * @return SecurityFilterChain le filtre configuré
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/", "/home.html", "/app/login", "/app/login?error=true", "/css/**", "/403",
								"/app/error")
						.permitAll()
						.requestMatchers("/bidList/**", "/curvePoint/**", "/rating/**", "/ruleName/**", "/trade/**",
								"secure/article-details")
						.hasAnyRole("USER", "ADMIN").requestMatchers("/admin/**", "/user/**").hasRole("ADMIN")
						.anyRequest().authenticated())
				.formLogin(form -> form.loginPage("/app/login").usernameParameter("username")
						.passwordParameter("password").defaultSuccessUrl("/", true).permitAll())
				.logout(logout -> logout.logoutUrl("/app-logout").logoutSuccessUrl("/app/login?logout").permitAll())
				.exceptionHandling(exception -> exception.accessDeniedPage("/app/error")).build();
	}

	/**
	 * Déclare un encodeur de mots de passe basé sur BCrypt.
	 * 
	 * @return une instance de BCryptPasswordEncoder
	 */
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Configuration manuelle du gestionnaire d'authentification.
	 * 
	 * @param http                  le contexte HttpSecurity
	 * @param bCryptPasswordEncoder l'encodeur de mot de passe à utiliser
	 * @return une instance d'AuthenticationManager
	 */
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder)
			throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http
				.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(customUserDetailsService)
				.passwordEncoder(bCryptPasswordEncoder);
		return authenticationManagerBuilder.build();
	}
}
