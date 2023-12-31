package com.vicheak.coreapi.api.authority.web;

import com.vicheak.coreapi.api.authority.AuthService;
import com.vicheak.coreapi.base.BaseApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthRestController {

    private final AuthService authService;

    @PostAuthorize("permitAll()")
    @PostMapping("/login")
    public BaseApi<?> login(@Valid @RequestBody LoginDto loginDto){

        AuthDto authDto = authService.login(loginDto);

        return BaseApi.builder()
                .isSuccess(true)
                .code(HttpStatus.OK.value())
                .message("You have logged in successfully!")
                .timestamp(LocalDateTime.now())
                .payload(authDto)
                .build();
    }

    @PostAuthorize("permitAll()")
    @PostMapping("/refresh")
    public BaseApi<?> refresh(@Valid @RequestBody RefreshTokenDto refreshTokenDto){

        AuthDto authDto = authService.refresh(refreshTokenDto);

        return BaseApi.builder()
                .isSuccess(true)
                .code(HttpStatus.OK.value())
                .message("Access Token has been renewed!")
                .timestamp(LocalDateTime.now())
                .payload(authDto)
                .build();
    }

}
