package com.rafu.sistrab.domain.enums;

import lombok.Getter;

import java.util.Set;

@Getter
public enum Profile {
  ADMIN("ROLE_ADMIN"),
  USER("ROLE_USER"),
  CLIENT("ROLE_CLIENT");
  private final String role;

  Profile(final String role) {
    this.role = role;
  }

  public static boolean isAdmin(final Set<Profile> profiles) {
    return profiles.contains(Profile.ADMIN);
  }
}
