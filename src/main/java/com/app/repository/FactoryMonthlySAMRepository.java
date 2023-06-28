package com.app.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.config.WriteableRepository;
import com.app.entity.FactoryMonthlySAM;

public interface FactoryMonthlySAMRepository extends WriteableRepository<FactoryMonthlySAM, Integer> {
	 
	public List<FactoryMonthlySAM> findAllByMonthObjStartDateAndMonthObjEndDate(LocalDate startDate,LocalDate endDate);
	
	
	@Query(value = "FROM FactoryMonthlySAM t where monthObj.startDate BETWEEN :startDate AND :endDate")
	public List<FactoryMonthlySAM> getAllBetweenDates(@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate);
}
