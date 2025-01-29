package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.sql.SQLOutput;
import java.util.*;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    private AdminController( UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String listUsers(ModelMap model) {

        model.addAttribute("users", userService.listUsers());
        model.addAttribute("newUser", new User());
        model.addAttribute("allRoles", roleService.findAll());
        return "index2";
    }

    @GetMapping("/add")
    public String addUserForm(ModelMap model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.findAll());
        return "user-add";
    }

    @PostMapping("/save")
    public String addUser(@ModelAttribute("newUser") @Valid User user,  BindingResult result) {

        if (result.hasErrors()) {
            return "index2";
        }
        // проверка на имейл
        Optional<User> userWithSameEmail = userService.findByEmail(user.getEmail());
        if (userWithSameEmail.isPresent()) {
            result.rejectValue("email", "error.newUser", "Этот email уже используется другим пользователем.");
            return "index2";
        }
        userService.save(user);
        return "redirect:/admin";
    }

    @PatchMapping("/edit")
    public String editUserForm(@ModelAttribute("newUser") @Valid User user,  BindingResult result) {

        if (result.hasErrors()) {
            return "index2";
        }
        // проверка на имейл
        Optional<User> userWithSameEmail = userService.findByEmail(user.getEmail());
        if (userWithSameEmail.isPresent() && !user.getEmail().equals(userWithSameEmail.get().getEmail())) {
            // проверяем, что email не совпадает с текущим пользователем
            result.rejectValue("email", "error.user", "Этот email уже используется другим пользователем.");
            return "index2";
        }
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}