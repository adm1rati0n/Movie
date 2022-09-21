package com.example.movie.repo;

import com.example.movie.models.Film;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FilmRepository extends CrudRepository<Film, Long> {
    List<Film> findByNameContains(String name);
}
