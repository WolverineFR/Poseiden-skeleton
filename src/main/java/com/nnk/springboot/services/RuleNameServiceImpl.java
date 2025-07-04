package com.nnk.springboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;

@Service
public class RuleNameServiceImpl implements RuleNameService {

	@Autowired
	private RuleNameRepository ruleNameRepository;

	@Override
	public List<RuleName> gettAllRulesNames() {
		return ruleNameRepository.findAll();
	}

	@Override
	public RuleName getRuleNameById(Integer id) {
		return ruleNameRepository.getById(id);
	}

	@Override
	public RuleName saveRuleName(RuleName ruleName) {
		return ruleNameRepository.save(ruleName);
	}

	@Override
	public void deleteRuleNameById(Integer id) {
		ruleNameRepository.deleteById(id);

	}

}
