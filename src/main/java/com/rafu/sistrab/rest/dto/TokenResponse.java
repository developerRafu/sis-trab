package com.rafu.sistrab.rest.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenResponse {
  private String type;
  private String token;
  private String duration;
}
