package com.surabi.restaurants.serviceimpl;

import com.surabi.restaurants.Enum.Authority;
import com.surabi.restaurants.entity.User;
import com.surabi.restaurants.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;


@RunWith(MockitoJUnitRunner.class)
public class AdminServiceImplTest {

    @InjectMocks
    AdminServiceImpl adminServiceImpl;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    User user = new User("suresh1", "ss", true, Authority.ROLE_ADMIN);
    Optional<User> userOptional = Optional.of(user);

    @Test
    public void shouldCreateUserByAdmin() throws Exception {
        Mockito.when(userRepository.existsById(user.getUsername())).thenReturn(false);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        String response = adminServiceImpl.CreateUser(user);
        Assert.assertEquals(response, "User created successfully");

    }

    @Test
    public void shouldThrowUserAlreadyExistIfAdminTry() throws Exception {
        Mockito.when(userRepository.existsById(user.getUsername())).thenReturn(true);
        String response = adminServiceImpl.CreateUser(user);
        Assert.assertEquals(response, "User suresh1 already exist, please update if you wish to change user");

    }

    @Test
    public void shouldDeleteUserByAdmin() throws Exception {
        Mockito.when(userRepository.existsById(user.getUsername())).thenReturn(true);
        String response = adminServiceImpl.deleteUser(user.getUsername());
        Mockito.verify(userRepository).deleteById(user.getUsername());
        Assert.assertEquals(response, "suresh1 deleted successfully from DB");

    }

    @Test
    public void shouldReturnUserDoesNotExistWhenAdminDelete() throws Exception {
        Mockito.when(userRepository.existsById(user.getUsername())).thenReturn(false);
        String response = adminServiceImpl.deleteUser(user.getUsername());
        Assert.assertEquals(response, "User suresh1 does not exist");

    }

    @Test
    public void shouldUpdateUserByAdmin() throws Exception {
        Mockito.when(userRepository.existsById(user.getUsername())).thenReturn(true);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        String response = adminServiceImpl.UpdateUser(user);
        Assert.assertEquals(response, "User suresh1 updated successfully");

    }

    @Test
    public void userUpdateByAdminShouldFailwithUserDoesNotExist() throws Exception {
        Mockito.when(userRepository.existsById(user.getUsername())).thenReturn(false);
        String response = adminServiceImpl.UpdateUser(user);
        Assert.assertEquals(response, "User suresh1 does not exist");

    }

    @Test
    public void shouldFindUserByID() throws Exception {
        Mockito.when(userRepository.existsById(user.getUsername())).thenReturn(true);
        Mockito.when(userRepository.findById(user.getUsername())).thenReturn(userOptional);
        Optional<User> result = adminServiceImpl.findUser(user.getUsername());
        Assert.assertEquals(userOptional, result);

    }

}
