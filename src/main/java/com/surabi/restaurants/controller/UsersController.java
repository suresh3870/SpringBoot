package com.surabi.restaurants.controller;

import com.surabi.restaurants.entity.*;
import com.surabi.restaurants.service.RestaurantsService;
import com.surabi.restaurants.serviceimpl.RestaurantServiceImpl;
import com.surabi.restaurants.serviceimpl.UserLoggedDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
	@GetMapping("/ItemByID")
	public Optional<Menu> getItem(int id) {
		return restaurantsService.getMenuById(id);
	}
	@GetMapping("/Order")
	public String order(int menuID, int qty) {
		 int orderID= restaurantsService.createOrder(menuID, qty);
		 if(orderID>0){
		 	return "Order "+orderID+" has been created successfully";}
		 return "Order not created as menu ID does not exist";
	}

	@PostMapping("/BulkOrder")
	public int order(@RequestBody List<OrderBulkDTO> orderBulkDTO) {
		return restaurantsService.createBulkItem(orderBulkDTO);

	}

	@GetMapping("/DeleteOrder")
	public List<Menu> deleteOrder() {
		return restaurantsService.viewAllMenu();
	}

	@GetMapping("/ViewBill")
	public List<Menu> viewBill() {
		return restaurantsService.viewAllMenu();
	}

	@GetMapping("/MyDetails")
	public String mydetails() {
		return UserLoggedDetailsImpl.getMyDetails();
	}

}
