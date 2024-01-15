package com.develop.photoapp.client;

import com.develop.photoapp.shared.AlbumDTOResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
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

@Component
class AlbumsFallbackFactory implements FallbackFactory<AlbumServiceClient> {
    @Override
    public AlbumServiceClient create(Throwable cause) {
        return new AlbumsServiceClientFallback(cause);
    }
}

@Slf4j
@RequiredArgsConstructor
class AlbumsServiceClientFallback implements AlbumServiceClient {
    private final Throwable cause;

    @Override
    public List<AlbumDTOResponse> getAlbums(String id) {
        if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
            log.error(String.format("404 error took place when getAlbums() was called " +
                    "with userId: %s. The error message: %s", id, cause.getLocalizedMessage()));
        } else {
            log.error(String.format("Other error took place: %s", cause.getLocalizedMessage()));
        }
        return new ArrayList<>();
    }
}