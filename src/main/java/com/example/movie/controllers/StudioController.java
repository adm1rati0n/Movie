package com.example.movie.controllers;

import com.example.movie.models.Genre;
import com.example.movie.models.MainActor;
import com.example.movie.models.Studio;
import com.example.movie.repo.GenreRepository;
import com.example.movie.repo.StudioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/studio")
@PreAuthorize("hasAnyAuthority('EMPLOYEE')")
public class StudioController {
    @Autowired
    private StudioRepository studioRepository;

    @GetMapping
    public String studioMain(Model model) {
        model.addAttribute("studio", studioRepository.findAll());
        return "studio/main";
    }

    @GetMapping("/add")
    public String addStudio(@ModelAttribute("studio") Studio studio) {
        return "/studio/add";
    }

    @PostMapping("/add")
    public String addStudio(
            @ModelAttribute("studio") @Valid Studio studio,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "studio/add";
        }
        studioRepository.save(studio);
        return "redirect:/studio";
    }

    @GetMapping("/edit/{id}")
    public String editStudio(@PathVariable("id") long id, Model model) {
        Studio studio = studioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid studio Id:" + id));
        model.addAttribute(studio);
        return "studio/edit";
    }

    @PostMapping("/edit/{id}")
    public String editStudio(
            @PathVariable("id") long id,
            @ModelAttribute("genre") @Valid Studio studio,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "studio/edit";
        }
        studio.setId(id);
        studioRepository.save(studio);
        return "redirect:/studio";
    }
    @PostMapping("/delete/{id}")
    public String deleteStudio(@PathVariable("id") long id){
        Studio studio = studioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid actor Id:" + id));
        studioRepository.delete(studio);
        return "redirect:/studio";
    }
}
