package com.iase24.springjunit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/unsecured")
    public String unsecuredData() {
        return "unsecuredData";
    }

    @GetMapping("/secured")
    public String securedData() {
        return "securedData";
    }

    @GetMapping("/admin")
    public String adminData() {
        return "adminData";
    }

    @GetMapping("/info")
    public String infoData(Principal principal) {
        return principal.getName();
    }
}
