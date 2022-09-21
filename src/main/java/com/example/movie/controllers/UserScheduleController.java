package com.example.movie.controllers;

import com.example.movie.models.Customer;
import com.example.movie.models.Producer;
import com.example.movie.models.Schedule;
import com.example.movie.models.Ticket;
import com.example.movie.repo.CustomerRepository;
import com.example.movie.repo.ScheduleRepository;
import com.example.movie.repo.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@PreAuthorize("hasAnyAuthority('CUSTOMER')")
public class UserScheduleController {
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    TicketRepository ticketRepository;

    @GetMapping("/customer/schedule")
    public String scheduleMain(Model model){
        model.addAttribute("schedule",scheduleRepository.findAll());
        return "schedule-customer";
    }
    @GetMapping("/customer/ticket")
    public String ticketMain(@ModelAttribute("ticket") Ticket ticket, Model model){
        model.addAttribute("tickets", ticketRepository.findAll());
        model.addAttribute("schedules", scheduleRepository.findAll());
        return "ticket/main";
    }
    @GetMapping("/customer/schedule/add/{id}")
    public String addTicket(
            @PathVariable("id") long id,
            @ModelAttribute("ticket") Ticket ticket,
            Model model){
        model.addAttribute("ticket", ticketRepository.findById(id));
        return "ticket/add";
    }
    @PostMapping("/customer/schedule/add/{id}")
    public String addTicket(
            @PathVariable("id") long id,
            @ModelAttribute("ticket") @Valid Ticket ticket,
            BindingResult bindingResult,
            Principal user,
            Model model){
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ticket Id:" + id));;
        if(bindingResult.hasErrors()){
            return "ticket/add";
        }
        if(schedule == null){
            return "redirect:/customer/ticket";
        }
        ticket.setSchedule(schedule);
        Customer customer = customerRepository.findByUser_Username(user.getName());
        ticket.setCustomer(customer);
        ticketRepository.save(ticket);
        return "redirect:/customer/ticket";
    }
    @PostMapping("/customer/ticket/delete/{id}")
    public String deleteTicket(@PathVariable("id") long id){
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ticket Id:" + id));
        ticketRepository.delete(ticket);
        return "redirect:/customer/ticket";
    }
}
