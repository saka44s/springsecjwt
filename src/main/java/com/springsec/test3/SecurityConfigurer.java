package com.springsec.test3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter{
	
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	JWTRequestFilter jwtRequestFilter;
	
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
	
	/*
	 * public void configure(HttpSecurity http) throws Exception {
	 * http.authorizeRequests() .antMatchers("/").hasAnyRole().and().formLogin(); }
	 */
	
	
	/*
	 * public void configure(HttpSecurity http) throws Exception {
	 * http.csrf().disable()
	 * .authorizeRequests().antMatchers("/authenticate").permitAll()
	 * .anyRequest().authenticated(); }
	 */
	
	
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.authorizeHttpRequests().antMatchers("/authenticate").permitAll()
		.anyRequest().authenticated().and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterAfter(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return  NoOpPasswordEncoder.getInstance();
	}
	
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
