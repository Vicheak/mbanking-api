package com.vicheak.coreapi.api.user;

import com.vicheak.coreapi.api.user.web.CreateUserDto;
import com.vicheak.coreapi.api.user.web.UpdateUserDto;
import com.vicheak.coreapi.api.user.web.UserDto;

public interface UserService {

    Iterable<UserDto> findAll();

    UserDto createNew(CreateUserDto createUserDto);

    UserDto findById(Integer id);

    UserDto findByUuid(String uuid);

    void deleteByUuid(String uuid);

    void disableByUuid(String uuid);

    UserDto updateByUuid(String uuid, UpdateUserDto updateUserDto);

}
