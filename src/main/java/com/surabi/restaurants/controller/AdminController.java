package com.surabi.restaurants.controller;

import com.surabi.restaurants.entity.Bill;
import com.surabi.restaurants.entity.Menu;
import com.surabi.restaurants.entity.User;
import com.surabi.restaurants.service.AdminService;
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
    AdminService adminService;

    @GetMapping("/CreateUser")
    public String CreateUser(User user) {
        return adminService.CreateUser(user);
    }

    @GetMapping("/UpdateUser")
    public String UpdateUser(User user) {
        return adminService.UpdateUser(user);
    }

    @GetMapping("/DeleteUser")
    public String deleteUser(String userName) {
        return adminService.deleteUser(userName);
    }

    @GetMapping("/TotalSellByMonth")
    public List<Object[]> totolSellByMonth(int monthID) {
        return adminService.totolSellByMonth(monthID);
    }

    @GetMapping("/ViewAllBills")
    public List<Bill> viewTodaysBills() {
        return adminService.viewTodaysBills();
    }

}
