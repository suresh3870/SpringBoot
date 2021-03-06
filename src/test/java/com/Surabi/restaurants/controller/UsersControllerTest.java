package com.surabi.restaurants.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.surabi.restaurants.entity.User;
import com.surabi.restaurants.service.AdminService;
import com.surabi.restaurants.service.RestaurantsService;
import com.surabi.restaurants.service.UserRegistrationService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AdminController.class)
@AutoConfigureTestDatabase
public class UsersControllerTest {

    private static ObjectMapper mapper = new ObjectMapper();
    @MockBean
    UserRegistrationService userRegistrationService;

    @MockBean
    RestaurantsService restaurantsService;

    @MockBean
    AdminService adminService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;


    @Test
    @WithMockUser(username = "suresh", roles = {"USER"})
    public void userShouldNotBeAllowedCreateUseraccountbyAdminPage() throws Exception {
        //User user = new User("suresh1", "ss", true, Authority.ROLE_ADMIN);
        Mockito.when(adminService.CreateUser(Mockito.any(User.class))).thenReturn("User created successfully");

        MvcResult responseOfRequest = mockMvc.perform(get("/surabi/admin/CreateUser").with(csrf()))
                .andExpect(status().is4xxClientError()).andReturn();

    }
}