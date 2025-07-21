package com.nnk.springboot.controller;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;

import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setUsername("Jean");
        user.setPassword("Azerty1!");
        user.setFullname("Jean Dupont");
        user.setRole("USER");
    }

    @WithMockUser
    @Test
    void testHome() throws Exception {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("users"));
    }

    @WithMockUser
    @Test
    void testAddUserForm() throws Exception {
        mockMvc.perform(get("/user/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    @WithMockUser
    @Test
    void testValidate_ValidUser() throws Exception {
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        mockMvc.perform(post("/user/validate")
                .param("username", "Jean")
                .param("password", "Azerty1!")
                .param("fullname", "Jean Dupont")
                .param("role", "USER")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        verify(userRepository, times(1)).save(any(User.class));
    }

    @WithMockUser
    @Test
    void testValidate_InvalidUser() throws Exception {
        mockMvc.perform(post("/user/validate")
                .param("username", "testuser")
                .param("password", "wrongpass") 
                .param("fullname", "Test User")
                .param("role", "USER")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));

        verify(userRepository, never()).save(any(User.class));
    }

    @WithMockUser
    @Test
    void testShowUpdateForm() throws Exception {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/user/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"))
                .andExpect(model().attributeExists("user"));
    }

    @WithMockUser
    @Test
    void testUpdateUser_Valid() throws Exception {
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        mockMvc.perform(post("/user/update/1")
                .param("username", "EditedUser")
                .param("password", "NewPassWord12!")
                .param("fullname", "Updated User")
                .param("role", "ADMIN")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        verify(userRepository, times(1)).save(any(User.class));
    }

    @WithMockUser
    @Test
    void testUpdateUser_Invalid() throws Exception {
        mockMvc.perform(post("/user/update/1")
                .param("username", "")
                .param("password", "newpassword")
                .param("fullname", "Updated User")
                .param("role", "ADMIN")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"));

        verify(userRepository, never()).save(any(User.class));
    }

    @WithMockUser
    @Test
    void testDeleteUser() throws Exception {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/user/delete/1").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));

        verify(userRepository, times(1)).delete(user);
    }
}
