package com.vicheak.coreapi.api.user.web;

import com.vicheak.coreapi.api.user.UserService;
import com.vicheak.coreapi.base.BaseApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

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

    @GetMapping("/{uuid}")
    public BaseApi<?> findByUuid(@PathVariable String uuid){

        UserDto user = userService.findByUuid(uuid);

        return BaseApi.builder()
                .isSuccess(true)
                .code(HttpStatus.OK.value())
                .message("User has been found")
                .timestamp(LocalDateTime.now())
                .payload(user)
                .build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{uuid}")
    public void deleteByUuid(@PathVariable String uuid){
        userService.deleteByUuid(uuid);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{uuid}/disable")
    public void disableByUuid(@PathVariable String uuid){
        userService.disableByUuid(uuid);
    }

}
