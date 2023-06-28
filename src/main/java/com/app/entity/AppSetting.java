package com.app.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "app_settings")
@Getter
@Setter
public class AppSetting implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Type(type = "uuid-char")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;

	@ApiModelProperty(value = "soPrefix", required = true, allowableValues = "NonEmpty String")
	@Column(name = "so_prefix")
	private String soPrefix;

	@NotNull(message="soSuffix cannot be null")
	@ApiModelProperty(value = "soSuffix", required = true, allowableValues = "NonEmpty String")
	@Column(name = "so_suffix")
	private Integer soSuffix;
	
}
