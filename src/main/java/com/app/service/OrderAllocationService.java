package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.entity.OrderAllocation;
import com.app.repository.OrderAllocationRepository;
import com.app.repository.OrderRepository;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Service
@Transactional
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class OrderAllocationService {

	private @NonNull OrderRepository orderRepository;
	
	private @NotNull OrderAllocationRepository orderAllocationRepository;
	
	@Transactional
	public void save(OrderAllocation orderAllocation) {
		orderAllocationRepository.saveAndFlush(orderAllocation	);
	}
	
	@Transactional
	public void saveAll(List<OrderAllocation> orderAllocationList) {
		for (OrderAllocation orderAllocation : orderAllocationList) {
			orderAllocationRepository.saveAndFlush(orderAllocation);
		}
	}
	
}
