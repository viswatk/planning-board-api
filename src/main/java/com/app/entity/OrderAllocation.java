package com.app.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "order_allocation")
public class OrderAllocation extends RecordModifier implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Integer id;
	
	@Column(name = "factory_id")
	private Integer factoryId;
	
	@Column(name = "month_id")
	private Integer monthId;
	
	@Column(name = "order_id")
	private Integer orderId;
	
	@Column(name = "style_id")
	private Integer styleId;
	
	@Column(name = "qty")
	private Integer qty;
	
	@Column(name = "allocated_minutes")
	private Integer sam;
	
 
	
}
