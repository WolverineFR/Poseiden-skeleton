package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurvePointServiceImpl;

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

	@Autowired
	private CurvePointServiceImpl curvePointServiceImpl;

	@RequestMapping("/curvePoint/list")
	public String home(Model model) {
		model.addAttribute("curvePoints", curvePointServiceImpl.getAllCurve());
		return "curvePoint/list";
	}

	@GetMapping("/curvePoint/add")
	public String addBidForm(CurvePoint bid) {
		return "curvePoint/add";
	}

	@PostMapping("/curvePoint/validate")
	public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "curvePoint/add";
		}
		curvePointServiceImpl.saveCurvePoint(curvePoint);
		model.addAttribute("curvePoints", curvePointServiceImpl.getAllCurve());
		return "curvePoint/list";
	}

	@GetMapping("/curvePoint/update/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		CurvePoint cp = curvePointServiceImpl.getCurveById(id);
		model.addAttribute("curvePoint", cp);
		return "curvePoint/update";
	}

	@PostMapping("/curvePoint/update/{id}")
	public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			return "curvePoint/update";
		}
		curvePoint.setId(id);
		curvePointServiceImpl.saveCurvePoint(curvePoint);
		return "redirect:/curvePoint/list";
	}

	@GetMapping("/curvePoint/delete/{id}")
	public String deleteBid(@PathVariable("id") Integer id, Model model) {
		CurvePoint cp = curvePointServiceImpl.getCurveById(id);
		curvePointServiceImpl.deleteCurvePointById(cp.getId());
		model.addAttribute("curvePoints", curvePointServiceImpl.getAllCurve());
		return "redirect:/curvePoint/list";
	}
}
