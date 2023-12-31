package com.develop.app.ws.service;

import com.develop.app.ws.model.UserDetails;
import com.develop.app.ws.model.UserRest;

public interface UserService {
    UserRest createUser(UserDetails userDetails);
}