package com.surabi.restaurants.service;

import com.surabi.restaurants.DTO.BillDetailsDTO;
import com.surabi.restaurants.entity.User;

import java.util.List;
import java.util.Map;

public interface AdminService {

    String CreateUser(User user);

    String UpdateUser(User user);

    String deleteUser(String userName);

    double totalSellByMonth(int monthID);

    Object viewTodayBills();
}
