package com.film.domains.contracts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.films.domains.core.repositories.contracts.RepositoryWithProjections;
import com.films.domains.entities.Actor;

public interface ActorRepository extends JpaRepository<Actor, Integer>, JpaSpecificationExecutor<Actor>,RepositoryWithProjections{

}
