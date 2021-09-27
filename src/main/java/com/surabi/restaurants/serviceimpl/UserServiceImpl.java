package com.surabi.restaurants.serviceimpl;

import com.surabi.restaurants.entity.User;
import com.surabi.restaurants.repository.UserRepository;
import com.surabi.restaurants.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    static final String auth="USER";
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public String save(User user) {
        String encodedPassword  = passwordEncoder.encode(user.getPassword());
        user.setEnabled(Boolean.TRUE);
        user.setPassword(encodedPassword);
        user.setUsername(user.getUsername());
        user.setAuthority(auth);
        userRepository.save(user);
        return  "User created successfully";
    }
}
