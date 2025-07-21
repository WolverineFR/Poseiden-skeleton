package com.nnk.springboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

/**
 * Service métier pour la gestion des trades. Implémente les opérations CRUD en
 * utilisant le repository {@link TradeRepository}.
 */
@Service
public class TradeServiceImpl implements TradeService {

	@Autowired
	private TradeRepository tradeRepository;

	@Autowired
	public TradeServiceImpl(TradeRepository tradeRepository) {
		this.tradeRepository = tradeRepository;
	}

	/**
	 * Récupère la liste complète des trades.
	 * 
	 * @return liste de tous les trades
	 */
	@Override
	public List<Trade> getAllTrades() {
		return tradeRepository.findAll();
	}

	/**
	 * Récupère un trade par son ID.
	 * 
	 * @param id identifiant du trade
	 * @return trade trouvé
	 */
	@Override
	public Trade getTradeById(Integer id) {
		return tradeRepository.getById(id);
	}

	/**
	 * Sauvegarde ou met à jour un trade.
	 * 
	 * @param trade l'objet à enregistrer
	 * @return l'objet persisté
	 */
	@Override
	public Trade saveTrade(Trade trade) {
		return tradeRepository.save(trade);
	}

	/**
	 * Supprime un trade par son ID.
	 * 
	 * @param id identifiant du trade à supprimer
	 */
	@Override
	public void deleteTradeById(Integer id) {
		tradeRepository.deleteById(id);

	}

}
