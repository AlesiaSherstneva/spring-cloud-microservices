package com.develop.photoapp.service;

import com.develop.photoapp.shared.UserDTORequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsersService extends UserDetailsService {
    UserDTORequest createUser(UserDTORequest userDetails);

    UserDTORequest getUserDetailsByEmail(String email);
}