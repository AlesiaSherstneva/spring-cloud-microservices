package com.develop.photoapp.security;

import com.develop.photoapp.model.Login;
import com.develop.photoapp.service.UsersService;
import com.develop.photoapp.shared.UserDTORequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final UsersService usersService;
    private final Environment environment;

    public AuthenticationFilter(AuthenticationManager authenticationManager,
                                UsersService usersService,
                                Environment environment) {
        super(authenticationManager);
        this.usersService = usersService;
        this.environment = environment;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            Login credentials = new ObjectMapper()
                    .readValue(request.getInputStream(), Login.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.getEmail(),
                            credentials.getPassword(),
                            new ArrayList<>()
                    )
            );
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) {
        String email = ((User) authResult.getPrincipal()).getUsername();
        UserDTORequest userDetails = usersService.getUserDetailsByEmail(email);

        SecretKey key = Keys.hmacShaKeyFor(Objects.requireNonNull(environment.getProperty("token.secret"))
                .getBytes(StandardCharsets.UTF_8));

        String token = Jwts.builder()
                .subject(userDetails.getUserId())
                .expiration(new Date(System.currentTimeMillis()
                        + Long.parseLong(Objects.requireNonNull(environment.getProperty("token.expiration_time")))))
                .signWith(key, Jwts.SIG.HS512)
                .compact();

        response.addHeader("token", token);
        response.addHeader("userId", userDetails.getUserId());
    }
}