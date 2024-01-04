package com.develop.photoapp.service;

import com.develop.photoapp.entity.UserEntity;
import com.develop.photoapp.repository.UsersRepository;
import com.develop.photoapp.shared.UserDTORequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDTORequest createUser(UserDTORequest userDetails) {
        userDetails.setUserId(UUID.randomUUID().toString());

        UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
        userEntity.setEncryptedPassword("test");
        usersRepository.save(userEntity);

        return modelMapper.map(userEntity, UserDTORequest.class);
    }
}