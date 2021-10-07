package com.surabi.restaurants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@SpringBootTest
@PropertySource("classpath:application.properties ")
public class AuthenticationTest {


    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;
    RequestBuilder userBuilder = formLogin().user("user").password("user");
    RequestBuilder adminBuilder = formLogin().user("admin").password("admin");
    RequestBuilder wronguserBuilder = formLogin().user("user").password("user1");
    RequestBuilder wrongadminBuilder = formLogin().user("admin").password("admin1");
    RequestBuilder logout = logout();

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilter(springSecurityFilterChain)
                .build();

    }

    @Test
    public void testSuccessfulLoginwithUserRole() throws Exception {
        mvc.perform(userBuilder)
                .andDo(print())
                .andExpect(redirectedUrl("/"));
    }
    @Test
    public void testSuccessfulLoginwithAdminRole() throws Exception {
        mvc.perform(adminBuilder)
                .andDo(print())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void testSuccessfulLogout() throws Exception {
        mvc.perform(logout)
                .andDo(print())
                .andExpect(redirectedUrl("/login?logout"));
    }
    @Test
    public void shouldFailduetoWrongAdminCred() throws Exception {
        mvc.perform(wrongadminBuilder)
                .andDo(print())
                .andExpect(redirectedUrl("/login?error"));
    }

    @Test
    public void shouldFailduetoWrongUserCred() throws Exception {
        mvc.perform(wronguserBuilder)
                .andDo(print())
                .andExpect(redirectedUrl("/login?error"));
    }

}

