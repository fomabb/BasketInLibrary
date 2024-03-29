package com.iase24.springjunit.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDTO {

    Long id;
    String login;
    String email;
}
