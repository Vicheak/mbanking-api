package com.vicheak.coreapi.api.authority.web;

import com.vicheak.coreapi.api.authority.Role;
import com.vicheak.coreapi.api.authority.RoleRepository;
import com.vicheak.coreapi.base.BaseApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/authorities")
@RequiredArgsConstructor
public class AuthorityRestController {

    private final RoleRepository roleRepository;
    @GetMapping
    public BaseApi<?> findAuthorities(){

        Iterable<Role> roles = roleRepository.findAll();

        return BaseApi.builder()
                .isSuccess(true)
                .code(HttpStatus.OK.value())
                .message("Roles has been fetched!")
                .timestamp(LocalDateTime.now())
                .payload(roles)
                .build();
    }

}
