package com.rafu.sistrab.services;

import com.rafu.sistrab.domain.User;
import com.rafu.sistrab.domain.enums.Profile;
import com.rafu.sistrab.errors.UserNotAvailableException;
import com.rafu.sistrab.repositories.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
  private final UserRepository repository;

  public User save(final User user) {
    if (Profile.isAdmin(user.getProfiles())) {
      throw new UserNotAvailableException();
    }
    return repository.save(user);
  }

  public Optional<User> findByEmail(final String email) {
    return repository.findByEmail(email);
  }
}
