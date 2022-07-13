package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RestApiController {

    private final UserService userService;

    @Autowired
    public RestApiController(UserService userService) {
        this.userService = userService;
    }

    @Secured(value = {"ROLE_ADMIN"})
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
    }

    @Secured(value = {"ROLE_ADMIN"})
    @PostMapping("/users")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        userService.addUser(user);
        return new ResponseEntity<>("User created", HttpStatus.OK);
    }

    @Secured(value = {"ROLE_ADMIN"})
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> pageDelete(@PathVariable("id") long id) {
        userService.removeUser(id);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }

    @GetMapping("users/{id}")
    public ResponseEntity<User> getUser (@PathVariable("id") long id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUserByUsername (Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @Secured(value = {"ROLE_ADMIN"})
    @PutMapping("/users/{id}")
    public ResponseEntity<String> pageEdit(@PathVariable("id") long id, @RequestBody User user) {
        userService.addUser(user);
        return new ResponseEntity<>("User updated", HttpStatus.OK);
    }

}
