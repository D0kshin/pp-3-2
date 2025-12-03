package ru.kata.spring.boot_security.demo.loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserDao userRepository;
    private final RoleDao roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(UserDao userRepository,  RoleDao roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) throws Exception {
        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");


        roleRepository.saveAll(Arrays.asList(userRole, adminRole));

        User user = new User();
        user.setUsername("user");
        user.setFirstName("FirstUserName");
        user.setLastName("LastUserName");
        user.setPassword(passwordEncoder.encode("123"));
        user.setRoles(List.of(userRole));

        User admin = new User();
        admin.setUsername("admin");
        admin.setFirstName("FirstAdminName");
        admin.setLastName("LastAdminName");
        admin.setPassword(passwordEncoder.encode("123"));
        admin.setRoles(List.of(adminRole));

        userRepository.saveAll(Arrays.asList(user, admin));
    }

}