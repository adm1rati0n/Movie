package com.example.movie.repo;

import com.example.movie.models.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    List<Customer> findBySurnameContains(String surname);
    Customer findByUser_Username(String username);
}
