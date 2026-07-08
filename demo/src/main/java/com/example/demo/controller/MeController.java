package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/me")
@RequiredArgsConstructor
public class MeController {

    @GetMapping
    public String me(Authentication authentication) {
        return authentication.getName();
    }

}
