package com.example.movie.repo;

import com.example.movie.models.Genre;
import com.example.movie.models.MainActor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MainActorRepository extends CrudRepository<MainActor, Long> {
    List<MainActor> findBySurnameContains(String surname);
}
