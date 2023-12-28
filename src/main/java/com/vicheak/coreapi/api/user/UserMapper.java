package com.vicheak.coreapi.api.user;

import com.vicheak.coreapi.api.user.web.CreateUserDto;
import com.vicheak.coreapi.api.user.web.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto userToUserDto(User user);

    Iterable<UserDto> usersToUsersDto(Iterable<User> users);

    User createUserDtoToUser(CreateUserDto createUserDto);

}
