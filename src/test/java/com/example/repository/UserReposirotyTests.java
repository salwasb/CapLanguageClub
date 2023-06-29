package com.example.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.user.Role;
import com.example.user.User;
import com.example.user.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserReposirotyTests {

    @Autowired
    private UserRepository userRepository;

    private User user0;

    @BeforeEach
    void setUp(){
        user0 = User.builder()
        .firstName("Test 0")
        .lastName("bbb")
        .password("1234")
        .email("gyyy@cap.com")
        .role(Role.USER)
        .build();
    }
    @Test
    @DisplayName("Test add user")
    public void testAddUser(){
        User user = User.builder()
        .firstName("Test 1")
        .lastName("kkk")
        .password("12346")
        .email("llll@cap.com")
        .role(Role.USER)
        .build();
    
    User usserAdded = userRepository.save(user);

    assertThat(usserAdded).isNotNull();
    assertThat(usserAdded.getId()).isGreaterThan(0L);

    }

    @Test
    @DisplayName("test lista  usuarios")
    public void testFindAllUsers(){

           User user1 = User.builder()
        .firstName("Test 1")
        .lastName("kkk")
        .password("12346")
        .email("llll@cap.com")
        .role(Role.USER)
        .build();

        userRepository.save(user0);
        userRepository.save(user1);

        List<User> usuarios = userRepository.findAll();

        assertThat(usuarios).isNotNull();
        assertThat(usuarios.size()).isEqualTo(2);
    }
}
