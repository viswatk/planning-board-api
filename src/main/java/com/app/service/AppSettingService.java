package com.app.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.entity.AppSetting;
import com.app.repository.AppSettingsRepository;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Service
@Transactional
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class AppSettingService {
	
	private @NonNull AppSettingsRepository repository;
	
	public void saveOrUpdate(AppSetting obj) {
		repository.saveAndFlush(obj);
	}
	
	public Optional<AppSetting> findById(UUID id) {
		return repository.findById(id);
	}
	
	public List<AppSetting> findAll() {
		return repository.findAll();
	}

}
