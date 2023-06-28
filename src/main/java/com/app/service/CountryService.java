package com.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.entity.Country;
import com.app.repository.CountryRepository;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Service
@Transactional
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class CountryService {

	private @NonNull CountryRepository countryRepository;
	
	
	public Optional<Country> findById(Integer id) {
		return countryRepository.findById(id);
	}
}
