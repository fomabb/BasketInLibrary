package com.iase24.springjunit.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationUserDTO {

    private Long id;

    private String username;

    private String email;

    private String password;

    private String confirmPassword;
}
