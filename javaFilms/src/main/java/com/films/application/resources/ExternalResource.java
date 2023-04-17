package com.films.application.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.films.application.proxies.IpDataProxy;
import com.films.application.proxies.MyIpProxy;
import com.films.domains.entities.dto.IpDataDTO;
import com.films.domains.entities.dto.IpDto;

@RestController
@RequestMapping ("/ip")
public class ExternalResource {
	
	@Autowired
	MyIpProxy proxyIp;
	
	@Autowired
	IpDataProxy proxyData;
	
	
	// localhost:8010/ip
	
	@GetMapping
	public IpDto getIp() {
		return proxyIp.getIp();
	}
	
	// localhost:8010/ip/data
	
	@GetMapping("/data")
	public IpDataDTO getIpData() {
		String ip = proxyIp.getIp().getIp();
		System.out.println(ip);
		return proxyData.getData(ip);
	}
}
