package com.surabi.restaurants.serviceimpl;

import com.surabi.restaurants.DTO.BillDetailsDTO;
import com.surabi.restaurants.DTO.OrderBulkDTO;
import com.surabi.restaurants.entity.*;
import com.surabi.restaurants.repository.BillRepository;
import com.surabi.restaurants.repository.MenuRepository;
import com.surabi.restaurants.repository.OrderDetailsRepository;
import com.surabi.restaurants.repository.OrderRepository;
import com.surabi.restaurants.response.APIResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.OngoingStubbing;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class RestaurantServiceImplTest {

    @InjectMocks
    RestaurantServiceImpl restaurantServiceImpl;
    @Mock
    MenuRepository menuRepository;
    @Mock
    OrderRepository orderRepository;
    @Mock
    UserLoggedDetailsImpl userLoggedDetailsImpl;
    @Mock
    OrderDetailsRepository orderDetailsRepository;
    @Mock
    BillRepository billRepository;
    @Mock
    Query nativeQuery;
    @Mock
    EntityManager entityManager;


    Menu menu = new Menu(1, "dosa", 200);
    Menu menu1 = new Menu(2, "dosa", 200);
    Orders orders = new Orders();
    Optional<Menu> menuOptional = Optional.of(menu);
    Optional<Menu> menuNotfound = Optional.of(new Menu("Menu not found"));
    List<OrderBulkDTO> orderList = new ArrayList<>();
    OrderBulkDTO orderBulkDTO = new OrderBulkDTO(1, 1);
    Date date = new Date();
    User user = new User();
    Bill bill = new Bill();
    BillDetailsDTO billDetailsDTO = new BillDetailsDTO();


    @Test
    public void shouldviewBillByBillID() {
        bill.setBillID(1);
        orders.setOrderId(1);
        user.setUsername("ram");
        orders.setUser(user);
        MockedStatic<UserLoggedDetailsImpl> theMock2 = Mockito.mockStatic(UserLoggedDetailsImpl.class);
        theMock2.when(UserLoggedDetailsImpl::getMyDetails).thenReturn("ram");
        Mockito.when(orderRepository.getOne(bill.getBillID())).thenReturn(orders);
        Mockito.when(billRepository.existsById(orders.getOrderId())).thenReturn(true);
        Mockito.when(entityManager.createNativeQuery("select b.BILLID as BILL_ID,  u.USERNAME as USERNAME, m.ITEM as ITEM,  d.QUANTITY as QTY, m.PRICE as PRICE, d.ITEM_TOTALPRICE as ITEM_TOTALPRICE,b.BILL_AMOUNT as BILL_AMOUNT from menu m, orders o, ORDER_DETAILS d, users u , BILL b where m.menu_id=d.menu_id  and o.ORDER_ID=d.ORDER_ID and u.USERNAME=o.USERNAME  \n" +
                "                        and b.ORDER_ID=O.ORDER_ID\n" +
                "                        and o.ORDER_ID=1", "BillDTOMapping")).thenReturn(nativeQuery);
        billDetailsDTO.setBILL_ID(1);
        billDetailsDTO.setBILL_AMOUNT(100);
        billDetailsDTO.setITEM("dosa");
        billDetailsDTO.setPRICE(100);
        billDetailsDTO.setITEM_TOTALPRICE(100);
        billDetailsDTO.setQTY(100);
        billDetailsDTO.setUSERNAME("ram");
        List<BillDetailsDTO> list1 = new ArrayList<>();
        list1.add(billDetailsDTO);
        list1.add(billDetailsDTO);
        Mockito.when(nativeQuery.getResultList()).thenReturn(list1);
        APIResponse<List<BillDetailsDTO>> result = restaurantServiceImpl.viewMyBill(bill.getBillID());
        theMock2.close();
    }


    @Test
    public void shouldgetMenuById() {
        Mockito.when(menuRepository.existsById(menu.getMenuId())).thenReturn(true);
        Mockito.when(menuRepository.findById(menu.getMenuId())).thenReturn(menuOptional);
        Optional<Menu> result = restaurantServiceImpl.getMenuById(menu.getMenuId());
        Assert.assertEquals(menuOptional, result);
    }


    @Test
    public void shouldThrowMenuNotFound() {
        Mockito.when(menuRepository.existsById(menu.getMenuId())).thenReturn(false);
        Mockito.when(menuRepository.existsById(menu.getMenuId())).thenReturn(false);
        Optional<Menu> result = restaurantServiceImpl.getMenuById(menu.getMenuId());
        Assert.assertEquals(menuNotfound, result);
    }

    @Test
    public void shouldSelectOrder() {
        orderList.add(orderBulkDTO);
        user.setUsername("ram");
        Orders orders1 = new Orders(1, date, user);
        OrderDetails orderDetails = new OrderDetails(1, orders1, menu, 2, 30);
        Mockito.when(menuRepository.getOne(menu.getMenuId())).thenReturn(menu);
        Mockito.when(orderRepository.save(Mockito.any(Orders.class))).thenReturn(orders1);
        Mockito.when(orderDetailsRepository.save(Mockito.any(OrderDetails.class))).thenReturn(orderDetails);
        MockedStatic<UserLoggedDetailsImpl> theMock1 = Mockito.mockStatic(UserLoggedDetailsImpl.class);
        theMock1.when(UserLoggedDetailsImpl::getMyDetails).thenReturn("ram");
        String result = restaurantServiceImpl.createBulkItem(orderList);
        Assert.assertEquals("Order ID: 1 has been created successfully", result);
        theMock1.close();

    }


    @Test
    public void billGenerationShouldThrowOrderNotFound() {
        orders.setOrderId(1);
        Mockito.when(orderRepository.existsById(orders.getOrderId())).thenReturn(false);
        String result = restaurantServiceImpl.checkOut(1);
        Assert.assertEquals("Order ID: 1 does not exist", result);
    }

    @Test
    public void billGenerationShouldThrowBillAlreadyGenerated() {
        orders.setOrderId(1);
        Mockito.when(entityManager.createNativeQuery("select sum(ITEM_TOTALPRICE) from ORDER_DETAILS where ORDER_ID=?1")).thenReturn(nativeQuery);
        List list = new ArrayList<>();
        list.add(100.0);
        Mockito.when(nativeQuery.setParameter(1, orders.getOrderId())).thenReturn(nativeQuery);
        Mockito.when(nativeQuery.getResultList()).thenReturn(list);
        Mockito.when(orderRepository.existsById(orders.getOrderId())).thenReturn(true);
        Mockito.when(billRepository.existsById(orders.getOrderId())).thenReturn(true);
        String result = restaurantServiceImpl.checkOut(1);
        Assert.assertEquals("Bill with given order already generated", result);
    }

    @Test
    public void shouldGenerateBill() {
        orders.setOrderId(1);
        user.setUsername("ram");
        orders.setUser(user);

        Mockito.when(entityManager.createNativeQuery("select sum(ITEM_TOTALPRICE) from ORDER_DETAILS where ORDER_ID=?1")).thenReturn(nativeQuery);
        List list = new ArrayList<>();
        list.add(100.0);
        bill.setBillID(1);
        Mockito.when(nativeQuery.setParameter(1, orders.getOrderId())).thenReturn(nativeQuery);
        Mockito.when(nativeQuery.getResultList()).thenReturn(list);
        Mockito.when(orderRepository.existsById(orders.getOrderId())).thenReturn(true);
        Mockito.when(billRepository.existsById(orders.getOrderId())).thenReturn(false);
        MockedStatic<UserLoggedDetailsImpl> theMock = Mockito.mockStatic(UserLoggedDetailsImpl.class);
        theMock.when(UserLoggedDetailsImpl::getMyDetails).thenReturn("ram");
        theMock.when(UserLoggedDetailsImpl::getUserRole).thenReturn("ADMIN");
        Mockito.when(orderRepository.getOne(orders.getOrderId())).thenReturn(orders);
        Mockito.when(billRepository.save(Mockito.any(Bill.class))).thenReturn(bill);
        String result = restaurantServiceImpl.checkOut(1);
        Assert.assertEquals("Bill saved with ID 1", result);
        theMock.close();
    }
}