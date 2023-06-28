package com.app.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesOrderDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String commitedDateOfDelivery;
	private String associateName;
	private String soNumber;
	private String soRefNumber;
	private String salesOrderNumber;
	private String salesRefNumber;
	private String orderDate;
	private Integer salesId;
	private String orderPlacedDate;
	private String cancelledDate;
	private Double totalCost;
	private String status;
	private Double grandTotal;
	private Integer id;
	private Integer createdBy;
	private Boolean isInvoiceCreated; 

}
