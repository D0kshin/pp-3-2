package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;


import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users")
    public String users(ModelMap model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/new")
    public String newUser(ModelMap model) {
        model.addAttribute("user", new User());
        return "new";
    }

    @GetMapping("/edit")
    public String editUser(ModelMap model, @RequestParam(value = "id") Long userId) {
        User user = userService.findById(userId);
        model.addAttribute("user", user);
        return "edit";
    }

    @PostMapping("/newUser")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.add(user);
        return "redirect:/users";
    }

    @PostMapping("/editUser")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/users";
    }

    @GetMapping("/delUser")
    public String delUser(ModelMap model, @RequestParam(value = "id") Long userId) {
        User user = userService.findById(userId);
        userService.delete(user);
        return "redirect:/users";
    }
}
