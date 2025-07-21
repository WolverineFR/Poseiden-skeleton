package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface de repository pour l'entité Trade. Hérite de JpaRepository pour
 * bénéficier des méthodes CRUD de base.
 */
public interface TradeRepository extends JpaRepository<Trade, Integer> {
}
