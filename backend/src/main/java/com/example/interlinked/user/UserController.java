package com.example.interlinked.user;

import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    public record CreateUserRequest(String displayName) {}

    @PostMapping
    public User create(@RequestBody CreateUserRequest req) {
        return service.createUser(req.displayName());
    }

    @GetMapping("/{id}")
    public User get(@PathVariable UUID id) {
        return service.getUser(id);
    }
}