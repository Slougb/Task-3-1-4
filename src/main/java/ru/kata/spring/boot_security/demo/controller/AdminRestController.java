package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    private final UserService userService;

    @Autowired
    public AdminRestController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/users")
    public List<User> getAllUsers() {
        List<User> allUsers = userService.listUsers();
//        System.out.println(allUsers.get(0));
        return allUsers;
    }


    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody  User user) {
        userService.save(user);
        return ResponseEntity.ok(user);
    }


    @PatchMapping("/users")
    public ResponseEntity<?> updateUser(@RequestBody User userDto) {
        User userFromDb = userService.getUser(userDto.getId());
        userFromDb.setFirstName(userDto.getFirstName());
        userFromDb.setLastName(userDto.getLastName());
        userFromDb.setEmail(userDto.getEmail());
        userFromDb.setRoles(userDto.getRoles());
        userService.updateUser(userFromDb);
        return ResponseEntity.ok(userFromDb);
    }

    @DeleteMapping("/users")
    public ResponseEntity<Void> deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
