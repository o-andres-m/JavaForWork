package com.films.domains.contracts.services;

import java.sql.Timestamp;
import java.util.List;

import com.films.domains.core.services.contracts.ProjectionDomainService;
import com.films.domains.entities.Language;

public interface LanguageService extends ProjectionDomainService<Language, Integer>{

	List<Language> news(Timestamp time);
}
