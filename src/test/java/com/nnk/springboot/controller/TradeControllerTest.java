package com.nnk.springboot.controller;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TradeController.class)
public class TradeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TradeServiceImpl tradeService;

	private Trade trade;

	@BeforeEach
	void setUp() {
		trade = new Trade();
		trade.setTradeId(1);
		trade.setAccount("Test Account");
		trade.setType("Test Type");
		trade.setBuyQuantity(10.0);
	}

	@WithMockUser(username = "user")
	@Test
	void testAddTrade_Valid() throws Exception {
		mockMvc.perform(post("/trade/validate").param("account", "Test Account").param("type", "Test Type")
				.param("buyQuantity", "10.0").with(csrf()).contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(status().isOk()).andExpect(view().name("trade/list"));

		verify(tradeService, times(1)).saveTrade(any(Trade.class));
	}

	@WithMockUser(username = "user")
	@Test
	void testAddTrade_Invalid() throws Exception {
		mockMvc.perform(post("/trade/validate").param("account", "")
				.with(csrf()).contentType(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(status().isOk())
				.andExpect(view().name("trade/add"));

		verify(tradeService, never()).saveTrade(any());
	}

	@WithMockUser(username = "user")
	@Test
	void testUpdateTrade_Valid() throws Exception {
		mockMvc.perform(post("/trade/update/1").param("account", "Updated Account").param("type", "Updated Type")
				.param("buyQuantity", "20.0").with(csrf()).contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/trade/list"));

		verify(tradeService, times(1)).saveTrade(any(Trade.class));
	}

	@WithMockUser(username = "user")
	@Test
	void testUpdateTrade_Invalid() throws Exception {
		mockMvc.perform(post("/trade/update/1").param("account", "")
				.param("type", "").with(csrf()).contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(status().isOk()).andExpect(view().name("trade/update"));

		verify(tradeService, never()).saveTrade(any());
	}

	@WithMockUser(username = "user")
	@Test
	void testDeleteTrade_Success() throws Exception {
		when(tradeService.getTradeById(1)).thenReturn(trade);

		mockMvc.perform(get("/trade/delete/1").with(csrf())).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/trade/list"));

		verify(tradeService, times(1)).deleteTradeById(1);
	}
}
