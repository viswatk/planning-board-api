package com.app.dto;

import java.io.Serializable;
import java.util.List;

import com.app.entity.FactoryMonthlySAM;

import lombok.Getter;
import lombok.Setter;
 
@Getter
@Setter
public class FactoryMonthlySAMDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer machineMins;
	private Integer operatorMins;
	private Integer workingDays;
	private String month;
	private List<FactoryMonthlyAllocationDTO> orders;
	
	public FactoryMonthlySAMDTO(FactoryMonthlySAM factoryMonthlySAM) {
		super();
		this.id = factoryMonthlySAM.getId();
		this.machineMins = factoryMonthlySAM.getMachineMins();
		this.operatorMins = factoryMonthlySAM.getOperatorMins();
		this.workingDays = factoryMonthlySAM.getWorkingDays();
		this.month = factoryMonthlySAM.getMonthObj().getName();
	}
	
	
	
}
