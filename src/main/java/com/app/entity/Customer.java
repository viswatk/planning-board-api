package com.app.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@ApiModel(value = "Customer Request", description = "The customer request payload")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "customer")
public class Customer extends RecordModifier implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Integer id;

	@NotNull(message = "Customer name cannot be blank")
	@ApiModelProperty(value = "Valid Customer name", required = true, allowableValues = "NonEmpty String")
	@Column(name = "name")
	private String name;

	@NotNull(message = "Address Line can be null but not blank")
	@ApiModelProperty(value = "Address Line", allowableValues = "NonEmpty String", allowEmptyValue = false)
	@Column(name = "address_line")
	private String addressLine;

	@JsonIgnore
	@OneToOne()
	@JoinColumn(name = "location_id")
	private Location locationObj;

	@ApiModelProperty(value = "Postal code", allowableValues = "NonEmpty String", allowEmptyValue = false)
	@Column(name = "postal_code")
	private String postalCode;
	
	@NotNull(message = "Location cannot be null")
	@ApiModelProperty(value = "Location", required = true, dataType = "Integer", allowableValues = "A valid country object")
	@Transient
	private Integer locationId;

	public Integer getLocationId() {
		if(null != locationObj) {
			this.locationId = locationObj.getId();
		}
		return locationId;
	}
}
