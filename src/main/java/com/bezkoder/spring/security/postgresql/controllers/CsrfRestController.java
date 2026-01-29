package com.bezkoder.spring.security.postgresql.controllers;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/csrf")
@CrossOrigin(
        origins = "http://localhost:4446",
        allowCredentials = "true"
)
public class CsrfRestController {

    @GetMapping
    public CsrfToken csrf(CsrfToken csrfToken) {
        return csrfToken;
    }
}
