package com.app.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dto.OrderDTO;
import com.app.dto.OrderResponseDTO;
import com.app.entity.AppSetting;
import com.app.entity.Order;
import com.app.entity.OrderStyle;
import com.app.repository.AppSettingsRepository;
import com.app.repository.OrderRepository;
import com.app.repository.OrderStyleRepository;
import com.app.util.ValidationUtil;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Service
@Transactional
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class OrderService {

	private @NonNull OrderRepository orderRepository;
	
	private @NotNull OrderStyleRepository orderStyleRepository;
	
	private @NonNull AppSettingsRepository appSettingRepository;
	
	@Transactional
	public Order save(Order order) {
		
		AppSetting appSetting = appSettingRepository.findAll().get(0);
		Integer orderNo = appSetting.getSoSuffix();
		
		order.setOrderNo(appSetting.getSoPrefix()+""+orderNo);
		orderRepository.saveAndFlush(order);
		
		appSetting.setSoSuffix(orderNo+1);
		appSettingRepository.saveAndFlush(appSetting);
		
		return order;
	}
	
	
	@Transactional
	public Order update(Order order) {
		
		Optional<Order> orderOptional = orderRepository.findById(order.getId());
		
		Map<Integer, OrderStyle> orderStyleMap = orderOptional.get().getStyleList().stream().collect(Collectors.toMap(OrderStyle::getStyleId, b -> b));
		
		for (OrderStyle style : order.getStyleList()) {
			OrderStyle styleObj = orderStyleMap.remove(style.getStyleObj().getId());
		}
		if(null != orderStyleMap && !orderStyleMap.isEmpty()) {
			for (OrderStyle style : orderStyleMap.values()) {
				orderStyleRepository.deleteById(style.getId());
			}
		}
		
		orderRepository.saveAndFlush(order);
		return order;
	}
	 
	/*
	@Transactional
	public Collection<OrderResponseDTO> findAllActiveOrders() {
		List<OrderDTO> orders =  orderRepository.findAllActiveOrders();
		Map<String, OrderResponseDTO> orderGroupMap = new HashMap<String, OrderResponseDTO>();
		for (OrderDTO orderDTO : orders) {
			String key = orderDTO.getOrderId() + "_"+orderDTO.getStyleId();
			OrderResponseDTO orderResObj = orderGroupMap.get(key);
			if(null != orderResObj) {
				orderResObj.setOrderedQty(orderResObj.getOrderedQty() + orderDTO.getOrderedQty());
				orderResObj.setProductionQty(orderResObj.getProductionQty() + orderDTO.getProductionQty());
			}else {
				orderResObj = new OrderResponseDTO(orderDTO);
			}
			orderResObj.setOrderSam(orderResObj.getStyleSam() * orderResObj.getProductionQty());
			orderGroupMap.put(key,orderResObj);
		}
		return orderGroupMap.values();
	}
	*/
	
	@Transactional
	public Collection<OrderResponseDTO> findAllActiveOrders(String key) {
		List<OrderDTO> orders = null;
		
		if(!ValidationUtil.isNullOrEmpty(key)) {
			orders =  orderRepository.findAllActiveOrders(key);
		}else {
			orders =  orderRepository.findAllActiveOrders();
		}
		
		List<OrderResponseDTO> orderList = new ArrayList<OrderResponseDTO>();
		for (OrderDTO orderDTO : orders) {
			OrderResponseDTO orderResObj = new OrderResponseDTO(orderDTO);
			orderResObj.setOrderSam(orderResObj.getStyleSam() * orderResObj.getProductionQty());
			orderList.add(orderResObj);
		}
		return orderList;
	}
	
	
}
