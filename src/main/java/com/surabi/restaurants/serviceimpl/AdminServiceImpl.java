package com.surabi.restaurants.serviceimpl;

import com.surabi.restaurants.entity.Bill;
import com.surabi.restaurants.entity.User;
import com.surabi.restaurants.repository.BillRepository;
import com.surabi.restaurants.repository.UserRepository;
import com.surabi.restaurants.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    PasswordEncoder passwordEncoder;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BillRepository billRepository;


    @Override
    public String CreateUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setEnabled(Boolean.TRUE);
        user.setPassword(encodedPassword);
        user.setUsername(user.getUsername());
        user.setAuthority(user.getAuthority());
        userRepository.save(user);
        return "User created successfully";
    }

    @Override
    public String UpdateUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return "User " + user.getUsername() + " updated successfully";
    }

    @Override
    public String deleteUser(String userName) {

        userRepository.deleteById(userName);
        return userName + " deleted successfully from DB";

    }

    @Override
    public double totalSellByMonth(int monthID) {
        Query nativeQuery = entityManager.createNativeQuery("select sum(bill_amount) from bill where month(bill_date)=?1");
        nativeQuery.setParameter(1, monthID);
        List amount = nativeQuery.getResultList();
        double amt= (double) amount.get(0);
        return amt;
    }

    @Override
    public List<Object[]> viewTodayBills() {
        Query nativeQuery = entityManager.createNativeQuery("select distinct b.ORDER_ID,  u.USERNAME, b.BILL_AMOUNT, b.BILL_DATE \n" +
                "from menu m, orders o, users u , BILL b where  \n" +
                "u.USERNAME=o.USERNAME and b.ORDER_ID=O.ORDER_ID and CAST(BILL_DATE as DATE)=TODAY");
        return nativeQuery.getResultList();
    }
}
