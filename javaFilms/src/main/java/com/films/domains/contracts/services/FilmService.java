package com.films.domains.contracts.services;

import java.sql.Timestamp;
import java.util.List;

import com.films.domains.core.services.contracts.ProjectionDomainService;
import com.films.domains.entities.Film;

public interface FilmService extends ProjectionDomainService<Film, Integer>{

	List<Film> news(Timestamp time);
}
