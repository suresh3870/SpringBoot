package com.surabi.restaurants.serviceimpl;

import com.surabi.restaurants.DTO.UserDTO;
import com.surabi.restaurants.Enum.Authority;
import com.surabi.restaurants.entity.User;
import com.surabi.restaurants.repository.UserRepository;
import com.surabi.restaurants.serviceimpl.UserServiceImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    @InjectMocks
    UserServiceImpl userServiceImpl;
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    @BeforeClass
    public static void setupBeforeAllTests() {
        //UserServiceImpl userServiceImpl = new UserServiceImpl();

    }

    @Test
    public void shouldSaveUser() {
        User user= new User();
        User newUser=new User("Ramesh","Ramesh",true,Authority.USER);
        UserDTO userDTO = new UserDTO(newUser.getUsername(), newUser.getPassword());
        //userRepository = Mockito.mock(UserRepository.class);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(newUser);
        String result = userServiceImpl.saveUser(userDTO);
        Assert.assertEquals("User created successfully", result);
    }
}