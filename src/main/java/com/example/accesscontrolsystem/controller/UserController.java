package com.example.accesscontrolsystem.controller;

import com.example.accesscontrolsystem.model.BuildingModel;
import com.example.accesscontrolsystem.model.UserModel;
import com.example.accesscontrolsystem.repository.BuildingRepository;
import com.example.accesscontrolsystem.repository.UserRepository;
import com.example.accesscontrolsystem.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final BuildingRepository buildingRepository;
    private final UserService userService;


    @GetMapping("/")
    public String showUserList(@RequestParam(value = "sortBy", required = false) String sortBy, Model model) {
        List<UserModel> list = userService.getUserList();
        userService.sortUsers(list, sortBy);
        model.addAttribute("users", list);
        model.addAttribute("count", userService.getUsersCount(list));
        return "index";
    }

    @GetMapping("/users")
    public String getUsers(@RequestParam(value = "sortBy", required = false) String sortBy, Model model) {
        List<UserModel> userList = (List<UserModel>) userRepository.findAll();
        userService.sortUsers(userList, sortBy);
        model.addAttribute("users", userList);
        model.addAttribute("count", userService.getUsersCount(userList));
        return "index";
    }

    @GetMapping("/signup")
    public String showSignUpForm(UserModel user, Model model) {
        List<BuildingModel> buildingModels = buildingRepository.findAll();
        model.addAttribute("buildingModels", buildingModels);
        return "user/add-user";
    }

    @PostMapping("/adduser")
    public String addUser(@Valid UserModel userModel, BindingResult result, Model model) {
        List<BuildingModel> buildingModels = buildingRepository.findAll();
        model.addAttribute("buildingModels", buildingModels);
            if (result.hasErrors()) {
                return "user/add-user";
            }

        userService.addUser(userModel);

        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        List<BuildingModel> buildingModels = buildingRepository.findAll();
        model.addAttribute("buildingModels", buildingModels);
        return "user/update-user";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, UserModel user,
                             Model model) {
        model.addAttribute("user", user);
        userService.saveEditUser(user);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.removeUser(id);
        return "redirect:/";
    }

}