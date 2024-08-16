package com.system.expenseTracker.controller;

import com.system.expenseTracker.dto.requestDto.UserRequestDto;
import com.system.expenseTracker.model.User;
import com.system.expenseTracker.service.UserService;
import com.system.expenseTracker.service.other.CategoryInitService;
import com.system.expenseTracker.service.other.EmailService;
import com.system.expenseTracker.util.Util;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    EmailService emailService;
    @Autowired
    CategoryInitService categoryInitService;
    @Autowired
    Util util;

    @GetMapping("/sign-up")
    public String getSignUp(Model model, @ModelAttribute("errorMsg") String errorMsg){
        model.addAttribute("dto", new UserRequestDto());
        model.addAttribute("errorMsg", errorMsg);
        return "signUp";
    }

    @PostMapping("/sign-up")
    public String signUpData(Model model, RedirectAttributes redirectAttributes, @ModelAttribute @Valid UserRequestDto userRequestDto, BindingResult bindingResult){
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("dto", userRequestDto);
//            return "signUp";
//        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("dto", userRequestDto);
            String errorMsg = util.getFirstErrorMessage(bindingResult);
            model.addAttribute("errorMsg", errorMsg);
            return "signUp";
        }

        try {
            User registeredUser = userService.saveUserToTable(userRequestDto);
            if (registeredUser != null) {
                categoryInitService.associateDefaultCategoriesWithNewUser(registeredUser);

                // Sending the email after successful sign up
                String subject = "Expense Tracking System";
                String message = "Thank you for signing up.";
                emailService.sendEmail(userRequestDto.getEmail(), subject, message);
                return "login";
            }

        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("errorMsg", "Username Taken.");
            return "redirect:/sign-up";
        }
        return "redirect:/sign-up";
    }



//    @PostMapping(value = "/sign-up", consumes = "application/x-www-form-urlencoded")
//    public String signUp(@RequestBody @Valid UserRequestDto userRequestDto,RedirectAttributes redirectAttributes){
//        String signUpMessage = userService.signUpUser(userRequestDto);
//
//        if (signUpMessage.equals("Signup successful. Please log in.")) {
//            redirectAttributes.addFlashAttribute("successMsg", signUpMessage);
//            return "redirect:/login";
//        } else {
//            redirectAttributes.addFlashAttribute("errorMsg", signUpMessage);
//            return "redirect:/sign-up";
//        }
//    }
}