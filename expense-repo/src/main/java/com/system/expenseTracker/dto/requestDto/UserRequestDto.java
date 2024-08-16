package com.system.expenseTracker.dto.requestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotBlank(message = "username cannot be blank")
    private String username;
    @NotBlank(message = "email cannot be blank")
    private String email;
    @NotBlank(message = "Password field cannot be blank")
    @Size(min = 8, message = "Password must contain at least 8 characters")
    private String password;
}
