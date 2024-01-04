package com.develop.photoapp.service;

import com.develop.photoapp.shared.UserDTORequest;

public interface UsersService {
    UserDTORequest createUser(UserDTORequest userDetails);
}