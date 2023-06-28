package com.app.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="caledar_month")
public class CalendarMonth implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Integer id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="short_name")
	private String shortName;
	
	@Column(name="calendar_index")
	private Integer calendarIndex; 
	
	@Column(name="start_date")
	private LocalDate startDate; 
	
	@Column(name="end_date")
	private LocalDate endDate; 
	
}
