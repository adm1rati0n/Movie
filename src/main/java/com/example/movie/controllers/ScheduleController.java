package com.example.movie.controllers;

import com.example.movie.models.Schedule;
import com.example.movie.repo.FilmRepository;
import com.example.movie.repo.HallRepository;
import com.example.movie.repo.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/schedule")
@PreAuthorize("hasAnyAuthority('EMPLOYEE')")
public class ScheduleController {
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    HallRepository hallRepository;
    @Autowired
    FilmRepository filmRepository;

    private void loadData(Model model){
        model.addAttribute("halls", hallRepository.findAll());
        model.addAttribute("films", filmRepository.findAll());
    }

    @GetMapping
    public String scheduleMain(Model model){
        model.addAttribute("schedule", scheduleRepository.findAll());
        return "schedule/main";
    }

    @GetMapping("/add")
    public String addSchedule(@ModelAttribute("schedule") Schedule schedule, Model model){
        loadData(model);
        return "schedule/add";
    }
    @PostMapping("/add")
    public String addSchedule(
            @ModelAttribute("schedule") @Valid Schedule schedule,
            BindingResult bindingResult,
            Model model){
        if(bindingResult.hasErrors()){
            loadData(model);
            return "schedule/add";
        }
        scheduleRepository.save(schedule);
        return "redirect:/schedule";
    }

    @GetMapping("/edit/{id}")
    public String editSchedule(@PathVariable("id") long id, Model model){
        loadData(model);
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid schedule Id:" + id));
        model.addAttribute("schedule", schedule);
        return "schedule/edit";
    }
    @PostMapping("/edit/{id}")
    public String editSchedule(
            @PathVariable("id") long id,
            @ModelAttribute("schedule") @Valid Schedule schedule,
            BindingResult bindingResult,
            Model model){
        if(bindingResult.hasErrors()){
            loadData(model);
            return "schedule/edit";
        }
        schedule.setId(id);
        scheduleRepository.save(schedule);
        return "redirect:/schedule";
    }

    @PostMapping("/delete/{id}")
    public String deleteSchedule(@PathVariable("id") long id){
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid schedule Id:" + id));
        scheduleRepository.delete(schedule);
        return "redirect:/schedule";
    }

}
