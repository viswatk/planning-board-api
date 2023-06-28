package com.app.repository;

import java.util.Optional;

import com.app.config.WriteableRepository;
import com.app.entity.Country;

public interface CountryRepository extends WriteableRepository<Country, Integer> {

	public Optional<Country> findByName(String name);
}
