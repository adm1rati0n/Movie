package com.example.movie.controllers;

import com.example.movie.models.Film;
import com.example.movie.models.Genre;
import com.example.movie.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/film")
@PreAuthorize("hasAnyAuthority('EMPLOYEE')")
public class FilmController {
    @Autowired
    FilmRepository filmRepository;
    @Autowired
    StudioRepository studioRepository;
    @Autowired
    MainActorRepository actorRepository;
    @Autowired
    ProducerRepository producerRepository;
    @Autowired
    GenreRepository genreRepository;

    private void loadData(Model model){
        model.addAttribute("studios", studioRepository.findAll());
        model.addAttribute("actors", actorRepository.findAll());
        model.addAttribute("producers", producerRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());
    }

    @GetMapping
    public String filmMain(Model model){
        model.addAttribute("films", filmRepository.findAll());
        return "film/main";
    }

    @GetMapping("/add")
    public String addFilm(@ModelAttribute("film") Film film, Model model){
        loadData(model);
        return "film/add";
    }
    @PostMapping("/add")
    public String addFilm(
            @ModelAttribute("film") @Valid Film film,
            BindingResult bindingResult,
            Model model){
        if(bindingResult.hasErrors()){
            loadData(model);
            return "film/add";
        }
        filmRepository.save(film);
        return "redirect:/film";
    }

    @PostMapping("/delete/{id}")
    public String deleteFilm(@PathVariable("id") long id){
        Film film = filmRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid film Id:" + id));
        filmRepository.delete(film);
        return "redirect:/film";
    }

    @GetMapping("/edit/{id}")
    public String editFilm(@PathVariable("id") long id, Model model){
        loadData(model);
        Film film = filmRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid film Id:" + id));
        model.addAttribute("film", film);
        return "film/edit";
    }
    @PostMapping("/edit/{id}")
    public String editFilm(
            @PathVariable("id") long id,
            @ModelAttribute("film") @Valid Film film,
            BindingResult bindingResult,
            Model model){
        if(bindingResult.hasErrors()){
            loadData(model);
            return "film/edit";
        }
        film.setId(id);
        filmRepository.save(film);
        return "redirect:/film";
    }
}
