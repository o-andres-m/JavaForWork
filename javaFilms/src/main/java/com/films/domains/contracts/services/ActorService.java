package com.films.domains.contracts.services;

import java.sql.Timestamp;
import java.util.List;

import com.films.domains.core.services.contracts.ProjectionDomainService;
import com.films.domains.entities.Actor;

public interface ActorService extends ProjectionDomainService<Actor, Integer>{

	List<Actor> novedades (Timestamp timestamp);

	
}
