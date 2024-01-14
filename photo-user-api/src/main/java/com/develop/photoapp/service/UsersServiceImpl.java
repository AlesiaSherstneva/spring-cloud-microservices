package com.develop.photoapp.service;

import com.develop.photoapp.client.AlbumServiceClient;
import com.develop.photoapp.entity.UserEntity;
import com.develop.photoapp.repository.UsersRepository;
import com.develop.photoapp.shared.AlbumDTOResponse;
import com.develop.photoapp.shared.UserDTORequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder encoder;
    // private final RestTemplate restTemplate;
    // private final Environment environment;
    private final AlbumServiceClient albumServiceClient;

    @Override
    public UserDTORequest createUser(UserDTORequest userDetails) {
        userDetails.setUserId(UUID.randomUUID().toString());
        userDetails.setEncryptedPassword(encoder.encode(userDetails.getPassword()));

        UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
        usersRepository.save(userEntity);

        return modelMapper.map(userEntity, UserDTORequest.class);
    }

    @Override
    public UserDTORequest getUserDetailsByEmail(String email) {
        UserEntity userEntity = usersRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }
        return modelMapper.map(userEntity, UserDTORequest.class);
    }

    @Override
    public UserDTORequest getUserByUserId(String userId) {
        UserEntity userEntity = usersRepository.findByUserId(userId);
        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found");
        }

        /*
        String albumsUrl = String.format(Objects.requireNonNull(environment.getProperty("albums.url")), userId);
        ResponseEntity<List<AlbumDTOResponse>> albumsListResponse = restTemplate.exchange(
                albumsUrl, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });
        List<AlbumDTOResponse> albums = albumsListResponse.getBody();

        List<AlbumDTOResponse> albums = null;
        try {
             albums = albumServiceClient.getAlbums(userId);
        } catch (FeignException exception) {
            log.error(exception.getLocalizedMessage());
        }
        */

        List<AlbumDTOResponse> albums = albumServiceClient.getAlbums(userId);

        UserDTORequest userDTORequest = modelMapper.map(userEntity, UserDTORequest.class);
        userDTORequest.setAlbums(albums);

        return userDTORequest;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = usersRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(),
                true, true, true, true, new ArrayList<>());
    }
}