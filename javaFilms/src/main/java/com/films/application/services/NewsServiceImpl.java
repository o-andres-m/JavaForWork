package com.films.application.services;

import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.films.domains.contracts.services.ActorService;
import com.films.domains.contracts.services.CategoryService;
import com.films.domains.contracts.services.FilmService;
import com.films.domains.contracts.services.LanguageService;
import com.films.domains.entities.dto.ActorDTO;
import com.films.domains.entities.dto.FilmDetailsDTO;
import com.films.domains.entities.dto.NewsDto;

import jakarta.transaction.Transactional;

@Service
public class NewsServiceImpl implements NewsService {

	@Autowired
	private FilmService filmSrv;
	
	@Autowired
	private ActorService artorSrv;
	
	@Autowired
	private CategoryService categorySrv;
	
	@Autowired
	private LanguageService languageSrv;

	@Override
	@Transactional
	public NewsDto news(Timestamp time) {
		
		/**
		 * Default Time: 864.000 -> 10 days
		 */
		
		if(time == null)
			time = Timestamp.from(Instant.now().minusSeconds(864000));
		
		
		return new NewsDto(
				filmSrv.news(time).stream()
					.map(item -> FilmDetailsDTO.from(item)).toList(),
				artorSrv.news(time).stream()
					.map(item -> ActorDTO.from(item)).toList(),
				categorySrv.news(time), 
				languageSrv.news(time)
				);
	}

}