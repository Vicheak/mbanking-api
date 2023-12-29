package com.vicheak.coreapi.api.user.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;

@Builder
public record UserDto(String uuid,
                      String name,
                      String gender,
                      String email,
                      String phoneNumber,
                      Boolean isStudent,
                      @JsonInclude(value = JsonInclude.Include.NON_NULL)
                      String studentCardNo,
                      List<UserRoleDto> userRoles) {
}
