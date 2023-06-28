package com.app.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "factory_monthly_sam_bak")
public class FactoryMonthlySAM extends RecordModifier implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Integer id;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "factory_id")
	private Factory factory;

	@JsonIgnore
	@OneToOne()
	@JoinColumn(name = "month_id")
	private CalendarMonth monthObj;
	
	@ApiModelProperty(value = "Machine Mins", required = false, allowableValues = "Integer")
	@Column(name = "machine_minutes")
	private Integer machineMins;
	
	@ApiModelProperty(value = "Operator Mins", required = false, allowableValues = "Integer")
	@Column(name = "operator_minutes")
	private Integer operatorMins;
	
	@ApiModelProperty(value = "Working Days", required = false, allowableValues = "Integer")
	@Column(name = "working_days")
	private Integer workingDays;
	
	@Transient
	private String month;
	
}
