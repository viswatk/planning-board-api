package com.app.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

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
@Table(name = "order_styles")
public class OrderStyle extends RecordModifier implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Integer id;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;

	@JsonIgnore
	@OneToOne()
	@JoinColumn(name = "style_id")
	private Style styleObj;	
	
	@ApiModelProperty(value = "Combo", required = false, allowableValues = "String")
	@Column(name = "combo")
	private String combo;
	
	@ApiModelProperty(value = "Ordered Quantity", required = false, allowableValues = "Integer")
	@Column(name = "ord_qty")
	private Integer orderedQty;
	
	@ApiModelProperty(value = "Prod Quantity", required = false, allowableValues = "Integer")
	@Column(name = "prod_qty")
	private Integer prodQty;
	
	@ApiModelProperty(value = "SAM", required = false, allowableValues = "Integer")
	@Column(name = "sam")
	private Integer sam;
	
	@ApiModelProperty(value = "Embellishment", required = false, allowableValues = "String")
	@Column(name = "embellishment")
	private String embellishment;
	
	@ApiModelProperty(value = "Style Id", required = false, allowableValues = "Integer")
	@Transient
	private Integer styleId;
	
	
	@Transient
	private String styleName;
	
	
	public Integer getStyleId() {
		if(null != styleObj) {
			this.styleId = styleObj.getId();
		}
		return styleId;
	}
	
	
	public String getStyleName() {
		if(null != styleObj) {
			this.styleName = styleObj.getName();
		}
		return styleName;
	}
	
}
