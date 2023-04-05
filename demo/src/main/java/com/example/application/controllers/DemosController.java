package com.example.application.controllers;

import java.util.Iterator;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.domains.entities.dtos.ActorDto;

import lombok.extern.log4j.Log4j2;

@Controller
//@RequestMapping(path = "/")
@Log4j2
public class DemosController {
	
	@GetMapping("/params/{id}")
	@ResponseBody
	public String cotilla(
			@PathVariable String id,
			@RequestParam String nom,
			@RequestHeader ("Accept-Language") String language,
			@CookieValue (required = false, name = "JSESSIONID") String cookie
			) {
		var sb = new StringBuilder();
		sb.append("id: " + id + "\n");
		sb.append("nom: " + nom + "\n");
		sb.append("language: " + language + "\n");
		sb.append("cookie: " + cookie + "\n");
		
		log.info("WebPage Reloaded....");
		
		return sb.toString();
	}
	
	@GetMapping("/actor")
	@ResponseBody
	public ActorDto actorDto() {
		var actor = new ActorDto(1, "Name", "Last");
	
		log.info("WebPage Reloaded....");

		return actor;
	}

}
