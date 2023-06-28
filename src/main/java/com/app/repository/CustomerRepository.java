package com.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.config.WriteableRepository;
import com.app.entity.Customer;
import com.app.entity.Location;

public interface CustomerRepository extends WriteableRepository<Customer, Integer> {
  
    Optional<Customer> findByName(String name);
    
    Optional<Customer> findByLocationObjAndName(Location location,String name);
    
    @Query("SELECT AI FROM Customer AI WHERE AI.name = :name and AI.id <> :excludeId")
    Optional<Customer> findByNameExcludeId(@Param("name") String name, @Param("excludeId") Integer excludeId);
}
