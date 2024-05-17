package com.vicheak.coreapi.api.auth.web;

import lombok.Builder;

@Builder
public record AuthDto(String tokenType,
                      String accessToken,
                      String refreshToken) {
}
