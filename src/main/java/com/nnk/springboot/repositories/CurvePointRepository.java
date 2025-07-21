package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.CurvePoint;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface de repository pour l'entité CurvePoint. Hérite de JpaRepository
 * pour bénéficier des méthodes CRUD de base.
 */
public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {

}
