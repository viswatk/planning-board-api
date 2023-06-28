package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.config.WriteableRepository;
import com.app.dto.OrderDTO;
import com.app.entity.Order;

public interface OrderRepository extends WriteableRepository<Order, Integer> {
	
	//UNIQNO	BUYERNAME	SEASON	PRODUNIT	IONO	PONUM	STYLENUMBER	EMBELLISHMENT	SDESC	COMBO	ORDQTY	PRODQTY	SAMVALUE	COUNTRY	SHIPDT

	
	@Query(value = "select os.id as orderId, o.order_no as orderNo, o.order_date as orderDate, o.delivery_date as deliveryDate, "
			+ "c.id as customerId, c.name as customerName,os.combo, os.style_id as styleId, s.name as styleName, s.description as styleDescription, "
			+ "s.sam as defaultSam, os.ord_qty orderedQty, os.prod_qty as productionQty, os.sam as styleSam, s.colour as styleColour,"
			+ "o.season,os.embellishment, o.country from order_styles os inner join orders o on o.id = os.order_id inner join customer c on "
			+ "c.id = o.customer_id inner join style s on os.style_id = s.id where os.id not in "
			+ "(select distinct order_id from order_allocation where status='ACTIVE') order by o.delivery_date asc",nativeQuery = true)
	List<OrderDTO> findAllActiveOrders();
	
	
	@Query(value = "select os.id as orderId, o.order_no as orderNo, o.order_date as orderDate, o.delivery_date as deliveryDate, "
			+ "c.id as customerId, c.name as customerName,os.combo, os.style_id as styleId, s.name as styleName, s.description as styleDescription, "
			+ "s.sam as defaultSam, os.ord_qty orderedQty, os.prod_qty as productionQty, os.sam as styleSam, s.colour as styleColour,"
			+ "o.season,os.embellishment, o.country from order_styles os inner join orders o on o.id = os.order_id inner join customer c on "
			+ "c.id = o.customer_id inner join style s on os.style_id = s.id where os.id not in (select distinct order_id from "
			+ "order_allocation where status='ACTIVE') and (o.order_no like '%:key%' or s.name like '%:key%' or s.description like '%:key%' "
			+ "or os.combo like '%:key%') order by o.delivery_date asc",nativeQuery = true)
	List<OrderDTO> findAllActiveOrders(@Param("key")String key);
	
}