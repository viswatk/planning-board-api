package com.app.dto;

import java.util.Date;

public interface OrderAllocationInterface {
	
	Integer getOrderId();
	
	String getOrderNo();
	
	Integer getStyleId();
	
	String getStyleName();
	
	String getStyleDescription();
	
	Integer getDefaultSam();
	
	Integer getQty();
	
	Integer getSam();
	
	String getStyleColour();
	
	String getSeason();
	
	String getCustomerName();
	
	String getCombo();
	
	String getEmbellishment();
	
	String getCountry();
	
	Date getDeliveryDate();
	
	Date getOrderDate();
	
	Integer getOrderedQty();
	
	Integer getProductionQty();

}
