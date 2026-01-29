package com.bezkoder.spring.security.postgresql.services;

import com.bezkoder.spring.security.postgresql.payload.request.CustomerRequest;
import com.bezkoder.spring.security.postgresql.payload.response.CustomerResponse;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    List<CustomerResponse> findAll();
    CustomerResponse findById(UUID id);

    CustomerResponse create(CustomerRequest request);
    CustomerResponse update(UUID id, CustomerRequest request);

    void deleteById(UUID id);
}
