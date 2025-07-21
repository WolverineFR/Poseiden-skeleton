package com.nnk.springboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;

/**
 * Service métier pour la gestion des ruleNames. Implémente les opérations
 * CRUD en utilisant le repository {@link RuleNameRepository}.
 */
@Service
public class RuleNameServiceImpl implements RuleNameService {

	@Autowired
	private RuleNameRepository ruleNameRepository;

	@Autowired
	public RuleNameServiceImpl(RuleNameRepository ruleNameRepository) {
		this.ruleNameRepository = ruleNameRepository;
	}

	/**
	 * Récupère la liste complète des ruleNames.
	 * 
	 * @return liste de tous les ruleNames
	 */
	@Override
	public List<RuleName> getAllRulesNames() {
		return ruleNameRepository.findAll();
	}

	/**
	 * Récupère un ruleName par son ID.
	 * 
	 * @param id identifiant du ruleName
	 * @return ruleName trouvé
	 */
	@Override
	public RuleName getRuleNameById(Integer id) {
		return ruleNameRepository.getById(id);
	}

	/**
	 * Sauvegarde ou met à jour un ruleName.
	 * 
	 * @param ruleName l'objet à enregistrer
	 * @return l'objet persisté
	 */
	@Override
	public RuleName saveRuleName(RuleName ruleName) {
		return ruleNameRepository.save(ruleName);
	}

	/**
	 * Supprime un ruleName par son ID.
	 * 
	 * @param id identifiant du ruleName à supprimer
	 */
	@Override
	public void deleteRuleNameById(Integer id) {
		ruleNameRepository.deleteById(id);

	}

}
