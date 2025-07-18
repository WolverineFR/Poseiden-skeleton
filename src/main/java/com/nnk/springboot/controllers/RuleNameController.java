package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameServiceImpl;

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
public class RuleNameController {

	private static final Logger logger = LogManager.getLogger(RuleNameController.class);

	@Autowired
	private RuleNameServiceImpl ruleNameServiceImpl;

	@RequestMapping("/ruleName/list")
	public String home(Model model) {
		model.addAttribute("ruleNames", ruleNameServiceImpl.getAllRulesNames());
		return "ruleName/list";
	}

	@GetMapping("/ruleName/add")
	public String addRuleForm(RuleName ruleName) {
		return "ruleName/add";
	}

	@PostMapping("/ruleName/validate")
	public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
		try {
			if (ruleName.getName() == null || ruleName.getName().trim().isEmpty() || ruleName.getDescription() == null
					|| ruleName.getDescription().trim().isEmpty() || ruleName.getJson() == null
					|| ruleName.getJson().trim().isEmpty() || ruleName.getTemplate() == null
					|| ruleName.getTemplate().trim().isEmpty() || ruleName.getSqlStr() == null
					|| ruleName.getSqlStr().trim().isEmpty() || ruleName.getSqlPart() == null
					|| ruleName.getSqlPart().trim().isEmpty()) {
				throw new IllegalArgumentException("Les champs ne doivent pas être vides");
			}

			if (result.hasErrors()) {
				logger.warn("Ajout impossible, les données sont incorrect pour ce ruleName");
				return "ruleName/add";
			}
			ruleNameServiceImpl.saveRuleName(ruleName);
			model.addAttribute("ruleNames", ruleNameServiceImpl.getAllRulesNames());
			logger.info("Les données sont bien ajouté avec succès pour le ruleName {}", ruleName.getName());
			return "ruleName/list";

		} catch (IllegalArgumentException e) {
			logger.warn("Ajout échoué : {}", e.getMessage());
			model.addAttribute("errorMessage", e.getMessage());
			return "ruleName/add";
		}
	}

	@GetMapping("/ruleName/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		RuleName ruleName = ruleNameServiceImpl.getRuleNameById(id);
		model.addAttribute("ruleName", ruleName);
		return "ruleName/update";
	}

	@PostMapping("/ruleName/update/{id}")
	public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName, BindingResult result,
			Model model) {
		try {
			if (ruleName.getName() == null || ruleName.getName().trim().isEmpty() || ruleName.getDescription() == null
					|| ruleName.getDescription().trim().isEmpty() || ruleName.getJson() == null
					|| ruleName.getJson().trim().isEmpty() || ruleName.getTemplate() == null
					|| ruleName.getTemplate().trim().isEmpty() || ruleName.getSqlStr() == null
					|| ruleName.getSqlStr().trim().isEmpty() || ruleName.getSqlPart() == null
					|| ruleName.getSqlPart().trim().isEmpty()) {
				throw new IllegalArgumentException("Les champs ne doivent pas être vides");
			}

			if (result.hasErrors()) {
				logger.warn("Modification impossible, les données sont incorrect pour ce ruleName");
				return "ruleName/update";
			}
			ruleName.setId(id);
			ruleNameServiceImpl.saveRuleName(ruleName);
			logger.info("Les données du ruleName ont bien été modifié avec succès pour le ruleName {}",
					ruleName.getName());
			return "redirect:/ruleName/list";

		} catch (IllegalArgumentException e) {
			logger.warn("Ajout échoué : {}", e.getMessage());
			model.addAttribute("errorMessage", e.getMessage());
			return "ruleName/update";
		}
	}

	@GetMapping("/ruleName/delete/{id}")
	public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
		RuleName ruleName = ruleNameServiceImpl.getRuleNameById(id);
		ruleNameServiceImpl.deleteRuleNameById(ruleName.getId());
		model.addAttribute("ruleNames", ruleNameServiceImpl.getAllRulesNames());
		logger.info("Le ruleName {} à bien été supprimé.", ruleName.getName());
		return "redirect:/ruleName/list";
	}
}
