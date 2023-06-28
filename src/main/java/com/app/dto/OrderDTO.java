package com.app.dto;

import java.util.Date;

public interface OrderDTO {
	
	Integer getOrderId();
	
	String getOrderNo();

	Date getOrderDate();
	
	Date getDeliveryDate();
	
	Integer getCustomerId();
	
	String getCustomerName();
	
	String getCombo();
	
	Integer getStyleId();
	
	String getStyleName();
	
	String getStyleDescription();
	
	Integer getDefaultSam();
	
	Integer getOrderedQty();
	
	Integer getProductionQty();
	
	Integer getStyleSam();
	
	String getStyleColour();
	
	String getSeason();
	
	String getEmbellishment();
	
	String getCountry();

}
