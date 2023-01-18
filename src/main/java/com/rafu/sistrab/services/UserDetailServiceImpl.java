package com.rafu.sistrab.services;

import com.rafu.sistrab.domain.UserDetailsImpl;
import com.rafu.sistrab.errors.UserNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userService
                .findByEmail(username)
                .map(UserDetailsImpl::new)
                .orElseThrow(() -> new UserNotFound(username));
    }
}
