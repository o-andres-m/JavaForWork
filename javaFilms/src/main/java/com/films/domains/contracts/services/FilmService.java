package com.films.domains.contracts.services;

import java.security.Timestamp;
import java.util.List;

import com.films.domains.core.services.contracts.ProjectionDomainService;
import com.films.domains.entities.Film;

public interface FilmService extends ProjectionDomainService<Film, Integer>{

	List<Film> novedades (Timestamp timestamp);

	
}
