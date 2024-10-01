package com.expensemanager.expensemanager.repository;

import com.expensemanager.expensemanager.model.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase
public class RoleRepositoryTests {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void RoleRepository_SaveRole_ReturnSavedRole() {
        Role test = com.expensemanager.expensemanager.model.Role.builder()
                .name("USER")
                .build();

        Role result = roleRepository.save(test);

        Assertions.assertEquals(test, result);
    }

    @Test
    public void RoleRepository_FindByName_ReturnRole() {
        String name = "USER";
        Role test = com.expensemanager.expensemanager.model.Role.builder()
                .name(name)
                .build();

        roleRepository.save(test);

        Role result = roleRepository.findByName(name).get();

        Assertions.assertEquals(test, result);
    }
}
