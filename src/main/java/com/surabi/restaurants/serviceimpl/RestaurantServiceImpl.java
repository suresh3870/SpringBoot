package com.surabi.restaurants.serviceimpl;

import com.surabi.restaurants.entity.Menu;
import com.surabi.restaurants.entity.OrderDetails;
import com.surabi.restaurants.entity.Orders;
import com.surabi.restaurants.entity.User;
import com.surabi.restaurants.repository.MenuRepository;
import com.surabi.restaurants.repository.OrderDetailsRepository;
import com.surabi.restaurants.repository.OrderRepository;
import com.surabi.restaurants.service.RestaurantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
}
