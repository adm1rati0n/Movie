package com.example.movie.controllers;

import com.example.movie.models.Genre;
import com.example.movie.models.MainActor;
import com.example.movie.models.Producer;
import com.example.movie.repo.GenreRepository;
import com.example.movie.repo.ProducerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/producer")
@PreAuthorize("hasAnyAuthority('EMPLOYEE')")
public class ProducerController {
    @Autowired
    private ProducerRepository producerRepository;

    @GetMapping
    public String producerMain(Model model){
        model.addAttribute("producer", producerRepository.findAll());
        return "producer/main";
    }

    @GetMapping("/add")
    public String addProducer(@ModelAttribute("producer") Producer producer){
        return "/producer/add";
    }
    @PostMapping("/add")
    public String addProducer(
            @ModelAttribute("producer")@Valid Producer producer,
            BindingResult bindingResult,
            Model model){
        if(bindingResult.hasErrors()){
            return "producer/add";
        }
        producerRepository.save(producer);
        return "redirect:/producer";
    }

    @GetMapping("/edit/{id}")
    public String editProducer(@PathVariable("id") long id, Model model){
        Producer producer = producerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid producer Id:" + id));
        model.addAttribute(producer);
        return "producer/edit";
    }
    @PostMapping("/edit/{id}")
    public String editProducer(
            @PathVariable("id") long id,
            @ModelAttribute("producer") @Valid Producer producer,
            BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "producer/edit";
        }
        producer.setId(id);
        producerRepository.save(producer);
        return "redirect:/producer";
    }

    @PostMapping("/delete/{id}")
    public String deleteProducer(@PathVariable("id") long id){
        Producer producer = producerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid actor Id:" + id));
        producerRepository.delete(producer);
        return "redirect:/producer";
    }
}
