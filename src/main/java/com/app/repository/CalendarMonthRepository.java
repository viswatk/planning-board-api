package com.app.repository;

import java.util.Optional;

import com.app.config.WriteableRepository;
import com.app.entity.CalendarMonth;
import com.app.entity.Style;

public interface CalendarMonthRepository extends WriteableRepository<CalendarMonth, Integer> {

	Optional<CalendarMonth> findByName(String name);
	
}
