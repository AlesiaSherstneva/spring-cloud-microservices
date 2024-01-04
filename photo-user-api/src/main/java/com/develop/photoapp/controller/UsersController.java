package com.develop.photoapp.controller;

import com.develop.photoapp.model.User;
import com.develop.photoapp.service.UsersService;
import com.develop.photoapp.shared.UserDTORequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {
    private final Environment environment;
    private final UsersService usersService;
    private final ModelMapper modelMapper;

    @GetMapping("/status/check")
    public String status() {
        return "Working on port: " + environment.getProperty("local.server.port");
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@Valid @RequestBody User userDetails) {
        UserDTORequest userDTORequest = modelMapper.map(userDetails, UserDTORequest.class);
        usersService.createUser(userDTORequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}