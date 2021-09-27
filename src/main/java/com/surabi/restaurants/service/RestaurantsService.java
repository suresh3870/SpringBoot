package com.surabi.restaurants.service;

import com.surabi.restaurants.entity.*;

import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RestaurantsService {

    List<Menu> viewAllMenu();
    List<Orders>getAllOrders();
    Optional<Menu> getMenuById(int id);
    public int createOrder(int menuID, int qty);
    public int createBulkItem(List<OrderBulkDTO> orderBulkDTO);
}