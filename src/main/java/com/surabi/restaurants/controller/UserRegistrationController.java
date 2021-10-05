package com.surabi.restaurants.controller;

import com.surabi.restaurants.DTO.UserDTO;
import com.surabi.restaurants.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/surabi/usersReg")
public class UserRegistrationController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public String resister(UserDTO userDTO) {
        return userService.saveUser(userDTO);

    }
}

