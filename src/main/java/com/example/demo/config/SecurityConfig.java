package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers("/api/attendance/mark").hasRole("EMPLOYEE")
            .requestMatchers("/api/attendance/report/**").hasRole("MANAGER")
            .anyRequest().authenticated()
            .and()
            .httpBasic();
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails employee = User.withUsername("employee")
                .password("{noop}password")
                .roles("EMPLOYEE")
                .build();

        UserDetails manager = User.withUsername("manager")
                .password("{noop}password")
                .roles("MANAGER")
                .build();

        return new InMemoryUserDetailsManager(employee, manager);
    }
}