package com.app.dto;

public class ProductDetailDTO {
	private Integer id;
	private Integer uomId;
	private String uomName;
	private Double PurchaseRatePerUnit;
	private Double SalesRatePerUnit;
	private String GstPercentage;
	private Double AvailableQuantity;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUomId() {
		return uomId;
	}

	public void setUomId(Integer uomId) {
		this.uomId = uomId;
	}

	public String getUomName() {
		return uomName;
	}

	public void setUomName(String uomName) {
		this.uomName = uomName;
	}

	public Double getPurchaseRatePerUnit() {
		return PurchaseRatePerUnit;
	}

	public void setPurchaseRatePerUnit(Double purchaseRatePerUnit) {
		PurchaseRatePerUnit = purchaseRatePerUnit;
	}

	public Double getSalesRatePerUnit() {
		return SalesRatePerUnit;
	}

	public void setSalesRatePerUnit(Double salesRatePerUnit) {
		SalesRatePerUnit = salesRatePerUnit;
	}

	public String getGstPercentage() {
		return GstPercentage;
	}

	public void setGstPercentage(String gstPercentage) {
		GstPercentage = gstPercentage;
	}

	public Double getAvailableQuantity() {
		return AvailableQuantity;
	}

	public void setAvailableQuantity(Double availableQuantity) {
		AvailableQuantity = availableQuantity;
	}

}
