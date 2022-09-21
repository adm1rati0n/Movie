package com.example.movie.repo;

import com.example.movie.models.Genre;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GenreRepository extends CrudRepository<Genre, Long> {
    List<Genre> findByNameContains(String name);
}
