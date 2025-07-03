package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.Rating;

public interface RatingService {
	List<Rating> getAllRatings();

	Rating getRatingById(Integer id);

	Rating saveRating(Rating rating);

	void deleteRatingById(Integer id);

}
