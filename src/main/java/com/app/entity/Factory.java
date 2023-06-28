package com.app.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
@Table(name = "factory")
public class Factory extends RecordModifier implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Integer id;
	
	@ApiModelProperty(value = "Name", required = false, allowableValues = "String")
	@Column(name = "name")
	private String name;
	
	@ApiModelProperty(value = "Short Name", required = false, allowableValues = "String")
	@Column(name = "short_name")
	private String shortName;
	
	@ApiModelProperty(value = "Address", required = false, allowableValues = "String")
	@Column(name = "address")
	private String address;
	
	@Valid
	@NotNull(message = "Location cannot be null")
	@ApiModelProperty(value = "Location", required = true, dataType = "object", allowableValues = "A valid country object")
	@OneToOne()
	@JoinColumn(name = "location_id")
	private Location location;
	
	@OneToMany(mappedBy = "factory", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<FactoryMonthlySAM> monthly;
	
}
