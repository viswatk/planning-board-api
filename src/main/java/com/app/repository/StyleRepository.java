package com.app.repository;

import java.util.Optional;

import com.app.config.WriteableRepository;
import com.app.entity.Style;

public interface StyleRepository extends WriteableRepository<Style, Integer> {

	Optional<Style> findByName(String name);
	
}
