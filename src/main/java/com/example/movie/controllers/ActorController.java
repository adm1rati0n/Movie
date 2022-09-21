package com.example.movie.controllers;

import com.example.movie.models.Genre;
import com.example.movie.models.MainActor;
import com.example.movie.repo.GenreRepository;
import com.example.movie.repo.MainActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/actor")
@PreAuthorize("hasAnyAuthority('EMPLOYEE')")
public class ActorController {
    @Autowired
    private MainActorRepository actorRepository;

    @GetMapping
    public String actorMain(Model model){
        model.addAttribute("actor", actorRepository.findAll());
        return "actor/main";
    }

    @GetMapping("/add")
    public String addActor(@ModelAttribute("actor") MainActor actor){
        return "/actor/add";
    }
    @PostMapping("/add")
    public String addActor(
            @ModelAttribute("actor")@Valid MainActor actor,
            BindingResult bindingResult,
            Model model){
        if(bindingResult.hasErrors()){
            return "actor/add";
        }
        actorRepository.save(actor);
        return "redirect:/actor";
    }

    @GetMapping("/edit/{id}")
    public String editActor(@PathVariable("id") long id, Model model){
        MainActor actor = actorRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid actor Id:" + id));
        model.addAttribute("actor" , actor);
        return "actor/edit";
    }
    @PostMapping("/edit/{id}")
    public String editActor(
            @PathVariable("id") long id,
            @ModelAttribute("actor") @Valid MainActor actor,
            BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "actor/edit";
        }
        actor.setId(id);
        actorRepository.save(actor);
        return "redirect:/actor";
    }

    @PostMapping("/delete/{id}")
    public String deleteActor(@PathVariable("id") long id){
        MainActor actor = actorRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid actor Id:" + id));
        actorRepository.delete(actor);
        return "redirect:/actor";
    }
}
