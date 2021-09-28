package com.surabi.restaurants.controller;

import com.surabi.restaurants.DTO.OrderBulkDTO;
import com.surabi.restaurants.entity.*;
import com.surabi.restaurants.service.RestaurantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/surabi/users")
public class UsersController {

    @Autowired
    RestaurantsService restaurantsService;

    @GetMapping("/ListMenu")
    public List<Menu> viewMenu() {
        return restaurantsService.viewAllMenu();
    }

    @GetMapping("/MenuByID")
    public Optional<Menu> getItem(int menuID) {
        return restaurantsService.getMenuById(menuID);
    }
    //@GetMapping("/Order")
    //public String order(int menuID, int qty) {
    //	 int orderID= restaurantsService.createOrder(menuID, qty);
    //	 if(orderID>0){
    //	 	return "Order "+orderID+" has been created successfully";}
    //	 return "Order not created as menu ID does not exist";
    //}

    @PostMapping("/Order")
    public String order(@RequestBody List<OrderBulkDTO> orderBulkDTO) {
        return restaurantsService.createBulkItem(orderBulkDTO);
    }

    @PostMapping("/CheckOut")
    public String checkOut(int orderId) {
        return restaurantsService.checkOut(orderId);
    }

    @GetMapping("/ViewBill")
    public List<? extends Object> viewBill(int billID) {
        return restaurantsService.viewBillByID(billID);
    }

    //@GetMapping("/MyDetails")
    //public String mydetails() {
    //	return UserLoggedDetailsImpl.getMyDetails();
    //}

}
