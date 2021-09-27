package com.surabi.restaurants.service;

import com.surabi.restaurants.DTO.OrderBulkDTO;
import com.surabi.restaurants.entity.*;

import java.util.List;
import java.util.Optional;

public interface RestaurantsService {

    List<Menu> viewAllMenu();
    Optional<Menu> getMenuById(int id);
    String createBulkItem(List<OrderBulkDTO> orderBulkDTO);
    List<Object[]> viewBillByID(int id);
    List<Orders> getAllOrders();
    int createOrder(int menuID, int qty);
    String checkOut(int orderId);
}