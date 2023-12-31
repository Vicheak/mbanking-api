package com.vicheak.coreapi.api.authority;

import com.vicheak.coreapi.api.authority.web.AuthDto;
import com.vicheak.coreapi.api.authority.web.LoginDto;
import com.vicheak.coreapi.api.authority.web.RefreshTokenDto;

public interface AuthService {

    AuthDto login(LoginDto loginDto);

    AuthDto refresh(RefreshTokenDto refreshTokenDto);

}
