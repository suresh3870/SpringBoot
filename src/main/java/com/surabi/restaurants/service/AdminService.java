package com.surabi.restaurants.service;

import com.surabi.restaurants.DTO.BillDTO;
import com.surabi.restaurants.entity.User;

import java.util.List;
import java.util.Optional;

public interface AdminService {

    String CreateUser(User user);

    String UpdateUser(User user);

    String deleteUser(String userName);

    double totalSellByMonth(int monthID);

    List<BillDTO>  viewTodayBills();
    public Optional<User> findUser(String userName);
}
