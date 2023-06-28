package com.app.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderAllocationDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@ApiModelProperty(value = "Quantity", required = false, allowableValues = "Integer")
	private Integer qty1;
	
	@ApiModelProperty(value = "SAM", required = false, allowableValues = "Integer")
	private Integer sam1;
	
	@ApiModelProperty(value = "Factory Id", required = false, allowableValues = "Integer")
	private Integer factoryId;
	
	@ApiModelProperty(value = "Month Id", required = false, allowableValues = "Integer")
	private Integer monthId;
	
	@ApiModelProperty(value = "Order Id", required = false, allowableValues = "Integer")
	private Integer orderId;
	
	@ApiModelProperty(value = "Style Id", required = false, allowableValues = "Integer")
	private Integer styleId;
	
	@ApiModelProperty(value = "Month SAM", required = false, allowableValues = "String")
	private String month;

}
