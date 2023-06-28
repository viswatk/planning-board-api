package com.app.dto;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderAllocationArrayDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "Factory Id", required = false, allowableValues = "Integer")
	private Integer factoryId;
	
	@ApiModelProperty(value = "Month SAM", required = false, allowableValues = "String")
	private String month;
	 
	private List<OrderAllocationObjDTO> orders;
	
}
