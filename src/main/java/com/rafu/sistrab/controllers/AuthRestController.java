package com.rafu.sistrab.controllers;

import com.rafu.sistrab.rest.dto.AuthenticationRequest;
import com.rafu.sistrab.rest.dto.RegisterRequest;
import com.rafu.sistrab.rest.dto.TokenResponse;
import com.rafu.sistrab.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthRestController {
  private final AuthService service;

  @PostMapping("/register")
  public ResponseEntity<TokenResponse> register(@RequestBody RegisterRequest request) {
    return ResponseEntity.ok(service.register(request));
  }

  @PostMapping("/authenticate")
  public ResponseEntity<TokenResponse> authenticate(@RequestBody AuthenticationRequest request) {
    return ResponseEntity.ok(service.authenticate(request));
  }
}
