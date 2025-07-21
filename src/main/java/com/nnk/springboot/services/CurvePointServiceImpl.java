package com.nnk.springboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.repositories.CurvePointRepository;

/**
 * Service métier pour la gestion des CurvePoints. Implémente les opérations
 * CRUD en utilisant le repository {@link CurvePointRepository}.
 */
@Service
public class CurvePointServiceImpl implements CurvePointService {

	@Autowired
	private CurvePointRepository curvePointRepository;

	@Autowired
	public CurvePointServiceImpl(CurvePointRepository curvePointRepository) {
		this.curvePointRepository = curvePointRepository;
	}

	/**
	 * Récupère la liste complète des curvepoints.
	 * 
	 * @return liste de tous les curvepoints
	 */
	@Override
	public List<CurvePoint> getAllCurves() {
		return curvePointRepository.findAll();
	}

	/**
	 * Récupère un curvePoint par son ID.
	 * 
	 * @param id identifiant du curvepoint
	 * @return curvePoint trouvé
	 */
	@Override
	public CurvePoint getCurveById(Integer id) {
		return curvePointRepository.getById(id);
	}

	/**
	 * Sauvegarde ou met à jour un curvePoint.
	 * 
	 * @param cp l'objet à enregistrer
	 * @return l'objet persisté
	 */
	@Override
	public CurvePoint saveCurvePoint(CurvePoint cp) {
		return curvePointRepository.save(cp);
	}

	/**
	 * Supprime un curvePoint par son ID.
	 * 
	 * @param id identifiant du curvePoint à supprimer
	 */
	@Override
	public void deleteCurvePointById(Integer id) {
		curvePointRepository.deleteById(id);

	}

}
