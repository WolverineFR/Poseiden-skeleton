package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Repository pour l'entité User. Fournit les opérations CRUD de base et la
 * possibilité d'écrire des requêtes dynamiques via les Specifications.
 */
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
	/**
	 * Recherche un utilisateur par son nom d'utilisateur. Méthode utilisée
	 * notamment pour l'authentification Spring Security.
	 *
	 * @param username le nom d'utilisateur
	 * @return l'utilisateur correspondant, ou null s'il n'existe pas
	 */
	public User findByUsername(String username);
}
