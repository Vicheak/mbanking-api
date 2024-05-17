package com.vicheak.coreapi.api.user;

import com.vicheak.coreapi.api.user.web.CreateUserDto;
import com.vicheak.coreapi.api.user.web.UpdateUserDto;
import com.vicheak.coreapi.api.user.web.UserDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.lang.annotation.Target;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto userToUserDto(User user);

    Iterable<UserDto> usersToUsersDto(Iterable<User> users);

    User createUserDtoToUser(CreateUserDto createUserDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserDtoToUser(UpdateUserDto updateUserDto, @MappingTarget User usre);

}
