package com.films.domains.contracts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.films.domains.core.repositories.contracts.RepositoryWithProjections;
import com.films.domains.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>, JpaSpecificationExecutor<Category>,RepositoryWithProjections{

}
