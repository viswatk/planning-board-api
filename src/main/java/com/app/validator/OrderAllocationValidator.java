package com.app.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.security.auth.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.controlleradvice.ObjectInvalidException;
import com.app.dto.OrderAllocationArrayDTO;
import com.app.dto.OrderAllocationDTO;
import com.app.dto.OrderAllocationObjDTO;
import com.app.dto.OrderDeAllocationDTO;
import com.app.entity.CalendarMonth;
import com.app.entity.Factory;
import com.app.entity.FactoryMonthlySAM;
import com.app.entity.Order;
import com.app.entity.OrderAllocation;
import com.app.entity.OrderStyle;
import com.app.enumeration.RequestType;
import com.app.enumeration.Status;
import com.app.repository.CalendarMonthRepository;
import com.app.repository.FactoryRepository;
import com.app.repository.OrderAllocationRepository;
import com.app.repository.OrderRepository;
import com.app.repository.OrderStyleRepository;
import com.app.repository.StyleRepository;
import com.app.service.MessagePropertyService;
import com.app.util.ValidationUtil;

import lombok.AllArgsConstructor;
import lombok.NonNull;


@Service
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class OrderAllocationValidator {
	
    @Autowired
    MessagePropertyService messageSource; 
    
    private @NonNull OrderAllocationRepository orderAllocationRepository;
    
    private @NonNull CalendarMonthRepository monthRepository;
    
    private @NonNull FactoryRepository factoryRepository;
    
    private @NonNull StyleRepository styleRepository;
    
    private @NonNull OrderRepository orderRepository;
    
    private @NonNull OrderStyleRepository orderStyleRepository;

	List<String> errors = null;
    List<String> errorsObj = null;
    Optional<Subject> subject = null;

    /**
     * method for Order Allocation validation
     * 
     * @param httpHeader
     * @return ValidationResult
     * @throws Exception
     */
	 public ValidationResult validateAllocation(RequestType requestType,OrderAllocationDTO request) {
		 
		errors = new ArrayList<>();
		OrderAllocation orderAllocationObj = null;

        if (requestType.equals(RequestType.POST)) {
            if (!ValidationUtil.isNull(request.getId())) {
                throw new ObjectInvalidException(messageSource.getMessage("invalid.request.payload"));
            }
        }else {
			if (ValidationUtil.isNull(request.getId())) {
	            throw new ObjectInvalidException(messageSource.getMessage("invalid.request.payload"));
	        } 
    		Optional<OrderAllocation> orderAllocationOptional = orderAllocationRepository.findById(request.getId());
            if (!orderAllocationOptional.isPresent()) {  
                throw new ObjectInvalidException(messageSource.getMessage("order.allocation.not.found"));
            }
            orderAllocationObj = orderAllocationOptional.get();
        }
        
        
        Optional<Order> order = null;
        if (ValidationUtil.isNull(request.getOrderId())) {
            errors.add(messageSource.getMessage("order.allocation.order.required"));
        }else {
        	order = orderRepository.findById(request.getOrderId());
            if (!order.isPresent()) {  
                throw new ObjectInvalidException(messageSource.getMessage("order.not.found"));
            }
        }
        
        
        Optional<CalendarMonth> calendarMonth = null;
        if (ValidationUtil.isNullOrEmpty(request.getMonth())) {
            errors.add(messageSource.getMessage("order.allocation.month.required"));
        }else {
        	calendarMonth = monthRepository.findByName(request.getMonth());
            if (!calendarMonth.isPresent()) {  
                throw new ObjectInvalidException(messageSource.getMessage("order.allocation.month.required"));
            }
        }
        
        Optional<Factory> factory = null;
        if (ValidationUtil.isNull(request.getFactoryId())) {
            errors.add(messageSource.getMessage("order.allocation.factory.required"));
        }else {
        	factory = factoryRepository.findById(request.getFactoryId());
            if (!factory.isPresent()) {  
                throw new ObjectInvalidException(messageSource.getMessage("order.allocation.factory.required"));
            }
        }
        
       
        List<OrderAllocation> allocationList = orderAllocationRepository.findByOrderId(order.get().getId());
        
        Map<Integer, OrderAllocation> allocationMap = allocationList.stream().collect(Collectors.toMap(OrderAllocation::getStyleId, b -> b));
        
        Integer allottedQty = 0;
        for (Integer styleId : allocationMap.keySet()) {
        	OrderAllocation allocation = allocationMap.get(styleId);
        	allottedQty += allocation.getQty();
		}
        
        OrderStyle styleDbObj = order.get().getStyleList().get(0);
        
        /*
        if(styleDbObj.getOrderedQty() < request.getQty()) {
			throw new ObjectInvalidException(messageSource.getMessage("order.allocation.qty.not.valid"));
		}else {
			if(allottedQty > styleDbObj.getOrderedQty()) {
				throw new ObjectInvalidException(messageSource.getMessage("order.allocation.qty.exceeds.ordered.qty"));
			}
		}
		*/
        
        if(allottedQty > styleDbObj.getOrderedQty()) {
			throw new ObjectInvalidException(messageSource.getMessage("order.allocation.qty.exceeds.ordered.qty"));
		}
        
        /*
        OrderStyle styleDbObj = null;
        
        for(OrderStyle styleObj : order.get().getStyleList()) {
        	
        	if(styleObj.getStyleObj().getId().equals(style.get().getId())) {
        		
        		if(styleObj.getOrderedQty() < request.getQty()) {
        			throw new ObjectInvalidException(messageSource.getMessage("order.allocation.qty.not.valid"));
        		}else {
        			if(allottedQty > styleObj.getOrderedQty()) {
        				throw new ObjectInvalidException(messageSource.getMessage("order.allocation.qty.exceeds.ordered.qty"));
        			}
        		}
        		
        		styleDbObj = styleObj;
        	}
        }*/
         
        ValidationResult result = new ValidationResult();
        if (errors.size() > 0) {
            String errorMessage = errors.stream().map(a -> String.valueOf(a)).collect(Collectors.joining(", "));
            throw new ObjectInvalidException(errorMessage);
        }
        
        Integer totalSam = styleDbObj.getSam() * styleDbObj.getOrderedQty();
        Integer totalAllottedSam = orderAllocationRepository.getTotalSamOfFactoryAndMonth(request.getFactoryId(), request.getMonthId());
        
        totalAllottedSam = (null != totalAllottedSam) ? totalAllottedSam:0;
        
        totalAllottedSam = totalAllottedSam + totalSam;
        
        List<FactoryMonthlySAM> monthlyList = factory.get().getMonthly();
        
        Integer monthId = calendarMonth.get().getId();
        
        FactoryMonthlySAM factoryMonthlySAM = monthlyList.stream()                        
                .filter(x -> monthId.equals(x.getMonthObj().getId()))        
                .findAny().orElse(null);     
        
        if(factoryMonthlySAM.getMachineMins() <=  totalAllottedSam) {
        	//Over allocated
        	//throw new ObjectInvalidException(messageSource.getMessage("order.allocation.max.available.reached"));
        }
        
        if(null == orderAllocationObj) {
        	orderAllocationObj = OrderAllocation.builder().factoryId(request.getFactoryId()).monthId(calendarMonth.get().getId())
        			.orderId(request.getOrderId()).styleId(styleDbObj.getStyleId()).qty(styleDbObj.getOrderedQty()).sam(totalSam).build();
        }else {
        	orderAllocationObj.setFactoryId(request.getFactoryId());
        	orderAllocationObj.setMonthId(calendarMonth.get().getId());
        	orderAllocationObj.setOrderId(request.getOrderId());
        	orderAllocationObj.setStyleId(styleDbObj.getStyleId());
        	orderAllocationObj.setQty(styleDbObj.getOrderedQty());
        	orderAllocationObj.setSam(totalSam);
        }
        orderAllocationObj.setStatus(Status.ACTIVE);
        result.setObject(orderAllocationObj);
        return result;
	 }
	 
	 
	 /**
	     * method for Order Allocation validation
	     * 
	     * @param httpHeader
	     * @return ValidationResult
	     * @throws Exception
	     */
		 public ValidationResult validateAllocationArray(RequestType requestType,OrderAllocationArrayDTO request) {
			 
			errors = new ArrayList<>();
			
			
			List<OrderAllocation> factoryAllocationList = new ArrayList<OrderAllocation>();
 
			Optional<CalendarMonth> calendarMonth = null;
	        if (ValidationUtil.isNullOrEmpty(request.getMonth())) {
	            errors.add(messageSource.getMessage("order.allocation.month.required"));
	        }else {
	        	calendarMonth = monthRepository.findByName(request.getMonth());
	            if (!calendarMonth.isPresent()) {  
	                throw new ObjectInvalidException(messageSource.getMessage("order.allocation.month.required"));
	            }
	        }
	        
	        Optional<Factory> factory = null;
	        if (ValidationUtil.isNull(request.getFactoryId())) {
	            errors.add(messageSource.getMessage("order.allocation.factory.required"));
	        }else {
	        	factory = factoryRepository.findById(request.getFactoryId());
	            if (!factory.isPresent()) {  
	                throw new ObjectInvalidException(messageSource.getMessage("order.allocation.factory.required"));
	            }
	        }
	        
	        ValidationResult result = new ValidationResult();
	        if (errors.size() > 0) {
	            String errorMessage = errors.stream().map(a -> String.valueOf(a)).collect(Collectors.joining(", "));
	            throw new ObjectInvalidException(errorMessage);
	        }
	        
	        //Integer totalAllottedSam = orderAllocationRepository.getTotalSamOfFactoryAndMonth(request.getFactoryId(), request.getMonthId());
	        
	        for(OrderAllocationObjDTO obj : request.getOrders()) {
	        	
	        	OrderAllocation orderAllocationObj = null;
	        	
	        	Optional<OrderStyle> orderStyle = null;
		        if (ValidationUtil.isNull(obj.getOrderId())) {
		            errors.add(messageSource.getMessage("order.allocation.order.required"));
		        }else {
		        	orderStyle = orderStyleRepository.findById(obj.getOrderId());
		            if (!orderStyle.isPresent()) {  
		                throw new ObjectInvalidException(messageSource.getMessage("order.not.found"));
		            }
		        }
		        
		        OrderStyle styleDbObj = orderStyle.get();
		        
		        Integer totalSam = styleDbObj.getSam() * styleDbObj.getOrderedQty();
		        
		        orderAllocationObj = OrderAllocation.builder().factoryId(request.getFactoryId()).monthId(calendarMonth.get().getId())
	        			.orderId(styleDbObj.getId()).styleId(styleDbObj.getId()).qty(styleDbObj.getOrderedQty()).sam(totalSam).build();
		        orderAllocationObj.setStatus(Status.ACTIVE);
		        factoryAllocationList.add(orderAllocationObj);
	        }
	        
	        result.setObject(factoryAllocationList);
	        return result;
		 }
		 
	 
	 /**
     * method for De Allocation validation
     * 
     * @param httpHeader
     * @return ValidationResult
     * @throws Exception
     */
	 public ValidationResult validateDeAllocation(RequestType requestType,OrderDeAllocationDTO request) {
		errors = new ArrayList<>();
       
		/*if (ValidationUtil.isNullObject(request.getQty())) {
            errors.add(messageSource.getMessage("order.allocation.qty.required"));
        }  
        
        if (ValidationUtil.isNullObject(request.getSam())) {
            errors.add(messageSource.getMessage("order.allocation.qty.sam.required"));
        }*/  
		
		 Optional<CalendarMonth> calendarMonth = null;
	        if (ValidationUtil.isNullOrEmpty(request.getMonth())) {
	            errors.add(messageSource.getMessage("order.allocation.month.required"));
	        }else {
	        	calendarMonth = monthRepository.findByName(request.getMonth());
	            if (!calendarMonth.isPresent()) {  
	                throw new ObjectInvalidException(messageSource.getMessage("order.allocation.month.required"));
	            }
	        }
        
        ValidationResult result = new ValidationResult();
        if (errors.size() > 0) {
            String errorMessage = errors.stream().map(a -> String.valueOf(a)).collect(Collectors.joining(", "));
            throw new ObjectInvalidException(errorMessage);
        }
        
        List<OrderAllocation> allocationList = orderAllocationRepository.findByFactoryIdAndMonthIdAndOrderId(request.getFactoryId(), 
        		calendarMonth.get().getId(), request.getOrderId());
         
	    if(null == allocationList || allocationList.isEmpty()) {
	    	throw new ObjectInvalidException(messageSource.getMessage("order.allocation.not.found"));
	    }
        result.setObject(allocationList);
        return result;
	 }
		
	
}