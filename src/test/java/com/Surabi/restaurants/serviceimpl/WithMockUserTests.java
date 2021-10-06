package com.surabi.restaurants.serviceimpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class WithMockUserTests {
    @Test
    //@WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    public void getMessageWithMockUserCustomAuthorities() {

    }

}