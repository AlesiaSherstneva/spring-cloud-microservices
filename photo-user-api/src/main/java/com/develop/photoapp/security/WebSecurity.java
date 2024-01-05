package com.develop.photoapp.security;

import com.develop.photoapp.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity {
    private final UsersService usersService;
    private final Environment environment;
    private final BCryptPasswordEncoder encoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.userDetailsService(usersService).passwordEncoder(encoder);

        AuthenticationManager authManager = authBuilder.build();

        return http
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .csrf(AbstractHttpConfigurer::disable)
                .authenticationManager(authManager)
                .addFilter(getAuthenticationFilter(authManager))
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/users/**", "/h2-console/**")
                        .permitAll())
                .build();
    }

    private AuthenticationFilter getAuthenticationFilter(AuthenticationManager authManager) {
        AuthenticationFilter filter = new AuthenticationFilter(usersService, environment);
        filter.setAuthenticationManager(authManager);
        return filter;
    }
}