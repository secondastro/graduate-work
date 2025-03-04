package ru.skypro.homework;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebSecurityConfig {

  @Bean
  public ApplicationContext applicationContext() {
    return new AnnotationConfigApplicationContext();
  }
  private static final String[] AUTH_WHITELIST = {
    "/swagger-resources/**",
    "/swagger-ui.html",
    "/v3/api-docs",
    "/webjars/**",
    "/login",
    "/register"
  };

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .authorizeHttpRequests(
                authorization ->
                        authorization
                                .mvcMatchers(AUTH_WHITELIST)
                                .permitAll()
                                .mvcMatchers(HttpMethod.GET, "/ads/image/**", "/ads", "/users/avatar/**")
                                .permitAll()
                                .mvcMatchers("/ads/**", "/users/**").authenticated()
        )
        .cors()
        .and()
        .httpBasic(withDefaults());
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
