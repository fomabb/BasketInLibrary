package com.iase24.springjunit.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateUserDTO {

    String login;
    String email;
    String password;
}
