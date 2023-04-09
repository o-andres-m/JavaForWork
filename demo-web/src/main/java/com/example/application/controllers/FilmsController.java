package com.example.application.controllers;

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
		model.addAttribute("action", "actores/add");
		model.addAttribute("elemento", new Actor());
		return "peliculas/form";
	}
	
	// Recibe y valida
	@PostMapping("/add")
	public ModelAndView addPOST(@ModelAttribute("elemento") @Valid Film item, 
			BindingResult result, Locale locale){
		
		ModelAndView mv = new ModelAndView();

		if(!result.hasErrors()) {
			try {

				srv.add(item);
				mv.setViewName("redirect:/films");

			} catch (DuplicateKeyException  e) {
				result.addError(
						new FieldError("elemento", "actorId", ms.getMessage("errormsg.clave.duplicada", null, locale)));
			} catch (InvalidDataException  e) {
				result.addError(
						new FieldError("elemento", "actorId", e.getMessage()));
			}catch (Exception  e) {
				result.addError(
						new FieldError("elemento", "actorId", e.getMessage()));
			}
		}
		mv.addObject("modo", "add");
		mv.addObject("action", "films/add");
		mv.addObject("elemento", item);
		mv.setViewName("films/form");
		return mv;
	}

	@GetMapping("/{id:\\d+}/edit")
	public String editGET(@PathVariable Integer id, Model model) {
		Optional<Film> item = srv.getOne(id);
		if(!item.isPresent())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		model.addAttribute("modo", "edit");
		model.addAttribute("action", "actores/" + id + "/edit");
		model.addAttribute("elemento", item.get());
		return "films/form";
	}
	@PostMapping("/{id:\\d+}/edit")
	public ModelAndView editPOST(@PathVariable Long id, @ModelAttribute("elemento") @Valid Film item, 
			BindingResult result, Locale locale) throws InvalidDataException {
		ModelAndView mv = new ModelAndView();
		if(id != item.getFilmId())
			result.addError(new FieldError("elemento", "actorId", ms.getMessage("errormsg.clave.invalida", null, locale)));
		if(result.hasErrors()) {
			mv.addObject("modo", "edit");
			mv.addObject("action", "actores/" + id + "/edit");
			mv.addObject("elemento", item);
			mv.setViewName("films/form");
		} else {
			if(!srv.getOne(item.getFilmId()).isPresent())
				throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			try {
				srv.modify(item);
			} catch (NotFoundException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			}
			mv.setViewName("redirect:/films");
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
		return "redirect:/actores";
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ EmptyResultDataAccessException.class })
    public void badRequest(Exception e) throws BadRequestException {
    	throw new BadRequestException("nuevo: " + e.getMessage(), e);
    }
}
