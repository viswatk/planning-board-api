package com.app.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Table(name="style")
public class Style extends RecordModifier implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Integer id;

	@NotNull(message = "Name cannot be blank")
	@ApiModelProperty(value = "Name", required = true, allowableValues = "String")
	@Column(name = "name")
	private String name;
	
	@NotNull(message = "Description cannot be blank")
	@ApiModelProperty(value = "Description", required = true, allowableValues = "String")
	@Column(name = "description")
	private String description;

	@ApiModelProperty(value = "SAM", required = false, allowableValues = "Integer")
	@Column(name = "sam")
	private Integer sam;
	
	@NotNull(message = "colour cannot be blank")
	@ApiModelProperty(value = "colour", required = true, allowableValues = "String")
	@Column(name = "colour")
	private String colour;
}
