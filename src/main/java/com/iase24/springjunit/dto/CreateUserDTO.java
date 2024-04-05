package com.iase24.springjunit.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserDTO {

    private String username;
    private String email;
    private String password;
}
