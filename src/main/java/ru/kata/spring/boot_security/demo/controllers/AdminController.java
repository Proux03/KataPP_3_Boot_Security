package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AdminController(UserService userService, UserRepository userRepository, RoleRepository roleRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users-table";
    }

    @GetMapping("/users-add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("role", new HashSet<Role>());
        return "users-add";
    }

    @PostMapping("/users-add")
    public String addUser(@ModelAttribute("user") User user, @RequestParam(value = "role") String[] roles) {
        Set<Role> roleSet = new HashSet<>();
        for(String role : roles) {
            roleSet.add(new Role(role));
        }
        roleRepository.saveAll(roleSet);
        user.setRoles(roleSet);
        userService.addUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.removeUser(id);
        return "redirect:/admin";
    }

    @GetMapping("user-update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "users-update";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") long id, @RequestParam(value = "role") String[] roles) {
        Set<Role> roleSet = new HashSet<>();
        for(String role : roles) {
            roleSet.add(new Role(role));
        }
        roleRepository.saveAll(roleSet);
        user.setRoles(roleSet);
        userService.addUser(user);
        return "redirect:/admin";
    }
}
