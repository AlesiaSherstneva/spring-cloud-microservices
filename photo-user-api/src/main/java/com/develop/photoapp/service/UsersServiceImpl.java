package com.develop.photoapp.service;

import com.develop.photoapp.entity.UserEntity;
import com.develop.photoapp.repository.UsersRepository;
import com.develop.photoapp.shared.UserDTORequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder encoder;

    @Override
    public UserDTORequest createUser(UserDTORequest userDetails) {
        userDetails.setUserId(UUID.randomUUID().toString());
        userDetails.setEncryptedPassword(encoder.encode(userDetails.getPassword()));

        UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
        usersRepository.save(userEntity);

        return modelMapper.map(userEntity, UserDTORequest.class);
    }
}