package com.vicheak.coreapi.api.user.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateUserDto(@NotBlank
                            @Size(min = 6, max = 30)
                            String name,
                            @NotBlank
                            String gender,
                            @NotBlank
                            @Email
                            String email,
                            @NotBlank
                            @Size(min = 8, max = 25)
                            String password,
                            @NotBlank
                            String phoneNumber,
                            @NotNull
                            Boolean isStudent,
                            String studentCardNo,
                            List<Integer> roleIds) {
}
