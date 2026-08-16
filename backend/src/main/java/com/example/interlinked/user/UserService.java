package com.example.interlinked.user;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public User createUser(String displayName) {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setDisplayName(displayName);
        user.setCreatedAt(LocalDateTime.now());
        return repo.save(user);
    }

    public User getUser(UUID id) {
        return repo.findById(id).orElseThrow();
    }
}