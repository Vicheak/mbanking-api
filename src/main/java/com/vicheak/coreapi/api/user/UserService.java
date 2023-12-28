package com.vicheak.coreapi.api.user;

import com.vicheak.coreapi.api.user.web.CreateUserDto;
import com.vicheak.coreapi.api.user.web.UserDto;

public interface UserService {

    Iterable<UserDto> findAll();

    UserDto createNew(CreateUserDto createUserDto);

    UserDto findById(Integer id);

}
