package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.dto.UserDTOMapper;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.util.List;


@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    private final UserService userService;

    private final UserDTOMapper userDTOMapper;

    @Autowired
    public AdminRestController(UserService userService , UserDTOMapper userDTOMapper) {
        this.userService = userService;
        this.userDTOMapper = userDTOMapper;
    }


    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return userService.listUsers().stream().map(userDTOMapper::toUserDto).toList();
    }


    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@RequestBody  User user) {
        userService.save(user);
        return ResponseEntity.ok(userDTOMapper.toUserDto(user));
    }


    @PatchMapping("/users")
    public ResponseEntity<UserDTO> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return ResponseEntity.ok(userDTOMapper.toUserDto(user));
    }

    @DeleteMapping("/users")
    public ResponseEntity<Void> deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
