package com.bezkoder.spring.security.postgresql.repository;


import com.bezkoder.spring.security.postgresql.payload.request.CustomerRequest;
import com.bezkoder.spring.security.postgresql.payload.response.CustomerResponse;
import com.bezkoder.spring.security.postgresql.services.CustomerService;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CustomerRepository implements CustomerService {

    private final JdbcClient jdbcClient;

    public CustomerRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<CustomerResponse> findAll() {
        String sql = """
                SELECT
                    id_customer,
                    first_name,
                    last_name,
                    phone,
                    email
                FROM customer
                """;

        return jdbcClient.sql(sql)
                .query(CustomerResponse.class)
                .list();
    }

    @Override
    public CustomerResponse findById(UUID id) {
        String sql = """
                SELECT
                    id_customer,
                    first_name,
                    last_name,
                    phone,
                    email
                FROM customer
                WHERE id_customer = :id
                """;

        Optional<CustomerResponse> customer = jdbcClient.sql(sql)
                .param("id", id)
                .query(CustomerResponse.class)
                .optional();

        return customer.orElse(null);
    }

    @Override
    public CustomerResponse create(CustomerRequest request) {
        String sql = """
                INSERT INTO customer(
                    id_customer,
                    first_name,
                    last_name,
                    phone,
                    email
                )
                VALUES (?,?,?,?,?)
                """;

        UUID id = UUID.randomUUID();

        int updated = jdbcClient.sql(sql)
                .params(List.of(
                        id,
                        request.firstName(),
                        request.lastName(),
                        request.phone(),
                        request.email()
                ))
                .update();

        if (updated != 0) {
            return new CustomerResponse(
                    id,
                    request.firstName(),
                    request.lastName(),
                    request.phone(),
                    request.email()
            );
        }

        return null;
    }

    @Override
    public CustomerResponse update(UUID id, CustomerRequest request) {
        String sql = """
                UPDATE customer SET
                    first_name = ?,
                    last_name  = ?,
                    phone      = ?,
                    email      = ?
                WHERE id_customer = ?
                """;

        int updated = jdbcClient.sql(sql)
                .params(List.of(
                        request.firstName(),
                        request.lastName(),
                        request.phone(),
                        request.email(),
                        id
                ))
                .update();

        if (updated != 0) {
            return new CustomerResponse(
                    id,
                    request.firstName(),
                    request.lastName(),
                    request.phone(),
                    request.email()
            );
        }

        return null;
    }

    @Override
    public void deleteById(UUID id) {
        jdbcClient.sql("DELETE FROM customer WHERE id_customer = :id")
                .param("id", id)
                .update();
    }
}
