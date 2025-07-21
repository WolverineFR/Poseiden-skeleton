package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingServiceImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
public class RatingController {

	private static final Logger logger = LogManager.getLogger(RatingController.class);

	@Autowired
	private RatingServiceImpl ratingServiceImpl;

	/**
	 * Affiche la liste des rating
	 */
	@RequestMapping("/rating/list")
	public String home(Model model) {
		model.addAttribute("ratings", ratingServiceImpl.getAllRatings());
		return "rating/list";
	}

	/**
	 * Affiche le formulaire d'ajout de rating
	 */
	@GetMapping("/rating/add")
	public String addRatingForm(Rating rating) {
		return "rating/add";
	}

	/**
	 * Valide et enregistre un nouveau rating
	 */
	@PostMapping("/rating/validate")
	public String validate(@Valid Rating rating, BindingResult result, Model model) {
		try {
			if (rating.getMoodysRating() == null || rating.getMoodysRating().trim().isEmpty()
					|| rating.getSandPRating() == null || rating.getSandPRating().trim().isEmpty()
					|| rating.getFitchRating() == null || rating.getFitchRating().trim().isEmpty()
					|| rating.getOrderNumber() == null) {
				throw new IllegalArgumentException("Les champs ne doivent pas être vides");
			}

			if (result.hasErrors()) {
				logger.warn("Ajout impossible, les données sont incorrect pour ce rating");
				return "rating/add";
			}
			ratingServiceImpl.saveRating(rating);
			model.addAttribute("ratings", ratingServiceImpl.getAllRatings());
			logger.info("Les données sont bien ajouté avec succès pour le rating {}", rating.getMoodysRating());
			return "rating/list";

		} catch (IllegalArgumentException e) {
			logger.warn("Ajout échoué : {}", e.getMessage());
			model.addAttribute("errorMessage", e.getMessage());
			return "rating/add";
		}
	}

	/**
	 * Affiche le formulaire de mise à jour d'un rating
	 */
	@GetMapping("/rating/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		Rating rating = ratingServiceImpl.getRatingById(id);
		model.addAttribute("rating", rating);
		return "rating/update";
	}

	/**
	 * Met à jour un rating existant
	 */
	@PostMapping("/rating/update/{id}")
	public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating, BindingResult result,
			Model model) {
		try {
			if (rating.getMoodysRating() == null || rating.getMoodysRating().trim().isEmpty()
					|| rating.getSandPRating() == null || rating.getSandPRating().trim().isEmpty()
					|| rating.getFitchRating() == null || rating.getFitchRating().trim().isEmpty()
					|| rating.getOrderNumber() == null) {
				throw new IllegalArgumentException("Les champs ne doivent pas être vides");
			}

			if (result.hasErrors()) {
				logger.warn("Modification impossible, les données sont incorrect pour ce rating");
				return "rating/update";
			}
			rating.setId(id);
			ratingServiceImpl.saveRating(rating);
			logger.info("Les données du rating ont bien été modifié avec succès pour le rating {}",
					rating.getMoodysRating());
			return "redirect:/rating/list";

		} catch (IllegalArgumentException e) {
			logger.warn("Ajout échoué : {}", e.getMessage());
			model.addAttribute("errorMessage", e.getMessage());
			return "rating/update";
		}
	}

	/**
	 * Supprime un rating par son ID
	 */
	@GetMapping("/rating/delete/{id}")
	public String deleteRating(@PathVariable("id") Integer id, Model model) {
		Rating rating = ratingServiceImpl.getRatingById(id);
		ratingServiceImpl.deleteRatingById(rating.getId());
		logger.info("Le rating {} à bien été supprimé.", rating.getMoodysRating());
		return "redirect:/rating/list";
	}
}
