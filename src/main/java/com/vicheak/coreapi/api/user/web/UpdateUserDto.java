package com.vicheak.coreapi.api.user.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UpdateUserDto(@NotBlank String name,
                            @NotBlank String gender,
                            @NotNull Boolean isStudent,
                            String studentCardNo,
                            @NotNull List<Integer> roleIds) {
}
