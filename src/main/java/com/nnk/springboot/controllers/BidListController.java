package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListServiceImpl;

import java.util.List;

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
public class BidListController {

	private static final Logger logger = LogManager.getLogger(BidListController.class);

	@Autowired
	private BidListServiceImpl bidListServiceImpl;

	/**
	 * Affiche la liste des BidList
	 */
	@RequestMapping("/bidList/list")
	public String home(Model model) {
		model.addAttribute("bidLists", bidListServiceImpl.getAllBids());
		return "bidList/list";
	}

	/**
	 * Affiche le formulaire d'ajout de bid
	 */
	@GetMapping("/bidList/add")
	public String addBidForm(BidList bid) {
		return "bidList/add";
	}

	/**
	 * Valide et enregistre un nouveau bid
	 */
	@PostMapping("/bidList/validate")
	public String validate(@Valid BidList bid, BindingResult result, Model model) {
		try {
			if (bid.getAccount() == null || bid.getAccount().trim().isEmpty() || bid.getType() == null
					|| bid.getType().trim().isEmpty() || bid.getBidQuantity() == null) {
				throw new IllegalArgumentException("Les champs ne doivent pas être vides");
			}

			if (result.hasErrors()) {
				logger.warn("Ajout impossible, les données sont incorrectes pour ce bid");
				return "bidList/add";
			}

			bidListServiceImpl.saveBid(bid);
			model.addAttribute("bidLists", bidListServiceImpl.getAllBids());
			logger.info("Les données ont été ajoutées avec succès pour le bid : {}", bid.getAccount());
			return "bidList/list";

		} catch (IllegalArgumentException e) {
			logger.warn("Ajout échoué : {}", e.getMessage());
			model.addAttribute("errorMessage", e.getMessage());
			return "bidList/add";
		}
	}

	/**
	 * Affiche le formulaire de mise à jour d'un bid
	 */
	@GetMapping("/bidList/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		BidList bid = bidListServiceImpl.getBidById(id);
		model.addAttribute("bidList", bid);
		return "bidList/update";
	}

	/**
	 * Met à jour un bid existant
	 */
	@PostMapping("/bidList/update/{id}")
	public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList, BindingResult result, Model model) {
		try {
			if (bidList.getAccount() == null || bidList.getAccount().trim().isEmpty() || bidList.getType() == null
					|| bidList.getType().trim().isEmpty() || bidList.getBidQuantity() == null) {
				throw new IllegalArgumentException("Les champs ne doivent pas être vides");
			}

			if (result.hasErrors()) {
				logger.warn("Modification impossible, les données sont incorrectes pour ce bid");
				return "bidList/update";
			}

			bidList.setBidListId(id);
			bidListServiceImpl.saveBid(bidList);
			logger.info("Les données ont été modifiées avec succès pour le bid : {}", bidList.getAccount());
			return "redirect:/bidList/list";

		} catch (IllegalArgumentException e) {
			logger.warn("Mise à jour échouée : {}", e.getMessage());
			model.addAttribute("errorMessage", e.getMessage());
			return "bidList/update";
		}
	}

	/**
	 * Supprime un bid par son ID
	 */
	@GetMapping("/bidList/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) {
		BidList bid = bidListServiceImpl.getBidById(id);
		bidListServiceImpl.deleteBidById(bid.getBidListId());
		model.addAttribute("bidLists", bidListServiceImpl.getAllBids());
		logger.info("Le bid {} à bien été supprimé.", bid.getAccount());
		return "redirect:/bidList/list";
	}
}
