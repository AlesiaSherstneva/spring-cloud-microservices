package com.develop.photoapp.shared;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlbumDTOResponse {
    private String albumId;
    private String userId; 
    private String name;
    private String description;
}