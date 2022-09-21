package com.example.movie.repo;

import com.example.movie.models.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    List<Employee> findBySurnameContains(String surname);
}
