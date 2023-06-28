package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.config.WriteableRepository;
import com.app.dto.OrderAllocationInterface;
import com.app.entity.OrderAllocation;

public interface OrderAllocationRepository extends WriteableRepository<OrderAllocation, Integer> {
	
	List<OrderAllocation> findByOrderId(Integer orderId);
	
	List<OrderAllocation> findByFactoryIdAndMonthId(Integer factoryId, Integer monthId);
	
	@Query("SELECT SUM(sam) FROM OrderAllocation oa WHERE oa.factoryId = :factoryId AND oa.monthId = :monthId AND oa.status='ACTIVE'")
    Integer getTotalSamOfFactoryAndMonth(@Param("factoryId") Integer factoryId, @Param("monthId") Integer monthId);
	
	List<OrderAllocation> findByFactoryIdAndMonthIdAndOrderId(Integer factoryId, Integer monthId, Integer orderId);
	
	
	
	@Query(value = "select oos.style_id as styleId, s.name as styleName, s.description as styleDescription, "
			+ "s.sam as defaultSam, os.qty qty, os.allocated_minutes as sam, s.colour as styleColour, o.id as orderId, "
			+ "o.order_no as orderNo,c.name as customerName, o.season,oos.combo, oos.embellishment,o.country"
			+ ",o.delivery_date as deliveryDate,o.order_date as orderDate,oos.ord_qty orderedQty, oos.prod_qty as productionQty   "
			+ "from order_allocation os inner join orders o on o.id =os.order_id inner join order_styles oos on os.style_id = oos.id "
			+ "inner join style s on oos.style_id = s.id inner join customer c on c.id = o.customer_id where "
			+ "os.factory_id = :factoryId and os.month_id =:monthId ",nativeQuery = true)
	List<OrderAllocationInterface> findAllByFactoryIdAndMonthId(@Param("factoryId") Integer factoryId, @Param("monthId") Integer monthId);
	 
} 
