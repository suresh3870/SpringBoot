package com.surabi.restaurants.serviceimpl;

import com.surabi.restaurants.DTO.OrderBulkDTO;
import com.surabi.restaurants.entity.*;
import com.surabi.restaurants.repository.BillRepository;
import com.surabi.restaurants.repository.MenuRepository;
import com.surabi.restaurants.repository.OrderDetailsRepository;
import com.surabi.restaurants.repository.OrderRepository;
import com.surabi.restaurants.service.RestaurantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;


@Service
public class RestaurantServiceImpl implements RestaurantsService {
    @Autowired
    MenuRepository menuRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    BillRepository billRepository;

    @Autowired
    OrderDetailsRepository orderDetailsRepository;

    @Override
    public List<Menu> viewAllMenu() {
        return menuRepository.findAll();
    }

    @Override
    public Optional<Menu> getMenuById(int menuID) {

        try {
          return  menuRepository.findById(menuID);
        } catch (IllegalStateException e) {
            return Optional.of(new Menu("Menu not found"));
        }
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
            orderDetails.setItemTotalprice(qty * menu.getPrice());
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
    public String createBulkItem(List<OrderBulkDTO> orderBulkDTO) {
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
                try {
                    Menu menu = menuRepository.getOne(orderBulkDTOtemp.getMenuID());
                    orderDetails.setMenu(menu);
                    orderDetails.setItemTotalprice(orderBulkDTOtemp.getQty() * menu.getPrice());
                    orderDetailsRepository.save(orderDetails);
                } catch (EntityNotFoundException e) {
                    return "Wrong menu id: "+orderBulkDTOtemp.getMenuID();
                }
            }
        return "Order ID: "+savedOrderID+ " has been created successfully";
        }
    @Override
        public List<Object[]> viewBillByID(int id) {
        try {
            Orders orders = orderRepository.getOne(id);
            String user = UserLoggedDetailsImpl.getMyDetails();
            User orderUser = orders.getUser();
            String orderUser1 = orderUser.getUsername();
            Query nativeQuery = null;
            System.out.println("users from DB for order is: " + orderUser);
            if (user == orderUser1 ) {
                nativeQuery = entityManager.createNativeQuery("select b.BILLID, o.ORDER_ID, u.USERNAME,o.ORDER_DATE, m.ITEM,  d.QUANTITY, m.PRICE, d.ITEM_TOTALPRICE,b.BILL_AMOUNT, b.BILL_DATE from menu m, orders o, ORDER_DETAILS d, users u , BILL b where m.menu_id=d.menu_id  and o.ORDER_ID=d.ORDER_ID and u.USERNAME=o.USERNAME \n" +
                        "and b.ORDER_ID=O.ORDER_ID\n" +
                        "and o.ORDER_ID=?1");
                nativeQuery.setParameter(1, id);
                return nativeQuery.getResultList();
            } else {
                System.out.println("Order ID: " + id + "does not belongs to you OR no bill generated, please check out First");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  Collections.emptyList();
    }

    @Override
    public String checkOut(int orderId) {
        Orders orders = new Orders();
        Date date = new Date();
        orders.setOrderId(orderId);
        Bill bill =new Bill();
        bill.setBillID(orderId);
        bill.setBillDate(date);
        bill.setOrders(orders);
        Query nativeQuery =entityManager.createNativeQuery("select sum(ITEM_TOTALPRICE) from ORDER_DETAILS where ORDER_ID=?1");
        nativeQuery.setParameter(1, orderId);
        List amount = nativeQuery.getResultList();
        double amt= (double) amount.get(0);
        System.out.println("amt: "+amount);
        bill.setBillAmount(amt);
        System.out.println("Bill ID: "+bill.getBillID());
        if(!billRepository.existsById(bill.getBillID()))
        {Bill savedBillId = billRepository.save(bill);
        int billid = savedBillId.getBillID();
        return "Bill saved with ID "+billid;}
        return "Bill with given order already generated";
    }

}

