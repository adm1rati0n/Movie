package com.example.movie.repo;

import com.example.movie.models.Genre;
import com.example.movie.models.Producer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProducerRepository extends CrudRepository<Producer, Long> {
    List<Producer> findBySurnameContains(String surname);
}
