package com.surabi.restaurants.controller;

import com.surabi.restaurants.entity.Menu;
import com.surabi.restaurants.entity.OrderDetails;
import com.surabi.restaurants.entity.Orders;
import com.surabi.restaurants.entity.User;
import com.surabi.restaurants.service.RestaurantsService;
import com.surabi.restaurants.serviceimpl.RestaurantServiceImpl;
import com.surabi.restaurants.serviceimpl.UserLoggedDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/surabi/users")
public class UsersController {

	@Autowired
	RestaurantsService restaurantsService;
	private Object Principal;

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
