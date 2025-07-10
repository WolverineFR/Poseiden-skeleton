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

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListServiceImpl;

@WebMvcTest(BidListController.class)
public class BidListControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BidListServiceImpl bidListService;

	private BidList bid;

	@BeforeEach
	void setup() {
		bid = new BidList();
		bid.setBidListId(1);
		bid.setAccount("Account Test");
		bid.setType("Type Test");
		bid.setBidQuantity(10.0);
	}

	@WithMockUser(username = "user", roles = "USER")
	@Test
	void testAddBid_Success() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/bidList/validate").param("account", "Account Test")
				.param("type", "Type Test").param("bidQuantity", "20").with(csrf())
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(status().isOk())
				.andExpect(view().name("bidList/list"));

		verify(bidListService, times(1)).saveBid(any(BidList.class));
	}

	@WithMockUser(username = "user", roles = "USER")
	@Test
	void testAddBid_Invalid() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/bidList/validate").param("account", "").param("type", "")
				.param("bidQuantity", "-1").with(csrf()).contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(status().isOk()).andExpect(view().name("bidList/add"));

		verify(bidListService, never()).saveBid(any());
	}

	@WithMockUser(username = "user", roles = "USER")
	@Test
	void testUpdateBid_Success() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/bidList/update/1").param("account", "Updated Account")
				.param("type", "Updated Type").param("bidQuantity", "15").with(csrf())
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/bidList/list"));

		verify(bidListService, times(1)).saveBid(any(BidList.class));
	}

	@WithMockUser(username = "user", roles = "USER")
	@Test
	void testUpdateBid_Invalid() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/bidList/update/1").param("account", "").param("type", "")
				.param("bidQuantity", "-1").with(csrf()).contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(status().isOk()).andExpect(view().name("bidList/update"));

		verify(bidListService, never()).saveBid(any());
	}

	@WithMockUser(username = "user", roles = "USER")
	@Test
	public void testDeleteBid_Success() throws Exception {
		when(bidListService.getBidById(1)).thenReturn(bid);

		mockMvc.perform(MockMvcRequestBuilders.get("/bidList/delete/1").with(csrf()))

				.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/bidList/list"));

		verify(bidListService, times(1)).deleteBidById(1);
	}

}
