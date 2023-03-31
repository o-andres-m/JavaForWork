package com.films.domains.core.repositories.contracts;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface RepositoryWithProjections {
	<T> List<T> findAllBy(Class<T> type);
	<T> Iterable<T> findAllBy(Sort order, Class<T> type);
	<T> Page<T> findAllBy(Pageable order, Class<T> type);
}