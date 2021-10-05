package com.surabi.restaurants.serviceimpl;

import com.surabi.restaurants.DTO.*;
import com.surabi.restaurants.entity.*;
import com.surabi.restaurants.repository.BillRepository;
import com.surabi.restaurants.repository.MenuRepository;
import com.surabi.restaurants.repository.OrderDetailsRepository;
import com.surabi.restaurants.repository.OrderRepository;
import com.surabi.restaurants.response.APIResponse;
import com.surabi.restaurants.service.RestaurantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
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
    BillRepository billRepository;
    @Autowired
    OrderDetailsRepository orderDetailsRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Menu> viewAllMenu() {
        return menuRepository.findAll();
    }

    @Override
    public Optional<Menu> getMenuById(int menuID) {

        if (menuRepository.existsById(menuID)) {
            return menuRepository.findById(menuID);
        } else {
            return Optional.of(new Menu("Menu not found"));
        }
    }

    @Override
    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
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
            System.out.println("Getting menu id" + orderBulkDTOtemp.getMenuID());
            try {
                Menu menu = menuRepository.getOne(orderBulkDTOtemp.getMenuID());
                orderDetails.setMenu(menu);
                orderDetails.setItemTotalprice(orderBulkDTOtemp.getQty() * menu.getPrice());
                orderDetailsRepository.save(orderDetails);
            } catch (EntityNotFoundException e) {
                return "Wrong menu id: " + orderBulkDTOtemp.getMenuID();
            }
        }
        return "Order ID: " + savedOrderID + " has been created successfully";
    }

    @Override
    public String checkOut(int orderId) {
        Orders orders = new Orders();
        Date date = new Date();
        orders.setOrderId(orderId);
        Bill bill = new Bill();
        bill.setBillID(orderId);
        bill.setBillDate(date);
        bill.setOrders(orders);
        if (orderRepository.existsById(orderId)) {
            Query nativeQuery = entityManager.createNativeQuery("select sum(ITEM_TOTALPRICE) from ORDER_DETAILS where ORDER_ID=?1");
            nativeQuery.setParameter(1, orderId);
            List amount = nativeQuery.getResultList();
            System.out.println("List from query "+amount.get(0));
            double amt = (double) amount.get(0);
            System.out.println("amt: " + amount);
            bill.setBillAmount(amt);
            System.out.println("Bill ID: " + bill.getBillID());
            if (!billRepository.existsById(bill.getBillID())) {
                String loggedUser = UserLoggedDetailsImpl.getMyDetails();
                String role = UserLoggedDetailsImpl.getUserRole().toString();
                System.out.println("Logged user role is "+role);
                Orders dborders = orderRepository.getOne(orderId);
                User orderUserDetails = dborders.getUser();
                String orderUser = orderUserDetails.getUsername();
                if (orderUser == loggedUser || role.equals("[ADMIN]")) {
                    Bill savedBillId = billRepository.save(bill);
                    int billid = savedBillId.getBillID();
                    return "Bill saved with ID " + billid;
                } else {
                    return "Order ID: " + orderId + " does not belongs to you, cant checkout!";
                }
            }
            return "Bill with given order already generated";
        } else {
            return "Order ID: " + orderId + " does not exist";
        }
    }

    @Override
    public APIResponse<List<BillDetailsDTO>> viewMyBill(int billID) {
        List<BillDetailsDTO> billDetailsDTOS = new ArrayList<>();
        if (billRepository.existsById(billID)) {
            Orders orders = orderRepository.getOne(billID);
            String user = UserLoggedDetailsImpl.getMyDetails();
            User orderUser = orders.getUser();
            String orderUser1 = orderUser.getUsername();
            System.out.println("users from DB for order is: " + orderUser);
            if (user == orderUser1) {
                Query nativeQuery = entityManager.createNativeQuery("select b.BILLID as BILL_ID,  u.USERNAME as USERNAME, m.ITEM as ITEM,  d.QUANTITY as QTY, m.PRICE as PRICE, d.ITEM_TOTALPRICE as ITEM_TOTALPRICE,b.BILL_AMOUNT as BILL_AMOUNT from menu m, orders o, ORDER_DETAILS d, users u , BILL b where m.menu_id=d.menu_id  and o.ORDER_ID=d.ORDER_ID and u.USERNAME=o.USERNAME  \n" +
                        "                        and b.ORDER_ID=O.ORDER_ID\n" +
                        "                        and o.ORDER_ID=1", "BillDTOMapping");
                List<BillDetailsDTO> list = nativeQuery.getResultList();
                BillOrderDetailsDTO billOrderDetailsDTO = new BillOrderDetailsDTO();
                BillDetailsDTO billDetailsDTO = list.get(1);
                billOrderDetailsDTO.setBILL_ID(billDetailsDTO.getBILL_ID());
                billOrderDetailsDTO.setUSERNAME(billDetailsDTO.getUSERNAME());
                billOrderDetailsDTO.setBILL_AMOUNT(billDetailsDTO.getBILL_AMOUNT());
                List<BillDetailDTO> orderDetailDTO = new ArrayList<>();
                for (BillDetailsDTO billDetailsDTO1 : list) {
                    BillDetailDTO billDetailDTO1 = new BillDetailDTO();
                    billDetailDTO1.setITEM(billDetailsDTO1.getITEM());
                    billDetailDTO1.setQTY(billDetailsDTO1.getQTY());
                    billDetailDTO1.setPRICE(billDetailsDTO1.getPRICE());
                    billDetailDTO1.setITEM_TOTALPRICE(billDetailsDTO1.getITEM_TOTALPRICE());
                    orderDetailDTO.add(billDetailDTO1);
                }
                billOrderDetailsDTO.setBillDetailDTO(orderDetailDTO);
                return new APIResponse("200", "success!", billOrderDetailsDTO);
            } else {
                return new APIResponse("400", "Bill ID: " + billID + " does not belongs to you!", billDetailsDTOS);
            }
        } else {
            return new APIResponse("400", "Bill ID: " + billID + " does not exist", billDetailsDTOS);
        }
    }

}

