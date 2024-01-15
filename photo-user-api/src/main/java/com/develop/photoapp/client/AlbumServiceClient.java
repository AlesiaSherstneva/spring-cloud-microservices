package com.develop.photoapp.client;

import com.develop.photoapp.shared.AlbumDTOResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "albums-ws", fallback = AlbumsFallback.class)
public interface AlbumServiceClient {
    @GetMapping("/users/{id}/albums")
    List<AlbumDTOResponse> getAlbums(@PathVariable String id);
}

@Component
class AlbumsFallback implements AlbumServiceClient {
    @Override
    public List<AlbumDTOResponse> getAlbums(String id) {
        return new ArrayList<>();
    }
}