package com.nnk.springboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

/**
 * Service métier pour la gestion des ratings. Implémente les opérations CRUD en
 * utilisant le repository {@link RatingRepository}.
 */
@Service
public class RatingServiceImpl implements RatingService {

	@Autowired
	private RatingRepository ratingRepository;

	@Autowired
	public RatingServiceImpl(RatingRepository ratingRepository) {
		this.ratingRepository = ratingRepository;
	}

	/**
	 * Récupère la liste complète des ratings.
	 * 
	 * @return liste de tous les ratings
	 */
	@Override
	public List<Rating> getAllRatings() {
		return ratingRepository.findAll();
	}

	/**
	 * Récupère un rating par son ID.
	 * 
	 * @param id identifiant du rating
	 * @return rating trouvé
	 */
	@Override
	public Rating getRatingById(Integer id) {
		return ratingRepository.getById(id);
	}

	/**
	 * Sauvegarde ou met à jour un rating.
	 * 
	 * @param rating l'objet à enregistrer
	 * @return l'objet persisté
	 */
	@Override
	public Rating saveRating(Rating rating) {
		return ratingRepository.save(rating);
	}

	/**
	 * Supprime un rating par son ID.
	 * 
	 * @param id identifiant du rating à supprimer
	 */
	@Override
	public void deleteRatingById(Integer id) {
		ratingRepository.deleteById(id);
	}

}
