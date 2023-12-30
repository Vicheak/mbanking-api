package com.vicheak.coreapi.security;

import com.vicheak.coreapi.api.user.User;
import com.vicheak.coreapi.api.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmailAndIsDeletedFalse(username)
                .orElseThrow(
                        () -> {
                            log.error("Email has not been found!");
                            return new UsernameNotFoundException("User with this email is not found!");
                        }
                );

        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setUser(user);

        log.info("User email : {}", customUserDetails.getUsername());
        log.info("Authorities : {}", customUserDetails.getAuthorities());

        return customUserDetails;
    }
}
