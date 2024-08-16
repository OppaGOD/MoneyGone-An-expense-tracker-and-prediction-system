package com.system.expenseTracker.configuration;

import com.system.expenseTracker.model.Role;
import com.system.expenseTracker.model.User;
import com.system.expenseTracker.repo.RoleRepo;
import com.system.expenseTracker.repo.UserRepo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
@Configuration
@RequiredArgsConstructor
public class InitDbConfig {
    private final RoleRepo roleRepo;
    private final UserRepo userRepo;
    @PostConstruct
    public void doEntries(){
        if (roleRepo.findAll().size()==0){

            Role userRole = new Role();
            userRole.setName("USER");
            Role savedUserRole =  roleRepo.save(userRole);

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            User nonAdminUser = new User();
            nonAdminUser.setName("user");
            nonAdminUser.setUsername("globalUser");
            nonAdminUser.setPassword(encoder.encode("globalUser"));
            nonAdminUser.setRoles(Arrays.asList(savedUserRole));
            userRepo.save(nonAdminUser);
        }
    }
}
