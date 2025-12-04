package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;


import java.security.Principal;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService,  RoleService roleService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping(value = "/admin")
    public String users(ModelMap model) {
        List<User> users = userService.findAll();
        List<Role> roles = roleService.findAll();
        model.addAttribute("listRoles", roles);
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping(value = "/user")
    public String user(Principal principal, ModelMap model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/admin/new")
    public String newUser(ModelMap model) {
        model.addAttribute("user", new User());
        List<Role> roles = roleService.findAll();
        model.addAttribute("listRoles", roles);
        return "new";
    }

    @GetMapping("/admin/edit")
    public String editUser(ModelMap model, @RequestParam(value = "id") Long userId) {
        User user = userService.findById(userId);
        List<Role> roles = roleService.findAll();
        model.addAttribute("listRoles", roles);
        model.addAttribute("user", user);
        return "edit";
    }

    @PostMapping("/admin/newUser")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.add(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/editUser")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/delUser")
    public String delUser(ModelMap model, @RequestParam(value = "id") Long userId) {
        User user = userService.findById(userId);
        userService.delete(user);
        return "redirect:/admin";
    }
}
