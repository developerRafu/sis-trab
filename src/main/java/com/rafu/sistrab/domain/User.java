package com.rafu.sistrab.domain;

import com.rafu.sistrab.domain.enums.Profile;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EqualsAndHashCode
@Table(name = "TAB_USER")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Column(unique = true)
  private String email;

  private String password;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable
  private Set<Profile> profiles = new HashSet<>();
}
