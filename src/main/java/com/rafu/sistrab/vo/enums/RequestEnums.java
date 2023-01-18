package com.rafu.sistrab.vo.enums;

import lombok.Getter;

@Getter
public enum RequestEnums {
  AUTHORIZATION_HEADER("Authorization");
  private final String value;

  RequestEnums(final String value) {
    this.value = value;
  }
}
