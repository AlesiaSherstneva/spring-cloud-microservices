package com.develop.photoapp.repository;

import com.develop.photoapp.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<UserEntity, Long> {
}