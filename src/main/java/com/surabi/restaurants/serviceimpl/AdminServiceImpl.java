package com.surabi.restaurants.serviceimpl;

import com.surabi.restaurants.DTO.BillDetailsDTO;
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
import java.util.Map;
import java.util.stream.Collectors;

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
    public Object viewTodayBills() {
        Query nativeQuery = entityManager.createNativeQuery("select b.BILLID as BILL_ID,  u.USERNAME as USERNAME, m.ITEM as ITEM,  d.QUANTITY as QTY, m.PRICE as PRICE, d.ITEM_TOTALPRICE as ITEM_TOTALPRICE,b.BILL_AMOUNT as BILL_AMOUNT from menu m, orders o, ORDER_DETAILS d, users u , BILL b where m.menu_id=d.menu_id  and o.ORDER_ID=d.ORDER_ID and u.USERNAME=o.USERNAME  \n" +
                        "                        and b.ORDER_ID=O.ORDER_ID\n" +
                        "                        and CAST(b.BILL_DATE as DATE)=TODAY", "BillDTOMapping" );
        List<BillDetailsDTO> list =  nativeQuery.getResultList();
        Map<Integer, Map<String, Map<Double, List<BillDetailsDTO>>>> map = list.stream()
                .collect(Collectors.groupingBy(BillDetailsDTO::getBILL_ID,
                        Collectors.groupingBy(BillDetailsDTO::getUSERNAME,
                                Collectors.groupingBy(BillDetailsDTO::getBILL_AMOUNT))));
        return map;
    }
}
