package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.dto.UserDTOMapper;
import ru.kata.spring.boot_security.demo.model.PersonDetails;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserRestController {


    private final UserDTOMapper userDTOMapper;

    @Autowired
    public UserRestController(UserDTOMapper userDTOMapper) {
        this.userDTOMapper = new UserDTOMapper();
    }

    @GetMapping
    public UserDTO getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails person = (PersonDetails) authentication.getPrincipal();
        return userDTOMapper.toUserDto(person.getUser());
    }
}
