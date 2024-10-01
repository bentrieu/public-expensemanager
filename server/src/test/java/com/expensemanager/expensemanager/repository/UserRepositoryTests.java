package com.expensemanager.expensemanager.repository;

import com.expensemanager.expensemanager.model.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;


@DataJpaTest
@AutoConfigureTestDatabase
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void UserRepository_SaveUser_ReturnSavedUser() {
        UserEntity user = UserEntity.builder()
                .name("Khang")
                .asset(0)
                .profileImage("String")
                .email("email")
                .password("password")
                .build();

        UserEntity result = userRepository.save(user);

        Assertions.assertEquals(user, result);
    }

    @Test
    public void UserRepository_SaveUser_ReturnSavedUserUsingEmail() {
        String email = "email@gmail.com";
        UserEntity user = UserEntity.builder()
                .name("Khang")
                .asset(0)
                .profileImage("String")
                .email(email)
                .password("password")
                .build();

        userRepository.save(user);
        UserEntity result = userRepository.findByEmail(email).get();

        Assertions.assertEquals(user, result);
    }

    @Test
    public void UserRepository_SaveUser_NotReturnUserUsingWrongEmail() {
        String email = "email@gmail.com";
        String wrongEmail = "wrongEmail@gmail.com";
        UserEntity user = UserEntity.builder()
                .name("Khang")
                .asset(0)
                .profileImage("String")
                .email(email)
                .password("password")
                .build();

        userRepository.save(user);
        Optional<UserEntity> result = userRepository.findByEmail(wrongEmail);

        Assertions.assertEquals(Optional.empty(), result);
    }

    @Test
    public void UserRepository_SaveUser_UserShouldExistWithEmail() {
        String email = "email@gmail.com";
        UserEntity user = UserEntity.builder()
                .name("Khang")
                .asset(0)
                .profileImage("String")
                .email(email)
                .password("password")
                .build();

        userRepository.save(user);
        boolean result = userRepository.existsByEmail(email);

        Assertions.assertTrue(result);
    }
    @Test
    public void UserRepository_SaveUser_UserShouldNotExistWithWrongEmail() {
        String email = "email@gmail.com";
        String wrongEmail = "wrongEmail@gmail.com";
        UserEntity user = UserEntity.builder()
                .name("Khang")
                .asset(0)
                .profileImage("String")
                .email(email)
                .password("password")
                .build();

        userRepository.save(user);
        boolean result = userRepository.existsByEmail(wrongEmail);

        Assertions.assertFalse(result);
    }
}
