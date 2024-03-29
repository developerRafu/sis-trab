package com.rafu.sistrab.configs;

import com.rafu.sistrab.rest.dto.RegisterRequest;
import com.rafu.sistrab.rest.dto.TokenResponse;
import com.rafu.sistrab.services.AuthService;
import java.math.BigDecimal;
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
        RegisterRequest.builder()
            .name("first user")
            .email("user@mail.com")
            .password("123")
            .taxa(BigDecimal.valueOf(70.0).toString())
            .horasMax(BigDecimal.valueOf(4).toString())
            .build();
    return authService.register(request);
  }
}
