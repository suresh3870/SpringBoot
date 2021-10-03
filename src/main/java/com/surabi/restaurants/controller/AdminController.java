package com.surabi.restaurants.controller;

import com.surabi.restaurants.DTO.BillDTO;
import com.surabi.restaurants.entity.Bill;
import com.surabi.restaurants.entity.User;
import com.surabi.restaurants.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/surabi/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/CreateUser")
    public String CreateUser(@Valid User user) {
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
    public double totalSellByMonth(int monthID) {
        return adminService.totalSellByMonth(monthID);
    }

    @GetMapping("/ViewAllBills")
    public List<BillDTO>  viewTodaysBills() {
        return adminService.viewTodayBills();
    }

}
