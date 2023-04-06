package com.films.domains.entities.dto;

import lombok.Value;

@Value
public class ItemDto<K, V> {
	K key;
	V value;
}
