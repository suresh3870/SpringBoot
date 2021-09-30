package com.surabi.restaurants.serviceimpl;

import com.surabi.restaurants.DTO.BillDTO;
import com.surabi.restaurants.DTO.BillDetailsDTO;
import com.surabi.restaurants.DTO.ErrorMsgDTO;
import com.surabi.restaurants.DTO.OrderBulkDTO;
import com.surabi.restaurants.entity.*;
import com.surabi.restaurants.repository.BillRepository;
import com.surabi.restaurants.repository.MenuRepository;
import com.surabi.restaurants.repository.OrderDetailsRepository;
import com.surabi.restaurants.repository.OrderRepository;
import com.surabi.restaurants.service.RestaurantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;


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
            Menu menu = menuRepository.findAll().get(menuID);
            orderDetails.setMenu(menu);
            orderDetails.setItemTotalprice(qty * menu.getPrice());
            Orders savedOrder = orderRepository.save(orders);
            savedOrderID = savedOrder.getOrderId();
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
    public List<?> viewBillByID(int billID) {
        List<ErrorMsgDTO> listBillNotFound = new ArrayList<ErrorMsgDTO>();
        listBillNotFound.add(new ErrorMsgDTO("Bill "+billID+" not found"));
        List<ErrorMsgDTO> OtherBill = new ArrayList<ErrorMsgDTO>();
        OtherBill.add(new ErrorMsgDTO("Bill "+billID+" does not belong to you!"));
        if (billRepository.existsById(billID)) {
            Orders orders = orderRepository.getOne(billID);
            String user = UserLoggedDetailsImpl.getMyDetails();
            User orderUser = orders.getUser();
            String orderUser1 = orderUser.getUsername();
            Query nativeQuery = null;
            System.out.println("users from DB for order is: " + orderUser);
            if (user == orderUser1) {
                nativeQuery = entityManager.createNativeQuery("select b.BILLID as BILL_ID, o.ORDER_ID as ORDER_ID, u.USERNAME as USERNAME,o.ORDER_DATE as ORDER_DATE, m.ITEM as ITEM,  d.QUANTITY as QTY, m.PRICE as PRICE, d.ITEM_TOTALPRICE as ITEM_TOTALPRICE,b.BILL_AMOUNT as BILL_AMOUNT, b.BILL_DATE as BILL_DATE from menu m, orders o, ORDER_DETAILS d, users u , BILL b where m.menu_id=d.menu_id  and o.ORDER_ID=d.ORDER_ID and u.USERNAME=o.USERNAME \n" +
                        "and b.ORDER_ID=O.ORDER_ID\n" +
                        "and o.ORDER_ID=?1");
                nativeQuery.setParameter(1, billID);
                List<BillDTO> list = (List<BillDTO>) nativeQuery.getResultList();
                return list;
            } else {
                System.out.println("Bill ID: " + billID + "does not belongs to you");
                return OtherBill;
            }
        } else {
            System.out.println("Bill ID: " + billID + "does not exist");
            return listBillNotFound;
        }
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
            double amt = (double) amount.get(0);
            System.out.println("amt: " + amount);
            bill.setBillAmount(amt);
            System.out.println("Bill ID: " + bill.getBillID());
            if (!billRepository.existsById(bill.getBillID())) {
                String loggedUser = UserLoggedDetailsImpl.getMyDetails();
                Orders dborders = orderRepository.getOne(orderId);
                User orderUserDetails = dborders.getUser();
                String orderUser = orderUserDetails.getUsername();
                if (orderUser == loggedUser) {
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
    public Object viewMyBill(int billID) {
        List<BillDetailsDTO> listBillNotFound = new ArrayList<BillDetailsDTO>();
        listBillNotFound.add(new BillDetailsDTO("Bill "+billID+" not found"));
        List<BillDetailsDTO> OtherBill = new ArrayList<BillDetailsDTO>();
        OtherBill.add(new BillDetailsDTO("Bill "+billID+" does not belong to you!"));
        if (billRepository.existsById(billID)) {
            Orders orders = orderRepository.getOne(billID);
            String user = UserLoggedDetailsImpl.getMyDetails();
            User orderUser = orders.getUser();
            String orderUser1 = orderUser.getUsername();
            //Query nativeQuery = null;
            System.out.println("users from DB for order is: " + orderUser);
            if (user == orderUser1) {
                Query nativeQuery = entityManager.createNativeQuery("select b.BILLID as BILL_ID,  u.USERNAME as USERNAME, m.ITEM as ITEM,  d.QUANTITY as QTY, m.PRICE as PRICE, d.ITEM_TOTALPRICE as ITEM_TOTALPRICE,b.BILL_AMOUNT as BILL_AMOUNT from menu m, orders o, ORDER_DETAILS d, users u , BILL b where m.menu_id=d.menu_id  and o.ORDER_ID=d.ORDER_ID and u.USERNAME=o.USERNAME  \n" +
                        "                        and b.ORDER_ID=O.ORDER_ID\n" +
                        "                        and o.ORDER_ID=1", "BillDTOMapping");
                //nativeQuery.setParameter(1, billID);
                List<BillDetailsDTO> list =  nativeQuery.getResultList();
                Map<Integer, Map<String, Map<Double, List<BillDetailsDTO>>>> map = list.stream()
                        .collect(Collectors.groupingBy(BillDetailsDTO::getBILL_ID,
                                Collectors.groupingBy(BillDetailsDTO::getUSERNAME,
                                        Collectors.groupingBy(BillDetailsDTO::getBILL_AMOUNT))));
                return map;
            } else {
                System.out.println("Bill ID: " + billID + "does not belongs to you");
                return OtherBill;
            }
        } else {
            System.out.println("Bill ID: " + billID + "does not exist");
            return listBillNotFound;
        }
    }

}

