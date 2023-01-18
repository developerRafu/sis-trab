package com.rafu.sistrab.configs;

import com.rafu.sistrab.rest.dto.RegisterRequest;
import com.rafu.sistrab.rest.dto.TokenResponse;
import com.rafu.sistrab.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DbConfig {
  private final AuthService authService;

  @Bean
  public TokenResponse saveFirstUser() {
    final var request =
        RegisterRequest.builder().name("first user").email("user@mail.com").password("123").build();
    return authService.register(request);
  }
}
