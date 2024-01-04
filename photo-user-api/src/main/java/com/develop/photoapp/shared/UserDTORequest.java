package com.develop.photoapp.shared;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class UserDTORequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 3025186900342435379L;

    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String userId;
    private String encryptedPassword;
}