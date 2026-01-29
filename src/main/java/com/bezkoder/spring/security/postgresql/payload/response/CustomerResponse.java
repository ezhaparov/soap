package com.bezkoder.spring.security.postgresql.payload.response;

import java.util.UUID;

public record CustomerResponse (
        UUID idCustomer,
        String firstName,
        String lastName,
        String phone,
        String email
) {}
