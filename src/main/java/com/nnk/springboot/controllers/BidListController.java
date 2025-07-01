package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListServiceImpl;

import java.util.List;

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
   
	@Autowired
	private BidListServiceImpl bidListServiceImpl;

    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        model.addAttribute("bidLists", bidListServiceImpl.getAllBid());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
    	if (result.hasErrors() ) {
    		return "bidList/add";
    	}
    	 bidListServiceImpl.saveBid(bid);
    	 model.addAttribute("bidLists", bidListServiceImpl.getAllBid());
        return "bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
    	BidList bid = bidListServiceImpl.getBidById(id);
        model.addAttribute("bidList", bid);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
    	 if (result.hasErrors()) {
    	        return "bidList/update";
    	    }
    	    bidList.setBidListId(id);
    	    bidListServiceImpl.saveBid(bidList);
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
    	BidList bid = bidListServiceImpl.getBidById(id);
        bidListServiceImpl.deleteBid(bid.getBidListId());
        model.addAttribute("bidLists",bidListServiceImpl.getAllBid());
        return "redirect:/bidList/list";
    }
}
