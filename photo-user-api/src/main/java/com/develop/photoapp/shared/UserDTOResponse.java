package com.develop.photoapp.shared;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTOResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String userId;
}