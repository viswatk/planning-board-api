package com.app.repository;

import java.util.UUID;

import com.app.config.WriteableRepository;
import com.app.entity.AppSetting;

public interface AppSettingsRepository extends WriteableRepository<AppSetting, UUID> {
  
    
}
