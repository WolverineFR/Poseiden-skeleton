package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameServiceImpl;

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

	@Autowired
	private RuleNameServiceImpl ruleNameServiceImpl;

	@RequestMapping("/ruleName/list")
	public String home(Model model) {
		model.addAttribute("ruleNames", ruleNameServiceImpl.getAllRulesNames());
		return "ruleName/list";
	}

	@GetMapping("/ruleName/add")
	public String addRuleForm(RuleName bid) {
		return "ruleName/add";
	}

	@PostMapping("/ruleName/validate")
	public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "ruleName/add";
		}
		ruleNameServiceImpl.saveRuleName(ruleName);
		model.addAttribute("ruleNames", ruleNameServiceImpl.getAllRulesNames());
		return "ruleName/list";
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
		if (result.hasErrors()) {
			return "ruleName/update";
		}
		ruleName.setId(id);
		ruleNameServiceImpl.saveRuleName(ruleName);
		return "redirect:/ruleName/list";
	}

	@GetMapping("/ruleName/delete/{id}")
	public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
		RuleName ruleName = ruleNameServiceImpl.getRuleNameById(id);
		ruleNameServiceImpl.deleteRuleNameById(ruleName.getId());
		model.addAttribute("ruleNames", ruleNameServiceImpl.getAllRulesNames());
		return "redirect:/ruleName/list";
	}
}
