package com.surabi.restaurants.service;

import com.surabi.restaurants.entity.Bill;
import com.surabi.restaurants.entity.User;

import java.util.List;

public interface AdminService {

    String CreateUser(User user);

    String UpdateUser(User user);

    String deleteUser(String userName);

    List<Object[]> totolSellByMonth(int monthID);

    List<Bill> viewTodaysBills();
}
