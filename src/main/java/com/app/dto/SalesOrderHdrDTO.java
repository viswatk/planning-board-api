package com.app.dto;

public interface SalesOrderHdrDTO {
	Integer getSalesId();

	String getSoNumber();

	String getSoRefNumber();

	String getAssociateName();

	//Integer getSoStatusId();

	String getOrderPlacedDate();

	String getStatus();

	String getCommitedDateOfDelivery();

	//String getActualDeliveryDate();

	Double getTotalCost();
	
	Double getGrandTotal();
	
	String getCancelledDate();

	//String getVendorName();

}
