package com.example.application.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
//Ruta por default
public class AppController {
	@GetMapping(path="/")
	public String inicio() {
		return "inicio";
	}
}
