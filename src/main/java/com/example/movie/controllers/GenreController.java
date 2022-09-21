package com.example.movie.controllers;

import com.example.movie.models.Genre;
import com.example.movie.models.MainActor;
import com.example.movie.repo.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/genre")
@PreAuthorize("hasAnyAuthority('EMPLOYEE')")
public class GenreController {
    @Autowired
    private GenreRepository genreRepository;

    @GetMapping
    public String genreMain(Model model){
        model.addAttribute("genre", genreRepository.findAll());
        return "genre/main";
    }

    @GetMapping("/add")
    public String addGenre(@ModelAttribute("genre") Genre genre){
        return "/genre/add";
    }
    @PostMapping("/add")
    public String addGenre(
            @ModelAttribute("genre")@Valid Genre genre,
            BindingResult bindingResult,
            Model model){
        if(bindingResult.hasErrors()){
            return "genre/add";
        }
        genreRepository.save(genre);
        return "redirect:/genre";
    }

    @GetMapping("/edit/{id}")
    public String editGenre(@PathVariable("id") long id, Model model){
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid genre Id:" + id));
        model.addAttribute(genre);
        return "genre/edit";
    }
    @PostMapping("/edit/{id}")
    public String editGenre(
            @PathVariable("id") long id,
            @ModelAttribute("genre") @Valid Genre genre,
            BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "genre/edit";
        }
        genre.setId(id);
        genreRepository.save(genre);
        return "redirect:/genre";
    }
    @PostMapping("/delete/{id}")
    public String deleteGenre(@PathVariable("id") long id){
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid genre Id:" + id));
        genreRepository.delete(genre);
        return "redirect:/genre";
    }
    @GetMapping("/filter")
    public String genreFilter(Model model){
        return "genre/filter";
    }
    @PostMapping("/filter/result")
    public String genreResult(@RequestParam String name, Model model){
        List<Genre> result = genreRepository.findByNameContains(name);
        model.addAttribute("result", result);
        return "genre/filter";
    }
}
