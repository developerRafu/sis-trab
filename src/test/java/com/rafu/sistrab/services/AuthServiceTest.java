package com.rafu.sistrab.services;

import com.rafu.sistrab.domain.User;
import com.rafu.sistrab.helper.RegisterMockBuilder;
import com.rafu.sistrab.helper.TokenMockBuilder;
import com.rafu.sistrab.mappers.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@Slf4j
class AuthServiceTest {
    UserService userService;
    UserMapper userMapper;
    JWTUtil jwtUtil;
    AuthenticationManager authenticationManager;
    PasswordEncoder passwordEncoder;
    HttpServletRequest request;
    AuthService authService;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        userMapper = spy(Mappers.getMapper(UserMapper.class));
        jwtUtil = mock(JWTUtil.class);
        authenticationManager = mock(AuthenticationManager.class);
        passwordEncoder = mock(PasswordEncoder.class);
        request = mock(HttpServletRequest.class);
        authService = new AuthService(userService, userMapper, jwtUtil, authenticationManager, passwordEncoder, request);
    }

    @Test
    void shouldRegisterUser() {
        final var token = TokenMockBuilder.getDefaultToken();
        //Given I have a valid RegisterRequest
        final var registerRequest = RegisterMockBuilder.getDefaultMock();
        //When I call encoder
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        //And I call save
        when(userService.save(any())).thenAnswer(answer -> {
            updateId(answer);
            return answer.getArgument(0);
        });
        //And I call getToken
        when(jwtUtil.getToken(any())).thenReturn(token);
        //And I call registers
        final var result = authService.register(registerRequest);
        //Then I should have a valid TokenResponse
        assertNotNull(result);
        assertEquals(token.getToken(), result.getToken());
        assertEquals(token.getDuration(), result.getDuration());
        assertEquals(token.getType(), result.getType());
    }

    private static void updateId(InvocationOnMock answer) {
        try {
            for (Method declaredMethod : answer.getArgument(0).getClass().getDeclaredMethods()) {
                if(declaredMethod.getName().contains("setId")){
                    declaredMethod.invoke(answer.getArgument(0), 1L);
                }
            }
        } catch (Exception ex) {
            log.error("Error on updateId", ex);
        }
    }
}