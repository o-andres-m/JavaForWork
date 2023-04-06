package com.films.domains.entities.dto;

import lombok.Value;

@Value
public class ElementoDto<K, V> {
	K key;
	V value;
}
