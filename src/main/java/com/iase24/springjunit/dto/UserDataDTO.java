package com.iase24.springjunit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDataDTO {

    private Long id;

    private String login;

    private String email;
}
