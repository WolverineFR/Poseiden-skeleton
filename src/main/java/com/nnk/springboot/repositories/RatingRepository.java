package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface de repository pour l'entité Rating. Hérite de JpaRepository pour
 * bénéficier des méthodes CRUD de base.
 */
public interface RatingRepository extends JpaRepository<Rating, Integer> {

}
