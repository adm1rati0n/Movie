package com.example.movie.controllers;

import com.example.movie.models.Employee;
import com.example.movie.models.Role;
import com.example.movie.models.User;
import com.example.movie.repo.EmployeeRepository;
import com.example.movie.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;

@Controller
@RequestMapping("/employee")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String employeeMain(Model model){
        Iterable<Employee> employees = employeeRepository.findAll();
        model.addAttribute("employees", employees);
        return "/employee/main";
    }

    @GetMapping("/add")
    public String addEmployee(@ModelAttribute("employee") Employee employee, Model model, @ModelAttribute("user") User user){
        return "employee/add";
    }
    @PostMapping("/add")
    public String addEmployee(
            @ModelAttribute("employee") @Valid Employee employee, BindingResult employeeResult,
            @ModelAttribute("user") @Valid User user, BindingResult userResult,
            Model model){
        if(employeeResult.hasErrors()){
            return "/employee/add";
        }
        if(userResult.hasErrors()){
            return "employee/add";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.EMPLOYEE));
        userRepository.save(user);
        employee.setUser(user);
        employeeRepository.save(employee);
        return "redirect:/employee";
    }

    @PostMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") long id){
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid employee Id: " + id));
        employeeRepository.delete(employee);
        return "redirect:/employee";
    }

    @GetMapping("/edit/{id}")
    public String editEmployee(@PathVariable("id") long id, Model model){
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
        model.addAttribute("employee", employee);
        model.addAttribute("user", employee.getUser());
        return "employee/edit";
    }
    @PostMapping("/edit/{id}")
    public String editEmployee(
            @PathVariable("id") long id,
            @ModelAttribute("employee") @Valid Employee employee, BindingResult employeeResult,
            @ModelAttribute("user") @Valid User user,
            BindingResult userResult,
            Model model){
        Employee employeeFromDB = employeeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
        if(employeeResult.hasErrors()){
            return "employee/edit";
        }
        if (userResult.hasErrors()){
            return "employee/edit";
        }
        user.setId(employeeFromDB.getUser().getId());
        user.setActive(true);
        userRepository.save(user);
        employee.setUser(user);
        employee.setId(id);
        employeeRepository.save(employee);
        return "redirect:/employee";
    }

}
