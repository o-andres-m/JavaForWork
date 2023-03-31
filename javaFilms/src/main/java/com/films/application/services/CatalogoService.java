package com.films.application.services;

import java.sql.Timestamp;

import com.films.domains.entities.dto.NovedadesDTO;

public interface CatalogoService {

	NovedadesDTO novedades(Timestamp fecha);

}