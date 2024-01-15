package com.develop.photoapp.client;

import com.develop.photoapp.shared.AlbumDTOResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

// @FeignClient(name = "albums-ws", fallback = AlbumsFallback.class)
@FeignClient(name = "albums-ws", fallbackFactory = AlbumsFallbackFactory.class)
public interface AlbumServiceClient {
    @GetMapping("/users/{id}/albums")
    List<AlbumDTOResponse> getAlbums(@PathVariable String id);
}

/*
@Component
class AlbumsFallback implements AlbumServiceClient {
    @Override
    public List<AlbumDTOResponse> getAlbums(String id) {
        return new ArrayList<>();
    }
}
*/

class AlbumsFallbackFactory implements FallbackFactory<AlbumServiceClient> {
    @Override
    public AlbumServiceClient create(Throwable cause) {
        return new AlbumsServiceClientFallback(cause);
    }
}

@RequiredArgsConstructor
class AlbumsServiceClientFallback implements AlbumServiceClient {
    private final Throwable cause;

    @Override
    public List<AlbumDTOResponse> getAlbums(String id) {
        return new ArrayList<>();
    }
}