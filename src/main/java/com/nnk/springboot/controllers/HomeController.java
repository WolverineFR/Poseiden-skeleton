package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	/**
	 * Affiche la page d'accueil
	 */
	@RequestMapping("/")
	public String home(Model model) {
		return "home";
	}

	/**
	 * Redirige vers la page d'affichage de la liste des bids
	 */
	@RequestMapping("/admin/home")
	public String adminHome(Model model) {
		return "redirect:/bidList/list";
	}

}
