package com.example.interlinked.user;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Testcontainers
class UserControllerTest {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16");
    
   @Value("${local.server.port}")
    int port;

    RestClient rest() {
        return RestClient.create("http://localhost:" + port);
    }
    
    @Test
    void canCreateAndRetrieveUser() {
    var req = new UserController.CreateUserRequest("Alice");
    var created = rest().post()
        .uri("/api/users")
        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
        .body(req)
        .retrieve()
        .body(User.class);

    var retrieved = rest().get()
        .uri("/api/users/{id}", created.getId())
        .retrieve()
        .body(User.class);

    assertThat(retrieved.getDisplayName()).isEqualTo("Alice");
    }

}