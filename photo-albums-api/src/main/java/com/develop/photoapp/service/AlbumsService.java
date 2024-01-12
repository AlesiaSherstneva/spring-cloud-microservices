package com.develop.photoapp.service;


import com.develop.photoapp.data.AlbumEntity;
import java.util.List;

public interface AlbumsService {
    List<AlbumEntity> getAlbums(String userId);
}