package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
public class UserController {

	private static final Logger logger = LogManager.getLogger(UserController.class);

	@Autowired
	private UserRepository userRepository;

	@RequestMapping("/user/list")
	public String home(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "user/list";
	}

	@GetMapping("/user/add")
	public String addUser(User user) {
		return "user/add";
	}

	@PostMapping("/user/validate")
	public String validate(@Valid User user, BindingResult result, Model model) {
		try {
			if (user.getUsername() == null || user.getUsername().trim().isEmpty() || user.getPassword() == null
					|| user.getPassword().trim().isEmpty() || user.getFullname() == null
					|| user.getFullname().trim().isEmpty() || user.getRole() == null
					|| user.getRole().trim().isEmpty()) {
				throw new IllegalArgumentException("Tous les champs sont obligatoires.");
			}

			if (!user.getPassword()
					.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,}$")) {
				throw new IllegalArgumentException(
						"Le mot de passe doit contenir au moins une majuscule, un chiffre, un symbole et faire 8 caractères minimum.");
			}

			if (!result.hasErrors()) {
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				user.setPassword(encoder.encode(user.getPassword()));
				userRepository.save(user);
				model.addAttribute("users", userRepository.findAll());
				logger.info("Les données sont bien ajouté avec succès pour cet user {}", user.getUsername());
				return "redirect:/user/list";
			}
			logger.warn("Ajout impossible, les données sont incorrect pour cet user");
			return "user/add";

		} catch (IllegalArgumentException e) {
			logger.warn("Erreur lors de l'ajout de l'utilisateur : {}", e.getMessage());
			model.addAttribute("errorMessage", e.getMessage());
			return "user/add";
		}
	}

	@GetMapping("/user/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		user.setPassword("");
		model.addAttribute("user", user);
		return "user/update";
	}

	@PostMapping("/user/update/{id}")
	public String updateUser(@PathVariable("id") Integer id, @Valid User user, BindingResult result, Model model) {
		try {
			if (user.getUsername() == null || user.getUsername().trim().isEmpty() || user.getPassword() == null
					|| user.getPassword().trim().isEmpty() || user.getFullname() == null
					|| user.getFullname().trim().isEmpty() || user.getRole() == null
					|| user.getRole().trim().isEmpty()) {
				throw new IllegalArgumentException("Tous les champs sont obligatoires.");
			}

			if (!user.getPassword()
					.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,}$")) {
				throw new IllegalArgumentException(
						"Le mot de passe doit contenir au moins une majuscule, un chiffre, un symbole et faire 8 caractères minimum.");
			}

			if (result.hasErrors()) {
				logger.warn("Modification impossible, les données sont incorrect pour cet user");
				return "user/update";
			}

			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			user.setPassword(encoder.encode(user.getPassword()));
			user.setId(id);
			userRepository.save(user);
			model.addAttribute("users", userRepository.findAll());
			logger.info("Les données utilisateur ont bien été modifié avec succès pour l'user {}", user.getUsername());
			return "redirect:/user/list";

		} catch (IllegalArgumentException e) {
			logger.warn("Erreur lors de l'ajout de l'utilisateur : {}", e.getMessage());
			model.addAttribute("errorMessage", e.getMessage());
			return "user/update";
		}
	}

	@GetMapping("/user/delete/{id}")
	public String deleteUser(@PathVariable("id") Integer id, Model model) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		userRepository.delete(user);
		model.addAttribute("users", userRepository.findAll());
		logger.info("L'user {} à bien été supprimé.", user.getUsername());
		return "redirect:/user/list";
	}
}
