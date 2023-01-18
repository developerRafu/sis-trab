package com.rafu.sistrab.rest.dto;

import lombok.Getter;

@Getter
public enum TokenEnum {
  BEARER("Bearer ");
  private final String value;

  TokenEnum(final String value) {
    this.value = value;
  }
}
