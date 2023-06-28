package com.app.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderResponseDTO {
	
	
	Integer orderId;
	
	String orderNo;
	
	@JsonFormat(pattern = "dd-MMM-yy")
	Date orderDate;
	
	@JsonFormat(pattern = "dd-MMM-yy")
	Date deliveryDate;
	
	Integer customerId;
	
	String customerName;
	
	String combo;
	
	Integer styleId;
	
	String styleName;
	
	String styleDescription;
	
	Integer defaultSam;
	
	Integer orderedQty;
	
	Integer productionQty;
	
	Integer styleSam;
	
	Integer orderSam;
	
	String styleColour;
	
	String season;
	
	String embellishment;
	
	String country;
	
	public OrderResponseDTO(OrderDTO orderDTO) {
		super();
		this.orderId = orderDTO.getOrderId();
		this.orderNo = orderDTO.getOrderNo();
		this.orderDate = orderDTO.getOrderDate();
		this.deliveryDate = orderDTO.getDeliveryDate();
		this.customerId = orderDTO.getCustomerId();
		this.customerName = orderDTO.getCustomerName();
		this.combo = orderDTO.getCombo();
		this.styleId = orderDTO.getStyleId();
		this.styleName = orderDTO.getStyleName();
		this.styleDescription = orderDTO.getStyleDescription();
		this.defaultSam = orderDTO.getDefaultSam();
		this.orderedQty = orderDTO.getOrderedQty();
		this.productionQty = orderDTO.getProductionQty();
		this.styleSam = orderDTO.getStyleSam();
		this.styleColour = orderDTO.getStyleColour();
		this.season = orderDTO.getSeason();
		this.country = orderDTO.getCountry();
		this.embellishment = orderDTO.getEmbellishment();
	}
	
	
	

}
