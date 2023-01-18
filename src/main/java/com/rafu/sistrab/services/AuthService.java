package com.rafu.sistrab.services;

import com.rafu.sistrab.domain.enums.Profile;
import com.rafu.sistrab.errors.UserNotFound;
import com.rafu.sistrab.mappers.UserMapper;
import com.rafu.sistrab.rest.dto.AuthenticationRequest;
import com.rafu.sistrab.rest.dto.RegisterRequest;
import com.rafu.sistrab.rest.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.rafu.sistrab.domain.User;

import java.util.Set;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthService {
    private final UserService userService;
    private final UserMapper userMapper;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public TokenResponse register(final RegisterRequest request) {
        final var user = userMapper.toUser(request);
        user.setProfiles(Set.of(Profile.USER));
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userService.save(user);
        return jwtUtil.getToken(user.getEmail());
    }

    public TokenResponse authenticate(final AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        final var user = userService.findByEmail(request.getEmail());
        return user.map(User::getEmail)
                .map(jwtUtil::getToken)
                .orElseThrow(() -> new UserNotFound(request.getEmail()));
    }
}
