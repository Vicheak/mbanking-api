package com.vicheak.coreapi.api.user;

import com.vicheak.coreapi.api.authority.Role;
import com.vicheak.coreapi.api.user.web.CreateUserDto;
import com.vicheak.coreapi.api.user.web.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserRoleRepository userRoleRepository;

    @Override
    public Iterable<UserDto> findAll() {
        //Retrieve user list from repository
        Iterable<User> users = userRepository.findAll();

        //List<UserDto> usersDto = new ArrayList<>();

        /*usersDto = StreamSupport.stream(users.spliterator(), false)
                .map(new Function<User, UserDto>() {
                    @Override
                    public UserDto apply(User user) {
                        return UserDto.builder()
                                .uuid(user.getUuid())
                                .name(user.getName())
                                .gender(user.getGender())
                                .email(user.getEmail())
                                .phoneNumber(user.getPhoneNumber())
                                .isStudent(user.getIsStudent())
                                .studentCardNo(user.getStudentCardNo())
                                .build();
                    }
                })
                .collect(Collectors.toList());*/

        /*for (User user : users) {
            usersDto.add(UserDto.builder()
                    .uuid(user.getUuid())
                    .name(user.getName())
                    .gender(user.getGender())
                    .email(user.getEmail())
                    .phoneNumber(user.getPhoneNumber())
                    .isStudent(user.getIsStudent())
                    .studentCardNo(user.getStudentCardNo())
                    .build());
        }*/

        return userMapper.usersToUsersDto(users);
    }

    @Override
    public UserDto createNew(CreateUserDto createUserDto) {

        User newUser = userMapper.createUserDtoToUser(createUserDto);
        newUser.setUuid(UUID.randomUUID().toString());
        newUser.setIsStudent(false);
        newUser.setIsDeleted(false);
        newUser.setIsVerified(true);

        userRepository.save(newUser);

        UserRole userRoleCustomer = UserRole.builder()
                .user(newUser)
                .role(Role.builder().id(3).build())
                .build();
        
        userRoleRepository.save(userRoleCustomer);

        return findById(newUser.getId());
    }

    @Override
    public UserDto findById(Integer id) {

        User user = userRepository.findById(id).orElseThrow();

        return userMapper.userToUserDto(user);
    }

}
