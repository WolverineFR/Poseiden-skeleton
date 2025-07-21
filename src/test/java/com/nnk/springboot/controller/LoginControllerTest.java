package com.nnk.springboot.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void loginSuccess() throws Exception {
        mockMvc.perform(formLogin("/app/login")
                .user("admin")
                .password("admin"))
            .andExpect(authenticated());
    }

    @Test
    public void loginFailure() throws Exception {
        mockMvc.perform(formLogin("/app/login")
                .user("invalidUser")
                .password("wrongPassword"))
            .andExpect(unauthenticated());
    }
}
