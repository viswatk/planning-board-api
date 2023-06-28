package com.app.repository;

import java.util.List;
import java.util.Optional;

import com.app.config.WriteableRepository;
import com.app.entity.Location;

public interface LocationRepository extends WriteableRepository<Location, Integer> {

	public List<Location> findAllByCountryObjId(Integer countryId);
	
	public Optional<Location> findByNameAndCountryObjId(String name, Integer countryId);
	
}
