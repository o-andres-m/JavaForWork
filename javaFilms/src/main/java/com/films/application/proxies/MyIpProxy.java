package com.films.application.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import com.films.domains.entities.dto.IpDto;


@FeignClient(name="ipInfo", url="https://api.ipify.org?format=json")
public interface MyIpProxy {
	
	@GetMapping 
	IpDto getIp();
	
}
