package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.RuleName;

public interface RuleNameService {
	List<RuleName> getAllRulesNames();

	RuleName getRuleNameById(Integer id);

	RuleName saveRuleName(RuleName ruleName);

	void deleteRuleNameById(Integer id);
}
