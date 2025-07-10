package com.nnk.springboot.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingServiceImpl;

@WebMvcTest(RatingController.class)
public class RatingControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RatingServiceImpl ratingService;

	private Rating rating;

	@BeforeEach
	void setup() {
		rating = new Rating();
		rating.setId(1);
		rating.setMoodysRating("Moody A");
		rating.setSandPRating("S&P A");
		rating.setFitchRating("Fitch A");
		rating.setOrderNumber(10);
	}

	@WithMockUser(username = "user", roles = "USER")
	@Test
	void testAddRating_Success() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/rating/validate").param("moodysRating", "Moody A")
				.param("sandPRating", "S&P A").param("fitchRating", "Fitch A").param("orderNumber", "10").with(csrf())
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(status().isOk())
				.andExpect(view().name("rating/list"));

		verify(ratingService, times(1)).saveRating(any(Rating.class));
	}

	@WithMockUser(username = "user", roles = "USER")
	@Test
	void testAddRating_Invalid() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/rating/validate").param("moodysRating", "")
				.param("sandPRating", "").param("fitchRating", "").param("orderNumber", "-1").with(csrf())
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(status().isOk())
				.andExpect(view().name("rating/add"));

		verify(ratingService, never()).saveRating(any());
	}

	@WithMockUser(username = "user", roles = "USER")
	@Test
	void testUpdateRating_Success() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/rating/update/1").param("moodysRating", "Moody B")
				.param("sandPRating", "S&P B").param("fitchRating", "Fitch B").param("orderNumber", "20").with(csrf())
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/rating/list"));

		verify(ratingService, times(1)).saveRating(any(Rating.class));
	}

	@WithMockUser(username = "user", roles = "USER")
	@Test
	void testUpdateRating_Invalid() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/rating/update/1").param("moodysRating", "")
				.param("sandPRating", "").param("fitchRating", "").param("orderNumber", "-10").with(csrf())
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(status().isOk())
				.andExpect(view().name("rating/update"));

		verify(ratingService, never()).saveRating(any());
	}

	@WithMockUser(username = "user", roles = "USER")
	@Test
	public void testDeleteRating_Success() throws Exception {
		when(ratingService.getRatingById(1)).thenReturn(rating);

		mockMvc.perform(MockMvcRequestBuilders.get("/rating/delete/1").with(csrf()))
				.andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/rating/list"));

		verify(ratingService, times(1)).deleteRatingById(1);
	}
}
