package com.surabi.restaurants.serviceimpl;

import com.surabi.restaurants.DTO.UserDTO;
import com.surabi.restaurants.Enum.Authority;
import com.surabi.restaurants.entity.User;
import com.surabi.restaurants.repository.UserRepository;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserRegistrationServiceImplTest {
    @InjectMocks
    UserRegistrationServiceImpl userRegistrationServiceImpl;
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    @BeforeClass
    public static void setupBeforeAllTests() {
        //UserServiceImpl userServiceImpl = new UserServiceImpl();

    }

    @Test
    public void shouldRegisterUser() {
        User user= new User();
        User newUser=new User("Ramesh","Ramesh",true,Authority.ROLE_USER);
        UserDTO userDTO = new UserDTO(newUser.getUsername(), newUser.getPassword());
        //userRepository = Mockito.mock(UserRepository.class);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(newUser);
        String result = userRegistrationServiceImpl.saveUser(userDTO);
        Assert.assertEquals("User created successfully", result);
    }

    @Test
    public void shouldNotRegisterUserAsAlreadyExist() {
        User user= new User();
        User newUser=new User("Ramesh","",true,Authority.ROLE_USER);
        UserDTO userDTO = new UserDTO(newUser.getUsername(), newUser.getPassword());
        //userRepository = Mockito.mock(UserRepository.class);
        Mockito.when(userRepository.existsById(newUser.getUsername())).thenReturn(true);
        String result = userRegistrationServiceImpl.saveUser(userDTO);
        Assert.assertEquals("User cant be saved! Name already exist", result);
    }
}