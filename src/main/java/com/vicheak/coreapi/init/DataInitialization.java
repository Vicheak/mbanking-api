package com.vicheak.coreapi.init;

import com.vicheak.coreapi.api.authority.Authority;
import com.vicheak.coreapi.api.authority.AuthorityRepository;
import com.vicheak.coreapi.api.authority.Role;
import com.vicheak.coreapi.api.authority.RoleRepository;
import com.vicheak.coreapi.api.user.User;
import com.vicheak.coreapi.api.user.UserRepository;
import com.vicheak.coreapi.api.user.UserRole;
import com.vicheak.coreapi.api.user.UserRoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataInitialization {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;

    @PostConstruct
    public void init() {

        //permission or authority
        Authority userRead = Authority.builder().name("user:read").build();
        Authority userWrite = Authority.builder().name("user:write").build();
        Authority userDelete = Authority.builder().name("user:delete").build();
        Authority userUpdate = Authority.builder().name("user:update").build();

        Authority accountRead = Authority.builder().name("account:read").build();
        Authority accountWrite = Authority.builder().name("account:write").build();
        Authority accountDelete = Authority.builder().name("account:delete").build();
        Authority accountUpdate = Authority.builder().name("account:update").build();

        Authority transactionRead = Authority.builder().name("transaction:read").build();
        Authority transactionWrite = Authority.builder().name("transaction:write").build();
        Authority transactionDelete = Authority.builder().name("transaction:delete").build();
        Authority transactionUpdate = Authority.builder().name("transaction:update").build();

        List<Authority> authorities = List.of(
                userRead, userWrite, userDelete, userUpdate, //user permission
                accountRead, accountWrite, accountDelete, accountUpdate, //account permission
                transactionRead, transactionWrite, transactionDelete, transactionUpdate //transaction permission
        );

        authorityRepository.saveAll(authorities);

        Role roleAdmin = Role.builder()
                .name("ADMIN")
                .authorities(authorities)
                .build();

        Role roleManager = Role.builder()
                .name("MANAGER")
                .authorities(List.of(
                        userRead, userDelete, userUpdate,
                        accountRead, accountDelete, accountUpdate,
                        transactionRead, transactionDelete, transactionUpdate
                ))
                .build();

        Role roleCustomer = Role.builder()
                .name("CUSTOMER")
                .authorities(List.of(
                        userRead, userWrite, userUpdate,
                        accountRead, accountWrite, accountUpdate,
                        transactionRead, transactionWrite
                ))
                .build();

        List<Role> roles = List.of(
                roleAdmin, roleManager, roleCustomer
        );

        roleRepository.saveAll(roles);

        User user = User.builder()
                .uuid(UUID.randomUUID().toString())
                .name("Administrator")
                .email("admin@gmail.com")
                .gender("Male")
                .isVerified(true)
                .phoneNumber("09876543")
                .isDeleted(false)
                .isStudent(false)
                .build();

        userRepository.save(user);

        UserRole userRoleAdmin = UserRole.builder()
                .user(user)
                .role(roleAdmin)
                .build();

        userRoleRepository.save(userRoleAdmin);
    }

}
