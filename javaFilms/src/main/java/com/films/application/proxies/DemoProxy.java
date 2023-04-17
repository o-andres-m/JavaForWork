package com.films.application.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.films.domains.entities.dto.FilmShortDTO;

import java.util.List;


@FeignClient(name = "DEMO-SERVICE")
public interface DemoProxy {
	
	@GetMapping("/api/film/v1")
	List<FilmShortDTO> getAllFilms();

}
