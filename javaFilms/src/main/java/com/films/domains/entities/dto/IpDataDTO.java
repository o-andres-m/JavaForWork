package com.films.domains.entities.dto;

import lombok.Data;

@Data
public class IpDataDTO {
	
	String ip;
	CompanyDTO company;
	LocationDTO location;
	Double elapsed_ms;
}
