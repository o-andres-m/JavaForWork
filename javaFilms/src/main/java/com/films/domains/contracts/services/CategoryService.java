package com.films.domains.contracts.services;

import java.sql.Timestamp;
import java.util.List;

import com.films.domains.core.services.contracts.ProjectionDomainService;
import com.films.domains.entities.Category;


public interface CategoryService extends ProjectionDomainService<Category, Integer>{
	
	List<Category> novedades (Timestamp timestamp);
}
