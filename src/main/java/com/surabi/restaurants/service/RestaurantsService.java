package com.surabi.restaurants.service;

import com.surabi.restaurants.DTO.BillDetailsDTO;
import com.surabi.restaurants.DTO.OrderBulkDTO;
import com.surabi.restaurants.entity.Menu;
import com.surabi.restaurants.entity.Orders;
import com.surabi.restaurants.response.APIResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface RestaurantsService {

    List<Menu> viewAllMenu();

    Optional<Menu> getMenuById(int menuID);

    String createBulkItem(List<OrderBulkDTO> orderBulkDTO);

    List<Orders> getAllOrders();

    String checkOut(int orderId);

    public APIResponse<List<BillDetailsDTO>> viewMyBill(int billID);
}