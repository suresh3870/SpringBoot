package com.surabi.restaurants.serviceimpl;

import com.surabi.restaurants.Enum.Authority;
import com.surabi.restaurants.entity.User;
import com.surabi.restaurants.DTO.UserDTO;
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
    public String save(UserDTO userDTO) {
        User user=new User();
        String encodedPassword  = passwordEncoder.encode(userDTO.getPassword());
        user.setEnabled(Boolean.TRUE);
        user.setPassword(encodedPassword);
        user.setUsername(userDTO.getUsername());
        user.setAuthority(Authority.USER);
        if (!userRepository.existsById(userDTO.getUsername())){
            userRepository.save(user);
            return  "User created successfully";
        }
        return  "User cant be saved! Name already exist";
    }
}
