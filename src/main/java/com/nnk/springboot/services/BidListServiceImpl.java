package com.nnk.springboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

/**
 * Service métier pour la gestion des BidList. Implémente les opérations CRUD en
 * utilisant le repository {@link BidListRepository}.
 */
@Service
public class BidListServiceImpl implements BidListService {

	@Autowired
	private BidListRepository bidListRepository;

	@Autowired
	public BidListServiceImpl(BidListRepository bidListRepository) {
		this.bidListRepository = bidListRepository;
	}

	/**
	 * Récupère la liste complète des bids.
	 * 
	 * @return liste de tous les BidList
	 */
	@Override
	public List<BidList> getAllBids() {
		return bidListRepository.findAll();
	}

	/**
	 * Sauvegarde ou met à jour un bid.
	 * 
	 * @param bid l'objet à enregistrer
	 * @return l'objet persisté
	 */
	@Override
	public BidList saveBid(BidList bid) {
		return bidListRepository.save(bid);
	}

	/**
	 * Récupère un bid par son ID.
	 * 
	 * @param id identifiant du bid
	 * @return bid trouvé
	 */
	@Override
	public BidList getBidById(Integer id) {
		return bidListRepository.getById(id);
	}

	/**
	 * Supprime un bid par son ID.
	 * 
	 * @param id identifiant du bid à supprimer
	 */
	@Override
	public void deleteBidById(Integer id) {
		bidListRepository.deleteById(id);

	}

}
