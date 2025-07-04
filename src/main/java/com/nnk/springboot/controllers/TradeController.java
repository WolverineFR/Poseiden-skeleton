package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeServiceImpl;

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
public class TradeController {

	private static final Logger logger = LogManager.getLogger(TradeController.class);

	@Autowired
	private TradeServiceImpl tradeServiceImpl;

	@RequestMapping("/trade/list")
	public String home(Model model) {
		model.addAttribute("trades", tradeServiceImpl.getAllTrades());
		return "trade/list";
	}

	@GetMapping("/trade/add")
	public String addUser(Trade bid) {
		return "trade/add";
	}

	@PostMapping("/trade/validate")
	public String validate(@Valid Trade trade, BindingResult result, Model model) {
		if (result.hasErrors()) {
			logger.warn("Ajout impossible, les données sont incorrect pour ce trade");
			return "trade/add";
		}
		tradeServiceImpl.saveTrade(trade);
		model.addAttribute("trades", tradeServiceImpl.getAllTrades());
		logger.info("Les données sont bien ajouté avec succès pour le trade {}", trade.getAccount());
		return "trade/list";
	}

	@GetMapping("/trade/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		Trade trade = tradeServiceImpl.getTradeById(id);
		model.addAttribute("trade", trade);
		return "trade/update";
	}

	@PostMapping("/trade/update/{id}")
	public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade, BindingResult result, Model model) {
		if (result.hasErrors()) {
			logger.warn("Modification impossible, les données sont incorrect pour ce trade");
			return "trade/update";
		}
		trade.setTradeId(id);
		tradeServiceImpl.saveTrade(trade);
		logger.info("Les données du trade ont bien été modifié avec succès pour le trade {}", trade.getAccount());
		return "redirect:/trade/list";
	}

	@GetMapping("/trade/delete/{id}")
	public String deleteTrade(@PathVariable("id") Integer id, Model model) {
		Trade trade = tradeServiceImpl.getTradeById(id);
		tradeServiceImpl.deleteTradeById(trade.getTradeId());
		logger.info("Le trade {} à bien été supprimé.", trade.getAccount());
		return "redirect:/trade/list";
	}
}
