package com.develop.app.ws.service;

import com.develop.app.ws.model.UserDetails;
import com.develop.app.ws.model.UserRest;
import com.develop.app.ws.shared.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    Map<String, UserRest> users;

    private final Utils utils;

    @Override
    public UserRest createUser(UserDetails userDetails) {
        UserRest returnValue = new UserRest();
        returnValue.setEmail(userDetails.getEmail());
        returnValue.setFirstName(userDetails.getFirstName());
        returnValue.setLastName(userDetails.getLastName());
        String userId = utils.generateUserId();
        returnValue.setUserId(userId);

        if (users == null) {
            users = new HashMap<>();
        }
        users.put(userId, returnValue);

        return returnValue;
    }
}
