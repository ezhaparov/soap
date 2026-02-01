package com.bezkoder.spring.security.postgresql.controllers;


import com.bezkoder.spring.security.postgresql.payload.request.CustomerRequest;
import com.bezkoder.spring.security.postgresql.payload.response.CustomerResponse;
import com.bezkoder.spring.security.postgresql.payload.response.CustomerXmlDto;
import com.bezkoder.spring.security.postgresql.payload.response.CustomersXmlResponseJaxb;
import com.bezkoder.spring.security.postgresql.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(
        origins = "http://localhost:4446",
        allowCredentials = "true"
)
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }


    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<CustomerResponse> customers = service.findAll();

        if (customers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(customers, HttpStatus.OK);
    }


    @GetMapping(value = "/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CustomerResponse>> getAllCustomersJson() {
        List<CustomerResponse> customers = service.findAll();

        if (customers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(customers, HttpStatus.OK);

    }

    @GetMapping(value = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<CustomersXmlResponseJaxb> getAllCustomersXml() {

        List<CustomerResponse> customers = service.findAll();


        if (customers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        CustomersXmlResponseJaxb  response  = new CustomersXmlResponseJaxb();
        response.setCustomers(customers.stream()
                .map(this::toXmlDto)
                .collect(Collectors.toList()));

        return new ResponseEntity<>(response, HttpStatus.OK);

    }



    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable("id") UUID id) {
        CustomerResponse customer = service.findById(id);

        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @GetMapping(value = "/{id}/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponse> getCustomerByIdJson(@PathVariable("id") UUID id) {
        CustomerResponse customer = service.findById(id);

        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping(value = "/{id}/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<CustomerXmlDto> getCustomerByIdXml(@PathVariable("id") UUID id) {
        CustomerResponse customer = service.findById(id);

        if (customer != null) {
            return new ResponseEntity<>(toXmlDto(customer), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    private CustomerXmlDto toXmlDto(CustomerResponse r) {
        return new CustomerXmlDto(
                r.idCustomer(),
                r.firstName(),
                r.lastName(),
                r.phone(),
                r.email()
        );
    }



    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest request) {
        CustomerResponse customer = service.create(request);

        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable("id") UUID id,
                                                   @Valid @RequestBody CustomerRequest request) {
        CustomerResponse customer = service.update(id, request);

        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable("id") UUID id) {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
