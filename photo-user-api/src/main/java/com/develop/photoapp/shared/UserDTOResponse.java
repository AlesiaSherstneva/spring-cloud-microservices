package com.develop.photoapp.shared;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTOResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String userId;
    private List<AlbumDTOResponse> albums;
}