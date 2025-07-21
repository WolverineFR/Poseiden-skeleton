package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface de repository pour l'entité BidList. Hérite de JpaRepository pour
 * bénéficier des méthodes CRUD de base.
 */
public interface BidListRepository extends JpaRepository<BidList, Integer> {

}
