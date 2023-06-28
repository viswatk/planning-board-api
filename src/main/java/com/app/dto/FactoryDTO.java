package com.app.dto;

import java.io.Serializable;
import java.util.List;

import com.app.entity.Factory;
import com.app.entity.Location;

import lombok.Getter;
import lombok.Setter;
 

@Getter
@Setter
public class FactoryDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private String shortName;
	private String address;
	private Location location;
	private List<FactoryMonthlySAMDTO> allocations;
	
	
	public FactoryDTO(Factory factory) {
		super();
		this.id = factory.getId();
		this.name = factory.getName();
		this.shortName = factory.getShortName();
		this.address = factory.getAddress();
		this.location = factory.getLocation();
	}
	
}
