package com.example.movie.repo;

import com.example.movie.models.Genre;
import com.example.movie.models.Studio;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudioRepository extends CrudRepository<Studio, Long> {
    List<Studio> findByNameContains(String name);
}
