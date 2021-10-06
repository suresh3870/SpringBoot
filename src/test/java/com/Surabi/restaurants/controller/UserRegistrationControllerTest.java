package com.surabi.restaurants.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.surabi.restaurants.DTO.UserDTO;
import com.surabi.restaurants.entity.OrderDetails;
import com.surabi.restaurants.service.AdminService;
import com.surabi.restaurants.service.RestaurantsService;
import com.surabi.restaurants.service.UserRegistrationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/*@WebMvcTest: This annotation initializes web MVC related configurations
 * required to write the JUnit test case for controller classes.*/
@WebMvcTest
@AutoConfigureTestDatabase
//@AutoConfigureMockMvc(addFilters = false)
public class UserRegistrationControllerTest {

    private static ObjectMapper mapper = new ObjectMapper();
    /*
     * @MockBean: This annotation creates mocked beans in the spring application
     * context.
     */
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
    public void shouldResister() throws Exception {
        UserDTO userDTO = new UserDTO("ram", "ram");
        Mockito.when(userRegistrationService.saveUser(Mockito.any(UserDTO.class))).thenReturn("User created successfully");
        MvcResult responseOfRequest = mockMvc
                .perform(post("/surabi/usersReg/register").param("User", "userDTO"))
                .andReturn();
        System.out.println(responseOfRequest);
        String response = responseOfRequest.getResponse().getContentAsString();
        assertEquals(response, "User created successfully");


    }

}