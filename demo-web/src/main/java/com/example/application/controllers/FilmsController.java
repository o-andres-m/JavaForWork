package com.example.application.controllers;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import com.example.domains.contracts.services.ActorService;
import com.example.domains.contracts.services.FilmService;
import com.example.domains.entities.Actor;
import com.example.domains.entities.Film;
import com.example.domains.entities.dtos.ActorDTO;
import com.example.exceptions.BadRequestException;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;
import com.example.domains.entities.Language;
import com.example.domains.entities.Film.Rating;

import jakarta.validation.Valid;

@Controller
@RequestMapping(path="/peliculas")
public class FilmsController {
	
	@Autowired
	private FilmService srv;
	
	@Autowired 
	private MessageSource ms;
	
	@GetMapping(path="")
	public String list(Model model, @PageableDefault(size=50, sort = {"title"})  Pageable page) {
		model.addAttribute("listado", srv.getAll(page));
		return "peliculas/list";
	}
	
	
	@GetMapping(path="/{id:\\d+}/**")
	public String view(@PathVariable Integer id, Model model) {
		Optional<Film> item = srv.getOne(id);
		if(!item.isPresent())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		model.addAttribute("elemento", item.get());
		return "peliculas/view";
	}
	
	@GetMapping("/add")
	public String addGET(Model model) {
		model.addAttribute("modo", "add");
		model.addAttribute("action", "peliculas/add");
		model.addAttribute("elemento", new Film());
		return "peliculas/form";
	}
	
	// Recibe y valida
	@PostMapping("/add")
	public ModelAndView addPOST(@ModelAttribute("elemento") Film item, 
			BindingResult result, Locale locale){
		
		ModelAndView mv = new ModelAndView();

		if(!result.hasErrors()) {
			try {

				// Default Values:
				item.setLastUpdate(Timestamp.from(Instant.now()));
				item.setLength(1);
				item.setRating(Rating.GENERAL_AUDIENCES);
				item.setReleaseYear(Short.valueOf("2023"));
				item.setRentalDuration(Byte.valueOf("1"));
				item.setRentalRate(BigDecimal.valueOf(1));
				item.setReplacementCost(BigDecimal.valueOf(1));
				item.setLanguage(new Language(1));
				
				
				//item.setActors(List.of());
				//item.setCategories(List.of());
				//item.setLanguageVO(new Language());

				
				srv.add(item);
				mv.setViewName("redirect:/peliculas");

			} catch (DuplicateKeyException  e) {
				result.addError(
						new FieldError("elemento", "filmId", ms.getMessage("errormsg.clave.duplicada", null, locale)));
			} catch (InvalidDataException  e) {
				result.addError(
						new FieldError("elemento", "filmId", e.getMessage()));
			}catch (Exception  e) {
				result.addError(
						new FieldError("elemento", "filmId", e.getMessage()));
			}
		}
		mv.addObject("modo", "add");
		mv.addObject("action", "peliculas/add");
		mv.addObject("elemento", item);
		mv.setViewName("peliculas/form");
		return mv;
	}

	@GetMapping("/{id:\\d+}/edit")
	public String editGET(@PathVariable Integer id, Model model) {
		Optional<Film> item = srv.getOne(id);
		if(!item.isPresent())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		model.addAttribute("modo", "edit");
		model.addAttribute("action", "peliculas/" + id + "/edit");
		model.addAttribute("elemento", item.get());
		return "peliculas/form";
	}
		
	@PostMapping("/{id:\\d+}/edit")
	public ModelAndView editPOST(@PathVariable Long id, @ModelAttribute("elemento") Film item, 
			BindingResult result, Locale locale) throws InvalidDataException {
		ModelAndView mv = new ModelAndView();
		if(id != item.getFilmId())
			result.addError(new FieldError("elemento", "filmId", ms.getMessage("errormsg.clave.invalida", null, locale)));
		if(result.hasErrors()) {
			mv.addObject("modo", "edit");
			mv.addObject("action", "peliculas/" + id + "/edit");
			mv.addObject("elemento", item);
			mv.setViewName("peliculas/form");
		} else {
			if(!srv.getOne(item.getFilmId()).isPresent())
				throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			try {
				var film = srv.getOne(item.getFilmId()).orElseThrow(
						()-> new NotFoundException());
				film.setTitle(item.getTitle());
				film.setDescription(item.getDescription());
				srv.modify(film);
			} catch (NotFoundException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			}
			mv.setViewName("redirect:/peliculas");
		}
		return mv;
	}
	
	@GetMapping("/{id:\\d+}/delete")
	public String editGET(@PathVariable Integer id) {
//		try {
			srv.deleteById(id);
//		} catch (Exception e) {
//			throw new BadRequestException(e.getMessage(), e);
//		}
		return "redirect:/peliculas";
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ EmptyResultDataAccessException.class })
    public void badRequest(Exception e) throws BadRequestException {
    	throw new BadRequestException("nuevo: " + e.getMessage(), e);
    }
}
