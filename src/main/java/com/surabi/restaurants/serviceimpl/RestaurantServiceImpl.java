package com.surabi.restaurants.serviceimpl;

import com.surabi.restaurants.entity.*;
import com.surabi.restaurants.repository.MenuRepository;
import com.surabi.restaurants.repository.OrderDetailsRepository;
import com.surabi.restaurants.repository.OrderRepository;
import com.surabi.restaurants.service.RestaurantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RestaurantServiceImpl implements RestaurantsService {

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailsRepository orderDetailsRepository;

    @Override
    public List<Menu> viewAllMenu() {
        return menuRepository.findAll();
    }

    @Override
    public Optional<Menu> getMenuById(int id) {
        return menuRepository.findById(id);
    }

    @Override
    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }
    @Override
    public int createOrder(int menuID, int qty) {

        int savedOrderID = 0;
        if (menuRepository.existsById(menuID)) {
            Date date = new Date();
            Orders orders = new Orders();
            OrderDetails orderDetails = new OrderDetails();
            User user = new User();
            orders.setOrderDate(date);
            user.setUsername(UserLoggedDetailsImpl.getMyDetails());
            orders.setUser(user);
            orderDetails.setQuantity(qty);
            orderDetails.setOrders(orders);
            Menu menu =menuRepository.findAll().get(menuID);
            orderDetails.setMenu(menu);
            orderDetails.setPrice(qty * menu.getPrice());
            Orders savedOrder = orderRepository.save(orders);
            savedOrderID= savedOrder.getOrderId();
            orderDetailsRepository.save(orderDetails);
            return savedOrderID;
        } else {
            System.out.println("No menu with provided ID");
        }
        return savedOrderID;
    }

    @Override
    public int createBulkItem(List<OrderBulkDTO> orderBulkDTO) {
        int savedOrderID = 0;
        Date date = new Date();
        Orders orders = new Orders();
        User user = new User();
        orders.setOrderDate(date);
        user.setUsername(UserLoggedDetailsImpl.getMyDetails());
        orders.setUser(user);
        Orders savedOrder = orderRepository.save(orders);
        savedOrderID = savedOrder.getOrderId();
        for (OrderBulkDTO orderBulkDTOtemp : orderBulkDTO) {
                OrderDetails orderDetails = new OrderDetails();
                orderDetails.setQuantity(orderBulkDTOtemp.getQty());
                orderDetails.setOrders(orders);
                System.out.println("Getting menu id"+orderBulkDTOtemp.getMenuID());
                Menu menu = menuRepository.getOne(orderBulkDTOtemp.getMenuID());
                System.out.println("menu details"+menu);
                orderDetails.setMenu(menu);
                orderDetails.setPrice(orderBulkDTOtemp.getQty() * menu.getPrice());
                orderDetailsRepository.save(orderDetails);

            }
        return savedOrderID;
        }

    }

