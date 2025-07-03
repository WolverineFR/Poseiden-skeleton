package com.nnk.springboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

@Service
public class RatingServiceImpl implements RatingService {

	@Autowired
	private RatingRepository ratingRepository;

	@Override
	public List<Rating> getAllRatings() {
		return ratingRepository.findAll();
	}

	@Override
	public Rating getRatingById(Integer id) {
		return ratingRepository.getById(id);
	}

	@Override
	public Rating saveRating(Rating rating) {
		return ratingRepository.save(rating);
	}

	@Override
	public void deleteRatingById(Integer id) {
		ratingRepository.deleteById(id);
	}

}
