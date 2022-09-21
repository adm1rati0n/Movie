package com.example.movie.repo;

import com.example.movie.models.Genre;
import com.example.movie.models.Ticket;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TicketRepository extends CrudRepository<Ticket, Long> {
    List<Ticket> findBySeatContains(String seat);
}
