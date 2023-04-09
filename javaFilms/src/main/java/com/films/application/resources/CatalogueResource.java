package com.films.application.resources;

import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.films.application.services.CatalogueService;
import com.films.domains.entities.dto.NewsDto;

@RestController
@RequestMapping(path = {"/api/catalogue/v1" , "/api/catalogue"})
public class CatalogueResource {

	@Autowired
	CatalogueService srv;
	

	@GetMapping
	public NewsDto getAll(@RequestParam(required = false) Integer days){
		if (days == null || days <= 0) return srv.news(null);
		return srv.news(Timestamp.from(Instant.now().minusSeconds(days*86400)));
	}
	
}
