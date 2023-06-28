package com.app.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FactoryMonthlyAllocationDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer qty;
	
	private Integer sam;
	
	private String styleName;
	
	private String styleDescription;
	
	private String styleColour;
	
	private Integer styleId;
	
	private Integer orderId;
	
	private String orderNo;
	
	private String customerName;
	
	private String season;
	
	private String combo;
	
	private String embellishment;
	
	private String country;
	
	@JsonFormat(pattern = "dd-MMM-yy")
	private Date deliveryDate;
	
	@JsonFormat(pattern = "dd-MMM-yy")
	private Date orderDate;
	
	private Integer orderedQty;
	
	private Integer productionQty;
	
	
	public FactoryMonthlyAllocationDTO(OrderAllocationInterface orderAllocation) {
		super();
		this.qty = orderAllocation.getQty();
		this.sam = orderAllocation.getSam();
		this.styleName = orderAllocation.getStyleName();
		this.styleDescription = orderAllocation.getStyleDescription();
		this.styleColour = orderAllocation.getStyleColour();
		this.styleId = orderAllocation.getStyleId();
		this.orderId = orderAllocation.getOrderId();
		this.orderNo = orderAllocation.getOrderNo();
		this.customerName = orderAllocation.getCustomerName();
		this.season = orderAllocation.getSeason();
		this.combo = orderAllocation.getCombo();
		this.embellishment = orderAllocation.getEmbellishment();
		this.country = orderAllocation.getCountry();
		this.deliveryDate = orderAllocation.getDeliveryDate();
		this.orderDate = orderAllocation.getOrderDate();
		this.orderedQty=orderAllocation.getOrderedQty();
		this.productionQty=orderAllocation.getProductionQty();
	}

}
