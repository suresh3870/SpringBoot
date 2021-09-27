package com.surabi.restaurants.service;

import com.surabi.restaurants.entity.Menu;
import com.surabi.restaurants.entity.User;
import com.surabi.restaurants.entity.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {

    String save(UserDTO userDTO);
}