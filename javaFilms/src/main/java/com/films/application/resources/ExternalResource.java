package com.films.application.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.films.application.proxies.DemoProxy;
import com.films.application.proxies.IpDataProxy;
import com.films.application.proxies.MyIpProxy;
import com.films.domains.entities.dto.FilmShortDTO;
import com.films.domains.entities.dto.IpDataDTO;
import com.films.domains.entities.dto.IpDto;

@RestController
@RequestMapping ("/external")
public class ExternalResource {
	
	@Autowired
	MyIpProxy proxyIp;
	
	@Autowired
	IpDataProxy proxyData;
	
	@Autowired
	DemoProxy proxyDemo;
	
	//Get your own IP
	// localhost:8010/external/ip
	@GetMapping("/ip")
	public IpDto getIp() {
		return proxyIp.getIp();
	}
	
	//Get your own IP with extra data
	// localhost:8010/external/ip/data
	@GetMapping("/ip/data")
	public IpDataDTO getIpData() {
		return proxyData.getData(proxyIp.getIp().getIp());
	}
	
	//Get all films from another service with proxy crossing over eureka
	@GetMapping("/films")
	public List<FilmShortDTO> getallFilms() {
		return proxyDemo.getAllFilms();
	}
}
