package com.rafu.sistrab.errors;

public class UserNotFound extends RuntimeException {
  private final String email;

  public UserNotFound(final String email) {
    this.email = email;
  }
}
