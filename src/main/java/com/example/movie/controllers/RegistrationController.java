package com.example.movie.controllers;

import com.example.movie.models.Customer;
import com.example.movie.models.Role;
import com.example.movie.models.User;
import com.example.movie.repo.CustomerRepository;
import com.example.movie.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Collections;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/registration")
    public String registration(@ModelAttribute("user") User user, @ModelAttribute("customer")Customer customer){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @ModelAttribute("user")@Valid User user,
            BindingResult userResult,
            @ModelAttribute("customer")@Valid Customer customer,
            BindingResult customerResult,
            Model model){
        if(userResult.hasErrors()){
            return "registration";
        }

        if(customerResult.hasErrors()){
            return "registration";
        }
        User userFromDb = userRepository.findByUsername(user.getUsername());
        if(userFromDb != null)
        {
            model.addAttribute("message", "Пользователь с таким логином уже зарегистрирован");
            return "registration";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.CUSTOMER));
        customer.setUser(user);
        userRepository.save(user);
        customerRepository.save(customer);
        return "redirect:/login";
    }
}
