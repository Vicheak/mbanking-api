package com.vicheak.coreapi.api.authority;

import com.vicheak.coreapi.api.authority.web.AuthDto;
import com.vicheak.coreapi.api.authority.web.LoginDto;
import com.vicheak.coreapi.api.authority.web.RefreshTokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final JwtEncoder jwtEncoder;

    private JwtEncoder refreshTokenJwtEncoder;

    @Autowired
    public void setRefreshTokenJwtEncoder(@Qualifier("refreshTokenJwtEncoder") JwtEncoder refreshTokenJwtEncoder) {
        this.refreshTokenJwtEncoder = refreshTokenJwtEncoder;
    }

    @Override
    public AuthDto login(LoginDto loginDto) {
        Authentication auth = new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password());

        auth = daoAuthenticationProvider.authenticate(auth);

        log.info("Authentication Name : {}", auth.getName());

        Instant now = Instant.now();

        String scope = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                //.filter(authority -> !authority.startsWith("ROLE_"))
                .collect(Collectors.joining(" "));

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject("access-resources")
                .expiresAt(now.plus(1, ChronoUnit.SECONDS))
                .issuer("self")
                .issuedAt(now)
                .id(auth.getName())
                .claim("scope", scope)
                .build();

        JwtClaimsSet refreshTokenJwtClaimsSet = JwtClaimsSet.builder()
                .subject("refresh-token")
                .expiresAt(now.plus(2, ChronoUnit.DAYS))
                .issuer("self")
                .issuedAt(now)
                .id(auth.getName())
                .claim("scope", scope)
                .build();

        String accessToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
        String refreshToken = refreshTokenJwtEncoder.encode(JwtEncoderParameters.from(refreshTokenJwtClaimsSet)).getTokenValue();

        return AuthDto.builder()
                .tokenType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthDto refresh(RefreshTokenDto refreshTokenDto) {

        Authentication auth = new BearerTokenAuthenticationToken(refreshTokenDto.refreshToken());

        auth = jwtAuthenticationProvider.authenticate(auth);

        /*log.info("Auth Name : {}", auth.getName());
        log.info("Auth Principal : {}", auth.getPrincipal());
        log.info("Auth Authorities : {}", auth.getAuthorities());*/

        Instant now = Instant.now();

        Jwt jwt = (Jwt) auth.getPrincipal();

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject("access-resources")
                .expiresAt(now.plus(1, ChronoUnit.SECONDS))
                .issuer("self")
                .issuedAt(now)
                .id(jwt.getId())
                .claim("scope", jwt.getClaimAsString("scope"))
                .build();

        JwtClaimsSet refreshTokenJwtClaimsSet = JwtClaimsSet.builder()
                .subject(auth.getName())
                .expiresAt(now.plus(2, ChronoUnit.DAYS))
                .issuer("self")
                .issuedAt(now)
                .id(jwt.getId())
                .claim("scope", jwt.getClaimAsString("scope"))
                .build();

        String accessToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
        String refreshToken = refreshTokenJwtEncoder.encode(JwtEncoderParameters.from(refreshTokenJwtClaimsSet)).getTokenValue();

        return AuthDto.builder()
                .tokenType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
