package com.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductTransactionDTO {
	private Integer id;
	private String transRef;
	private String transSrc;
	private String transDate;
	private Double transQty;
	private String productName;

}
