package com.app.dto;

public interface ProductDetailsDTO {
	Integer getId();
	
	Integer getProductUomId();
	
	String getUomName();
	
	Double getPurchaseRatePerUnit();
	
	Double getSalesRatePerUnit();
	
	String getGstPercentage();
	
	Double getAvailableQuantity();
	
	

}
