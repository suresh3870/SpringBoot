package com.surabi.restaurants.controller;

import com.surabi.restaurants.entity.Menu;
import com.surabi.restaurants.service.RestaurantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/surabi/admin")
public class AdminController {

    @Autowired
    RestaurantsService restaurantsService;

    @GetMapping("/CreateMenu")
    public List<Menu> CreateMenu() {
        return restaurantsService.viewAllMenu();
    }

    @GetMapping("/UpdateMenu")
    public Optional<Menu> UpdateMenu(int id) {
        return restaurantsService.getMenuById(id);
    }

    @GetMapping("/DeleteMenu")
    public List<Menu> deleteMenu() {
        return restaurantsService.viewAllMenu();
    }

    @GetMapping("/ListMenu")
    public List<Menu> ListMenu() {
        return restaurantsService.viewAllMenu();
    }

    @GetMapping("/GenerateBill")
    public List<Menu> generateBill() {
        return restaurantsService.viewAllMenu();
    }

}
