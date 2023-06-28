package com.app.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dto.FactoryDTO;
import com.app.dto.FactoryMonthlyAllocationDTO;
import com.app.dto.FactoryMonthlySAMDTO;
import com.app.dto.OrderAllocationInterface;
import com.app.entity.Factory;
import com.app.entity.FactoryMonthlySAM;
import com.app.repository.FactoryMonthlySAMRepository;
import com.app.repository.FactoryRepository;
import com.app.repository.OrderAllocationRepository;
import com.app.repository.OrderRepository;
import com.app.repository.OrderStyleRepository;
import com.app.util.DateUtil;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Service
@Transactional
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class FactoryService {

	private @NonNull OrderRepository orderRepository;
	
	private @NotNull OrderStyleRepository orderStyleRepository;
	
	private @NonNull FactoryRepository factoryRepository;
	
	private @NonNull FactoryMonthlySAMRepository factoryMonthlySAMRepository;
	
	private @NonNull OrderAllocationRepository orderAllocationRepository;
	
	
	public static void main(String[] args) {
		LocalDate today = LocalDate.now();
		
		LocalDate twelMonthDate = today.plusMonths(12);
		
		System.out.println(twelMonthDate);
		
		LocalDate twelMonthEndDate = twelMonthDate.withDayOfMonth(twelMonthDate.lengthOfMonth());
		
		System.out.println(twelMonthEndDate);
		
		LocalDate startDate = today.withDayOfMonth(1);
		LocalDate endDate = today.withDayOfMonth(today.lengthOfMonth());
		
		System.out.println("First day: " + startDate);
		System.out.println("Last day: " + endDate);
		
		LocalDate testDate = LocalDate.now();;
		
		Period period = Period.between(startDate, endDate);
		
		//testDate.isAfter(startDate) && testDate.isBefore(endDate)
		
	}
	
	
	
	public List<FactoryDTO> findAllFactory() {
		
		LocalDate twelMonthDate = LocalDate.now().plusMonths(12);
		
		List<FactoryMonthlySAM> factoryMothlySamList = factoryMonthlySAMRepository.getAllBetweenDates(DateUtil.getCurrentMonthStartDate(),
				DateUtil.getLastDateOfMonthFromLocalDate(twelMonthDate));
		
		Map<Integer, List<FactoryMonthlySAMDTO>> factoryMonthlySamMap = new HashMap<Integer, List<FactoryMonthlySAMDTO>>();
		
		Map<Integer, Factory> factoryMap = new HashMap<Integer, Factory>();
		
		for (FactoryMonthlySAM factoryMonthlySAM : factoryMothlySamList) {
			
			List<FactoryMonthlySAMDTO> list = factoryMonthlySamMap.get(factoryMonthlySAM.getFactory().getId());
			
			FactoryMonthlySAMDTO factoryMonthlySAMDTO = new FactoryMonthlySAMDTO(factoryMonthlySAM);
			
			List<OrderAllocationInterface> ordersTempList = orderAllocationRepository.findAllByFactoryIdAndMonthId(factoryMonthlySAM.getFactory().getId(), factoryMonthlySAM.getMonthObj().getId());
			
			List<FactoryMonthlyAllocationDTO> orders =  new ArrayList<FactoryMonthlyAllocationDTO>();
			
			for (OrderAllocationInterface obj : ordersTempList) {
				orders.add(new FactoryMonthlyAllocationDTO(obj));
			}
			if(null == list) {
				list = new ArrayList<FactoryMonthlySAMDTO>();
				factoryMap.put(factoryMonthlySAM.getFactory().getId(), factoryMonthlySAM.getFactory());
			}
			factoryMonthlySAMDTO.setOrders(orders);
			list.add(factoryMonthlySAMDTO);
			factoryMonthlySamMap.put(factoryMonthlySAM.getFactory().getId(),list);
		}
		
		List<FactoryDTO> factoryList = new ArrayList<FactoryDTO>();
		
		for (Integer factoryId : factoryMonthlySamMap.keySet()) {
			
			List<FactoryMonthlySAMDTO> list = factoryMonthlySamMap.get(factoryId);
			Factory factory = factoryMap.get(factoryId);
			
			FactoryDTO factoryDTO= new FactoryDTO(factory);
			
			factoryDTO.setAllocations(list);
			
			factoryList.add(factoryDTO);
		}
		return factoryList;
	}
}
