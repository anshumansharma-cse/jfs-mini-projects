package com.example.employeemanagementweb.controller;

import com.example.employeemanagementweb.model.Role;
import com.example.employeemanagementweb.model.User;
import com.example.employeemanagementweb.repository.RoleRepository;
import com.example.employeemanagementweb.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            Role adminRole = roleRepository.findByName("ADMIN");
            if (adminRole == null) {
                adminRole = roleRepository.save(new Role("ADMIN"));
            }
            Role userRole = roleRepository.findByName("USER");
            if (userRole == null) {
                userRole = roleRepository.save(new Role("USER"));
            }

            User admin = new User("admin", passwordEncoder.encode("admin123"));
            admin.setRoles(new HashSet<>(java.util.List.of(adminRole)));
            userRepository.save(admin);

            User user = new User("user", passwordEncoder.encode("user123"));
            user.setRoles(new HashSet<>(java.util.List.of(userRole)));
            userRepository.save(user);
        }
    }
}
