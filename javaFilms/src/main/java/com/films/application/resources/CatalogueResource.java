package com.films.application.resources;

import java.sql.Timestamp;

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
	public NewsDto getAll(@RequestParam(required = false) Timestamp time){
		if (time == null) return srv.news(null);
		return srv.news(time);
	}	
}
