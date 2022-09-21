package com.example.movie.controllers;

import com.example.movie.models.Genre;
import com.example.movie.models.Hall;
import com.example.movie.models.MainActor;
import com.example.movie.repo.GenreRepository;
import com.example.movie.repo.HallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/hall")
@PreAuthorize("hasAnyAuthority('EMPLOYEE')")
public class HallController {
    @Autowired
    private HallRepository hallRepository;

    @GetMapping
    public String hallMain(Model model){
        model.addAttribute("hall", hallRepository.findAll());
        return "hall/main";
    }

    @GetMapping("/add")
    public String addHall(@ModelAttribute("hall") Hall hall){
        return "/hall/add";
    }
    @PostMapping("/add")
    public String addHall(
            @ModelAttribute("hall")@Valid Hall hall,
            BindingResult bindingResult,
            Model model){
        if(bindingResult.hasErrors()){
            return "hall/add";
        }
        hallRepository.save(hall);
        return "redirect:/hall";
    }

    @GetMapping("/edit/{id}")
    public String editHall(@PathVariable("id") long id, Model model){
        Hall hall = hallRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid hall Id:" + id));
        model.addAttribute(hall);
        return "hall/edit";
    }
    @PostMapping("/edit/{id}")
    public String editHall(
            @PathVariable("id") long id,
            @ModelAttribute("hall") @Valid Hall hall,
            BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "hall/edit";
        }
        hall.setId(id);
        hallRepository.save(hall);
        return "redirect:/hall";
    }

    @PostMapping("/delete/{id}")
    public String deleteHall(@PathVariable("id") long id){
        Hall hall = hallRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid actor Id:" + id));
        hallRepository.delete(hall);
        return "redirect:/hall";
    }
}
