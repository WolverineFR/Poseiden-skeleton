package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointServiceImpl;

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
public class CurveController {

	private static final Logger logger = LogManager.getLogger(CurveController.class);

	@Autowired
	private CurvePointServiceImpl curvePointServiceImpl;

	/**
	 * Affiche la liste des CurvePoint
	 */
	@RequestMapping("/curvePoint/list")
	public String home(Model model) {
		model.addAttribute("curvePoints", curvePointServiceImpl.getAllCurves());
		return "curvePoint/list";
	}

	/**
	 * Affiche le formulaire d'ajout du CurvePoint
	 */
	@GetMapping("/curvePoint/add")
	public String addBidForm(CurvePoint curvePoint) {
		return "curvePoint/add";
	}

	/**
	 * Valide et enregistre un nouveau CurvePoint
	 */
	@PostMapping("/curvePoint/validate")
	public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
		try {
			if (curvePoint.getCurveId() == null || curvePoint.getTerm() == null || curvePoint.getValue() == null) {
				throw new IllegalArgumentException("Les champs ne doivent pas être vides");
			}

			if (result.hasErrors()) {
				logger.warn("Ajout impossible, les données sont incorrect pour ce curve point");
				return "curvePoint/add";
			}
			curvePointServiceImpl.saveCurvePoint(curvePoint);
			model.addAttribute("curvePoints", curvePointServiceImpl.getAllCurves());
			logger.info("Les données sont bien ajouté avec succès pour le curvePoint ID numéro : {}",
					curvePoint.getCurveId());
			return "curvePoint/list";

		} catch (IllegalArgumentException e) {
			logger.warn("Ajout échoué : {}", e.getMessage());
			model.addAttribute("errorMessage", e.getMessage());
			return "curvePoint/add";
		}
	}

	/**
	 * Affiche le formulaire de mise à jour d'un CurvePoint
	 */
	@GetMapping("/curvePoint/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		CurvePoint cp = curvePointServiceImpl.getCurveById(id);
		model.addAttribute("curvePoint", cp);
		return "curvePoint/update";
	}

	/**
	 * Met à jour un CurvePoint existant
	 */
	@PostMapping("/curvePoint/update/{id}")
	public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint, BindingResult result,
			Model model) {
		try {
			if (curvePoint.getCurveId() == null || curvePoint.getTerm() == null || curvePoint.getValue() == null) {
				throw new IllegalArgumentException("Les champs ne doivent pas être vides");
			}
			if (result.hasErrors()) {
				logger.warn("Modification impossible, les données sont incorrect pour le curve point avec l'id : {}",
						curvePoint.getId());
				return "curvePoint/update";
			}
			curvePoint.setId(id);
			curvePointServiceImpl.saveCurvePoint(curvePoint);
			logger.info(
					"Les données du curve point ont bien été modifié avec succès pour le curve point avec l'id : {}",
					curvePoint.getId());
			return "redirect:/curvePoint/list";

		} catch (IllegalArgumentException e) {
			logger.warn("Mise à jour échouée : {}", e.getMessage());
			model.addAttribute("errorMessage", e.getMessage());
			return "curvePoint/update";
		}
	}

	/**
	 * Supprime un CurvePoint par son ID
	 */
	@GetMapping("/curvePoint/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) {
		CurvePoint cp = curvePointServiceImpl.getCurveById(id);
		curvePointServiceImpl.deleteCurvePointById(cp.getId());
		model.addAttribute("curvePoints", curvePointServiceImpl.getAllCurves());
		logger.info("Le curve point {} à bien été supprimé.", cp.getId());
		return "redirect:/curvePoint/list";
	}
}
