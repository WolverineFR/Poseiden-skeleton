package com.nnk.springboot;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

@SpringBootTest
public class RatingTest {

	@Autowired
	private RatingRepository ratingRepository;

	@Test
	public void RatingTest() {

		Rating rating = new Rating();
		rating.setOrderNumber(10);
		rating.setMoodysRating("Moodys");
		rating.setSandPRating("SandP");
		rating.setFitchRating("fitch");

		// Save
		rating = ratingRepository.save(rating);
		assertNotNull(rating.getId());
		assertTrue(rating.getOrderNumber() == 10);

		// Update
		rating.setOrderNumber(20);
		rating = ratingRepository.save(rating);
		assertTrue(rating.getOrderNumber() == 20);

		// Find
		List<Rating> listResult = ratingRepository.findAll();
		assertTrue(listResult.size() > 0);

		// Delete
		Integer id = rating.getId();
		ratingRepository.delete(rating);
		Optional<Rating> ratingList = ratingRepository.findById(id);
		assertFalse(ratingList.isPresent());
	}
}
