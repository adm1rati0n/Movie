package com.example.movie.controllers;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(){
        return "redirect:/home";
    }
    @GetMapping("/home")
    public String success(Authentication authentication){
        String role = authentication.getAuthorities().toString();
        if(role.contains("ADMIN")){
            return"redirect:/employee";
        }
        else if (role.contains("EMPLOYEE")){
            return "redirect:/film";
        }
        else if(role.contains("CUSTOMER")){
            return "redirect:/customer/schedule";
        }
        return "/home";
    }
}
