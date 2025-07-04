package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.Trade;

public interface TradeService {
	List<Trade> getAllTrades();

	Trade getTradeById(Integer id);

	Trade saveTrade(Trade trade);

	void deleteTradeById(Integer id);
}
