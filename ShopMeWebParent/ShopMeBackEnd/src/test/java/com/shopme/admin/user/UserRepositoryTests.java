package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

   @Autowired
   private UserRepository userRepository;

   @Autowired
   private TestEntityManager entityManager;


    @Test
    public void testCreateNewUserWithOneRole() {
        Role roleAdmin = entityManager.find(Role.class, 1);
        User user2 = new User("romanciuc1@gmail.com", "mercedes", "Adriano", "Celentano");
        user2.addRole(roleAdmin);

        User savedUser = userRepository.save(user2);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateNewUserWithTwoRoles() {
        User userAndreea = new User("bradis@gmail.com", "bradis11", "Bradis", "Andreea");
        Role roleEditor = new Role(3);
        Role roleAssistant = new Role(5);

        userAndreea.addRole(roleEditor);
        userAndreea.addRole(roleAssistant);

        User savedUser = userRepository.save(userAndreea);

        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUsers() {
        Iterable<User> listUsers = userRepository.findAll();
        listUsers.forEach(user -> System.out.println(user));

    }

    @Test
    public void testGetUserById() {
       User userAndr = userRepository.findById(4).get();
        System.out.println(userAndr);
       assertThat(userAndr).isNotNull();

    }

    @Test
    public void testUpdateUserDetails() {
        User userAndr = userRepository.findById(1).get();
        userAndr.setEnabled(true);
        userAndr.setEmail("bradibradi@gmail.com");

        userRepository.save(userAndr);
    }

}
