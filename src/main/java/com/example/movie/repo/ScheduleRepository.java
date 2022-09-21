package com.example.movie.repo;

import com.example.movie.models.Schedule;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScheduleRepository extends CrudRepository<Schedule, Long> {
    List<Schedule> findByDateContains(String date);
}
