package com.vicheak.coreapi.api.user;

import com.vicheak.coreapi.api.authority.Role;
import com.vicheak.coreapi.api.user.web.CreateUserDto;
import com.vicheak.coreapi.api.user.web.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserRoleRepository userRoleRepository;

    @Override
    public Iterable<UserDto> findAll() {
        //Retrieve user list from repository
        Iterable<User> users = userRepository.findAll();

        return userMapper.usersToUsersDto(users);
    }

    @Override
    public UserDto createNew(CreateUserDto createUserDto) {
        User newUser = userMapper.createUserDtoToUser(createUserDto);
        newUser.setUuid(UUID.randomUUID().toString());
        newUser.setIsDeleted(false);
        newUser.setIsVerified(true);

        userRepository.save(newUser);

        List<UserRole> userRoles = new ArrayList<>();

        //validate role id
        createUserDto.roleIds().forEach(id ->
                userRoles.add(UserRole.builder()
                        .user(newUser)
                        .role(Role.builder().id(id).build())
                        .build()));

        userRoleRepository.saveAll(userRoles);

        return findById(newUser.getId());
    }

    @Override
    public UserDto findById(Integer id) {

        User user = userRepository.findById(id).orElseThrow();

        return userMapper.userToUserDto(user);
    }

    @Override
    public UserDto findByUuid(String uuid) {

        User user = userRepository.findByUuid(uuid)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                String.format("User UUID : %s is not found!", uuid))
                );

        return userMapper.userToUserDto(user);
    }

    @Override
    public void deleteByUuid(String uuid) {

        if (userRepository.existsByUuid(uuid)) {
            User user = userRepository.findByUuid(uuid).orElseThrow();
            userRepository.delete(user);
            return;
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("User UUID : %s is not found!", uuid));
    }

    @Override
    public void disableByUuid(String uuid) {
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                String.format("User UUID : %s is not found!", uuid))
                );

        user.setIsDeleted(true);

        userRepository.save(user);
    }

}
