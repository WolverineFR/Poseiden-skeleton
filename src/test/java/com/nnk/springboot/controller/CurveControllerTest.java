package com.nnk.springboot.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(CurveController.class)
public class CurveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurvePointServiceImpl curvePointService;

    private CurvePoint curvePoint;

    @BeforeEach
    void setup() {
        curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(10);
        curvePoint.setTerm(20.0);
        curvePoint.setValue(30.0);
    }

    @WithMockUser(username = "user", roles = "USER")
    @Test
    void testAddCurvePoint_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/validate")
                .param("curveId", "10")
                .param("term", "20.0")
                .param("value", "30.0")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"));

        verify(curvePointService, times(1)).saveCurvePoint(any(CurvePoint.class));
    }

    @WithMockUser(username = "user", roles = "USER")
    @Test
    void testAddCurvePoint_Invalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/validate")
                .param("curveId", "")
                .param("term", "-1")
                .param("value", "")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));

        verify(curvePointService, never()).saveCurvePoint(any());
    }

    @WithMockUser(username = "user", roles = "USER")
    @Test
    void testUpdateCurvePoint_Success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/update/1")
                .param("curveId", "15")
                .param("term", "25.0")
                .param("value", "35.0")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(curvePointService, times(1)).saveCurvePoint(any(CurvePoint.class));
    }

    @WithMockUser(username = "user", roles = "USER")
    @Test
    void testUpdateCurvePoint_Invalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/update/1")
                .param("curveId", "")
                .param("term", "")
                .param("value", "")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"));

        verify(curvePointService, never()).saveCurvePoint(any());
    }

    @WithMockUser(username = "user", roles = "USER")
    @Test
    void testDeleteCurvePoint_Success() throws Exception {
        when(curvePointService.getCurveById(1)).thenReturn(curvePoint);

        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/delete/1").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));

        verify(curvePointService, times(1)).deleteCurvePointById(1);
    }
}
