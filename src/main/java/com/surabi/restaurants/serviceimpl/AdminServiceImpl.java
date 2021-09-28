package com.surabi.restaurants.serviceimpl;

import com.surabi.restaurants.entity.Bill;
import com.surabi.restaurants.entity.User;
import com.surabi.restaurants.repository.UserRepository;
import com.surabi.restaurants.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AdminServiceImpl implements AdminService
{
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;


    @Override
    public String CreateUser(User user) {
        String encodedPassword  = passwordEncoder.encode(user.getPassword());
        user.setEnabled(Boolean.TRUE);
        user.setPassword(encodedPassword);
        user.setUsername(user.getUsername());
        user.setAuthority(user.getAuthority());
        userRepository.save(user);
        return  "User created successfully";
    }

    @Override
    public String UpdateUser(User user) {
        String encodedPassword  = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return "User "+user.getUsername()+" updated successfully";
    }

    @Override
    public String deleteUser(String userName) {
        userRepository.deleteById(userName);
        return userName +" deleted successfully from DB";
    }

    @Override
    public List<Object[]> totolSellByMonth(int monthID) {
        return null;
    }

    @Override
    public List<Bill> viewTodaysBills() {
        return null;
    }
}
