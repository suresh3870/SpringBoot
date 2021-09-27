package com.surabi.restaurants.service;

import com.surabi.restaurants.entity.Menu;
import com.surabi.restaurants.entity.OrderDetails;
import com.surabi.restaurants.entity.Orders;
import com.surabi.restaurants.entity.User;

import java.util.List;
import java.util.Optional;

public interface RestaurantsService {

    List<Menu> viewAllMenu();
    List<Orders>getAllOrders();
    Optional<Menu> getMenuById(int id);
    public int createOrder(int menuID, int qty);
}