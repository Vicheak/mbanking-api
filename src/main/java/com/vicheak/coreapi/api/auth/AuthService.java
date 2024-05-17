package com.vicheak.coreapi.api.auth;

import com.vicheak.coreapi.api.auth.web.AuthDto;
import com.vicheak.coreapi.api.auth.web.LoginDto;
import com.vicheak.coreapi.api.auth.web.RefreshTokenDto;

public interface AuthService {

    AuthDto login(LoginDto loginDto);

    AuthDto refresh(RefreshTokenDto refreshTokenDto);

}
