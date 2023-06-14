package com.example.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

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
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user0;

    @BeforeEach
    void setUp() {
        user0 = User.builder()
                .firstName("Test User 0")
                .lastName("Assist")
                .password("123456")
                .email("user0@cap.com")
                .role(Role.USER)
                .build();
    }

    // Test pour ajouter un utilisateur
    @Test
    @DisplayName("Add user")
    public void testAddUser() {
    
        // given 
        User user = User.builder()
                .firstName("Test User 1")
                .lastName("Assist")
                .password("123456")
                .email("v@cap.com")
                .role(Role.USER)
                .build();
                
        // when 
        User userAdded = userRepository.save(user);

        // then
        assertThat(userAdded).isNotNull();
        assertThat(userAdded.getId()).isGreaterThan(0L);

    }

    @DisplayName("Test pour obtenir la liste des utilisateurs ")
    @Test
    public void testFindAllUsers() {

        // given
        User user1 = User.builder()
                .firstName("Test User 1")
                .lastName("Assist")
                .password("123456")
                .email("v@cap.com")
                .role(Role.USER)
                .build();

        userRepository.save(user0);
        userRepository.save(user1);

	// when
        List<User> usuarios = userRepository.findAll();

        // then
        assertThat(usuarios).isNotNull();
        assertThat(usuarios.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Test pour obtenir un utilizateur à travers de son ID")
    public void findUserById() {

        // given
        userRepository.save(user0);

        // when
        User user = userRepository.findById(user0.getId()).get();

        // then
        assertThat(user.getId()).isNotEqualTo(0L);

    }

    @Test
    @DisplayName("Test pour actualizer un utilizateur")
    public void testUpdateUser() {

        // given
        userRepository.save(user0);

        // when
        User userGuardado = userRepository.findByEmail(user0.getEmail()).get();

        userGuardado.setLastName("Pierre");
        userGuardado.setFirstName("Jhone");
        userGuardado.setEmail("jp@cap.com");

        User userUpdated = userRepository.save(userGuardado);

        // then

        assertThat(userUpdated.getEmail()).isEqualTo("jp@cap.com");
        assertThat(userUpdated.getFirstName()).isEqualTo("Jhone");

    }

    @DisplayName("Test para eliminer un utilizateur")
    @Test
    public void testDeleteUser () {

        // given
        userRepository.save(user0);

        // when
        userRepository.delete(user0);
        Optional<User> optionalUser = userRepository.findByEmail(user0.getEmail());

        // then
        assertThat(optionalUser).isEmpty();
    }
}
