package com.bezkoder.spring.security.postgresql.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomerRequest(
        @NotBlank@NotBlank(message = "First name cannot be empty")
        @Size(min = 2, max = 100, message = "First name must be between 2 and 100 characters")
        String firstName,

        @NotBlank(message = "Last name cannot be empty")
        @Size(min = 2, max = 100, message = "Last name must be between 2 and 100 characters")
        String lastName,

        @NotBlank(message = "Phone number cannot be empty")
        @Size(min = 10, max = 30, message = "Phone number must be between 10 and 30 characters")
        String phone,

        @NotBlank(message = "Email cannot be empty")
        @Email(message = "Email must be a valid email address")
        @Size(min = 5, max = 150, message = "Email must be between 5 and 150 characters")
        String email
) {}