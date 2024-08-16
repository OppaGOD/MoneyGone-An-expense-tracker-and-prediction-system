package com.system.expenseTracker.service.impl;

import com.system.expenseTracker.dto.requestDto.UserRequestDto;
import com.system.expenseTracker.dto.responseDto.UserResponseDto;
import com.system.expenseTracker.model.User;
import com.system.expenseTracker.repo.RoleRepo;
import com.system.expenseTracker.repo.UserRepo;
import com.system.expenseTracker.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    RoleRepo roleRepo;
    @Override
    public User saveUserToTable(UserRequestDto dto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User existingUser = userRepo.getUserByUsername(dto.getUsername());
        User existingUserbyEmail = userRepo.findByEmail(dto.getEmail());
        if (existingUser != null || existingUserbyEmail!=null){
            return null;
        }
        else {
            User newUser = new User();

            newUser.setName(dto.getName());
            newUser.setUsername(dto.getUsername());
            newUser.setPassword(encoder.encode(dto.getPassword()));
            newUser.setEmail(dto.getEmail());
            newUser.setRoles(roleRepo.getUserRole("USER"));

            User savedUser = userRepo.save(newUser);

            return savedUser;
        }
    }

    @Override
    public UserResponseDto findUserByUserName(String username) {
        return new UserResponseDto(userRepo.getUserByUsername(username));
    }

    @Override
    public String signUpUser(UserRequestDto userRequestDto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User existingUser = userRepo.getUserByUsername(userRequestDto.getUsername());
        User existingUserByEmail = userRepo.findByEmail(userRequestDto.getEmail());

        if (existingUser != null) {
            log.info("Username is already taken.");
            return "Username is already taken.";
        }

        if (existingUserByEmail != null) {
            log.info("Email is already registered with another account.");
            return "Email is already registered with another account.";
        }

        User newUser = new User();
        newUser.setName(userRequestDto.getName());
        newUser.setUsername(userRequestDto.getUsername());
        newUser.setPassword(encoder.encode(userRequestDto.getPassword()));
        newUser.setEmail(userRequestDto.getEmail());
        newUser.setRoles(roleRepo.getUserRole("USER"));

        userRepo.save(newUser);
        return "Signup successful. Please log in.";
    }
}
