package com.example.movie.repo;

import com.example.movie.models.Genre;
import com.example.movie.models.Hall;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HallRepository extends CrudRepository<Hall, Long> {
    List<Hall> findByNameContains(String name);
}
