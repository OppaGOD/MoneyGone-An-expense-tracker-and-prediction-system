package com.system.expenseTracker.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.List;

@Component
public class Util {
    public String getFirstErrorMessage(BindingResult bindingResult) {
        List<String> fieldOrder = Arrays.asList("name", "username", "email", "password");
        for (String field : fieldOrder) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors(field);
            if (!fieldErrors.isEmpty()) {
                return fieldErrors.get(0).getDefaultMessage();
            }
        }
        return "Invalid input.";
    }
}
