package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.RuleName;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface de repository pour l'entité RuleName. Hérite de JpaRepository pour
 * bénéficier des méthodes CRUD de base.
 */
public interface RuleNameRepository extends JpaRepository<RuleName, Integer> {
}
