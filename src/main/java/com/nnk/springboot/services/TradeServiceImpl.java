package com.nnk.springboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

@Service
public class TradeServiceImpl implements TradeService {

	@Autowired
	private TradeRepository tradeRepository;

	@Override
	public List<Trade> getAllTrades() {
		return tradeRepository.findAll();
	}

	@Override
	public Trade getTradeById(Integer id) {
		return tradeRepository.getById(id);
	}

	@Override
	public Trade saveTrade(Trade trade) {
		return tradeRepository.save(trade);
	}

	@Override
	public void deleteTradeById(Integer id) {
		tradeRepository.deleteById(id);

	}

}
