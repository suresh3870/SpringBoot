package com.surabi.restaurants.repository;

import com.surabi.restaurants.entity.Menu;
import com.surabi.restaurants.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
