package com.vicheak.coreapi.api.user.web;

import com.vicheak.coreapi.api.user.UserService;
import com.vicheak.coreapi.base.BaseApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @PreAuthorize("hasAuthority('SCOPE_user:read')")
    @GetMapping
    public BaseApi<?> findAll() {

        var usersDto = userService.findAll();

        return BaseApi.builder()
                .isSuccess(true)
                .code(HttpStatus.OK.value())
                .message("Users have been found")
                .timestamp(LocalDateTime.now())
                .payload(usersDto)
                .build();
    }

    @PreAuthorize("hasAuthority('SCOPE_user:write')")
    @PostMapping
    public BaseApi<?> createNew(@Valid @RequestBody CreateUserDto createUserDto) {

        UserDto newUser = userService.createNew(createUserDto);

        return BaseApi.builder()
                .isSuccess(true)
                .code(HttpStatus.CREATED.value())
                .message("User has been created successfully")
                .timestamp(LocalDateTime.now())
                .payload(newUser)
                .build();
    }

    @PreAuthorize("hasAuthority('SCOPE_user:read')")
    @GetMapping("/{uuid}")
    public BaseApi<?> findByUuid(@PathVariable String uuid) {

        UserDto user = userService.findByUuid(uuid);

        return BaseApi.builder()
                .isSuccess(true)
                .code(HttpStatus.OK.value())
                .message("User has been found")
                .timestamp(LocalDateTime.now())
                .payload(user)
                .build();
    }

    @PreAuthorize("hasAuthority('SCOPE_user:delete')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{uuid}")
    public void deleteByUuid(@PathVariable String uuid) {
        userService.deleteByUuid(uuid);
    }

    @PreAuthorize("hasAuthority('SCOPE_user:update')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{uuid}/disable")
    public void disableByUuid(@PathVariable String uuid) {
        userService.disableByUuid(uuid);
    }

    @PreAuthorize("hasAuthority('SCOPE_user:update')")
    @PutMapping("/{uuid}")
    public BaseApi<?> updateByUuid(@PathVariable String uuid,
                                   @Valid @RequestBody UpdateUserDto updateUserDto) {
        UserDto updatedUser = userService.updateByUuid(uuid, updateUserDto);

        return BaseApi.builder()
                .isSuccess(true)
                .code(HttpStatus.OK.value())
                .message("User has been updated successfully")
                .timestamp(LocalDateTime.now())
                .payload(updatedUser)
                .build();
    }

}
