package com.films.domains.contracts.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.films.domains.core.repositories.contracts.RepositoryWithProjections;
import com.films.domains.entities.Film;

public interface FilmRepository extends JpaRepository<Film, Integer>, JpaSpecificationExecutor<Film>,RepositoryWithProjections{

	List<Film> findByLastUpdateGreaterThanEqualOrderByLastUpdate(Timestamp time);
}
