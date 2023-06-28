package com.app.repository;

import java.util.Optional;

import com.app.config.WriteableRepository;
import com.app.entity.Role;

public interface RoleRepository extends WriteableRepository<Role, Integer> {

	public Optional<Role> findByRoleName(String roleName);
}
