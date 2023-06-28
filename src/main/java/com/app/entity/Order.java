package com.app.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "orders")
public class Order extends RecordModifier implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Integer id;
	
	@Column(name = "order_no")
	private String orderNo;
	
	@JsonIgnore
	@OneToOne()
	@JoinColumn(name = "customer_id")
	private Customer customerObj;
	
	@ApiModelProperty(value = "Order Date", required = false, allowableValues = "dd/MM/yyyy HH:mm")
	@JsonFormat(pattern = "dd-MMM-yy")
	@Column(name = "order_date")
	private Date orderDate;
	
	@ApiModelProperty(value = "Delivery Date", required = false, allowableValues = "dd/MM/yyyy HH:mm")
	@JsonFormat(pattern = "dd-MMM-yy")
	@Column(name = "delivery_date")
	private Date deliveryDate;
	
	@Column(name = "season")
	private String season;
	
	@Column(name = "country")
	private String country;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<OrderStyle> styleList;
	
	@ApiModelProperty(value = "Customer Id", required = false, allowableValues = "Integer")
	@Transient
	private Integer customerId;
	
	@Transient
	private String customerName;
	
	
	public Integer getCustomerId() {
		if(null != customerObj) {
			this.customerId = customerObj.getId();
		}
		return customerId;
	}
	
	public String getCustomerName() {
		if(null != customerObj) {
			this.customerName = customerObj.getName();
		}
		return customerName;
	}
	
}
