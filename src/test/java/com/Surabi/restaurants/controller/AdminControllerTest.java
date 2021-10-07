package com.surabi.restaurants.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.surabi.restaurants.Enum.Authority;
import com.surabi.restaurants.entity.User;
import com.surabi.restaurants.service.AdminService;
import com.surabi.restaurants.service.RestaurantsService;
import com.surabi.restaurants.service.UserRegistrationService;
import org.h2.tools.Server;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.sql.SQLException;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;


@WebMvcTest(AdminController.class)
@AutoConfigureTestDatabase
public class AdminControllerTest {

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
    @WithMockUser(username = "suresh", roles = {"ADMIN"})
    public void shouldCreateUserByAdmin() throws Exception {
        //User user = new User("suresh1", "ss", true, Authority.ROLE_ADMIN);
        Mockito.when(adminService.CreateUser(Mockito.any(User.class))).thenReturn("User created successfully");

        MvcResult responseOfRequest = mockMvc.perform(get("/surabi/admin/CreateUser").with(csrf()))
                .andExpect(status().isOk()).andReturn();
        String response = responseOfRequest.getResponse().getContentAsString();
        Assert.assertEquals(response, "User created successfully");

    }

    @Test
    @WithMockUser(username = "suresh", roles = {"USER"})
    public void userShouldNotBeabletoCreateUseraccountbyAdminPage() throws Exception {
        //User user = new User("suresh1", "ss", true, Authority.ROLE_ADMIN);
        Mockito.when(adminService.CreateUser(Mockito.any(User.class))).thenReturn("User created successfully");

        MvcResult responseOfRequest = mockMvc.perform(get("/surabi/admin/CreateUser").with(csrf()))
                .andExpect(status().is4xxClientError()).andReturn();


    }
}