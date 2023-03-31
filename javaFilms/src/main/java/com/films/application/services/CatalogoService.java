package com.films.application.services;

import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;

import com.films.domains.contracts.services.ActorService;
import com.films.domains.contracts.services.CategoryService;
import com.films.domains.contracts.services.FilmService;
import com.films.domains.contracts.services.LanguageService;
import com.films.domains.entities.dto.ActorDTO;
import com.films.domains.entities.dto.FilmEditDTO;
import com.films.domains.entities.dto.NovedadesDTO;

public class CatalogoService {

		@Autowired
		private FilmService filmSrv;
		@Autowired
		private ActorService artorSrv;
		@Autowired
		private CategoryService categorySrv;
		@Autowired
		private LanguageService languageSrv;

		public NovedadesDTO novedades(Timestamp fecha) {
			// Timestamp fecha = Timestamp.valueOf("2019-01-01 00:00:00");
			if(fecha == null)
				fecha = Timestamp.from(Instant.now().minusSeconds(36000));
			return new NovedadesDTO(
					filmSrv.novedades(fecha).stream().map(item -> FilmEditDTO.from(item)).toList(), 
					artorSrv.novedades(fecha).stream().map(item -> ActorDTO.from(item)).toList(), 
					categorySrv.novedades(fecha), 
					languageSrv.novedades(fecha)
					);
		}

	}