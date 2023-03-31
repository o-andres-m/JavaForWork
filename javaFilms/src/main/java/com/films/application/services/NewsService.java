package com.films.application.services;

import java.sql.Timestamp;

import com.films.domains.entities.dto.NewsDto;

public interface NewsService {

	NewsDto news(Timestamp fecha);

}