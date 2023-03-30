package com.films.domains.contracts.services;

import java.security.Timestamp;
import java.util.List;

import com.films.domains.core.services.contracts.ProjectionDomainService;
import com.films.domains.entities.Language;

public interface LanguageService extends ProjectionDomainService<Language, Integer>{

	List<Language> novedades (Timestamp timestamp);

	
}
