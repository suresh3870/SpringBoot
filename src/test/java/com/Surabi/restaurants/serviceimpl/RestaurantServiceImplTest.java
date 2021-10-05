package com.surabi.restaurants.serviceimpl;

import com.surabi.restaurants.DTO.OrderBulkDTO;
import com.surabi.restaurants.entity.*;
import com.surabi.restaurants.repository.*;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

import static org.hamcrest.Matchers.any;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mockStatic;

@RunWith(MockitoJUnitRunner.class)
//@PrepareForTest(UserLoggedDetailsImpl.class)
//@RunWith(PowerMockRunner.class)
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

    //private Query nativeQuery;

    //private EntityManager entityManager;

    //MockedStatic<UserLoggedDetailsImpl> classMock = mockStatic(UserLoggedDetailsImpl.class);

    Menu menu = new Menu(1, "dosa", 200);
    Menu menu1 = new Menu(2, "dosa", 200);
    Orders orders = new Orders();
    Optional<Menu> menuOptional = Optional.of(menu);
    Optional<Menu> menuNotfound = Optional.of(new Menu("Menu not found"));
    List<OrderBulkDTO> orderList= new ArrayList<>();
    OrderBulkDTO orderBulkDTO= new OrderBulkDTO(1,1);
    Date date = new Date();
    User user = new User();
    Bill bill = new Bill();


    @Test
    public void viewAllMenu() {
    }

    @BeforeClass
    public static void beforeClass() throws Exception {

    }

    @Test
    public void shouldgetMenuById() {
        Mockito.when(menuRepository.existsById(menu.getMenuId())).thenReturn(true);
        Mockito.when(menuRepository.findById(menu.getMenuId())).thenReturn(menuOptional);
        Optional<Menu> result = restaurantServiceImpl.getMenuById(menu.getMenuId());
        Assert.assertEquals(menuOptional , result);
    }
    @Test
    public void shouldThrowMenuNotFound() {
        Mockito.when(menuRepository.existsById(menu.getMenuId())).thenReturn(false);
        Mockito.when(menuRepository.existsById(menu.getMenuId())).thenReturn(false);
        Optional<Menu> result = restaurantServiceImpl.getMenuById(menu.getMenuId());
        Assert.assertEquals(menuNotfound , result);
    }

    @Test
    public void shouldSelectOrder() {
        orderList.add(orderBulkDTO);
        user.setUsername("ram");
        Orders orders1 = new Orders(1,date,user);
        OrderDetails orderDetails = new OrderDetails(1,orders1,menu,2,30);
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