package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeServiceImpl;

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
			return "trade/add";
		}
		tradeServiceImpl.saveTrade(trade);
		model.addAttribute("trades", tradeServiceImpl.getAllTrades());
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
			return "trade/update";
		}
		trade.setTradeId(id);
		tradeServiceImpl.saveTrade(trade);
		return "redirect:/trade/list";
	}

	@GetMapping("/trade/delete/{id}")
	public String deleteTrade(@PathVariable("id") Integer id, Model model) {
		Trade trade = tradeServiceImpl.getTradeById(id);
		tradeServiceImpl.deleteTradeById(trade.getTradeId());
		return "redirect:/trade/list";
	}
}
