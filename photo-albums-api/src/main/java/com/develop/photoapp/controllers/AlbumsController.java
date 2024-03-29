package com.develop.photoapp.controllers;

import com.develop.photoapp.data.AlbumEntity;
import com.develop.photoapp.service.AlbumsService;
import com.develop.photoapp.shared.AlbumDTOResponse;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import java.lang.reflect.Type;

import org.modelmapper.TypeToken;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/users/{id}/albums")
@RequiredArgsConstructor
public class AlbumsController {
    private final AlbumsService albumsService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,})
    public List<AlbumDTOResponse> userAlbums(@PathVariable String id) {
        List<AlbumDTOResponse> returnValue = new ArrayList<>();
        List<AlbumEntity> albumsEntities = albumsService.getAlbums(id);

        if (albumsEntities == null || albumsEntities.isEmpty()) {
            return returnValue;
        }

        Type listType = new TypeToken<List<AlbumDTOResponse>>() {
        }.getType();

        returnValue = new ModelMapper().map(albumsEntities, listType);
        logger.info("Returning " + returnValue.size() + " albums");
        return returnValue;
    }
}