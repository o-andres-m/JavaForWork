package com.films.application.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.films.domains.entities.dto.IpDataDTO;

@FeignClient(name="ipData", url = "https://ipapi.is/json")
public interface IpDataProxy {

	@GetMapping("/?q={ip}")
	IpDataDTO getData(@PathVariable String ip);
}
