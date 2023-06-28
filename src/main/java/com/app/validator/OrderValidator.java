package com.app.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.security.auth.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.controlleradvice.ObjectInvalidException;
import com.app.entity.Customer;
import com.app.entity.Order;
import com.app.entity.OrderStyle;
import com.app.entity.Style;
import com.app.enumeration.RequestType;
import com.app.enumeration.Status;
import com.app.repository.CustomerRepository;
import com.app.repository.OrderRepository;
import com.app.repository.StyleRepository;
import com.app.service.MessagePropertyService;
import com.app.util.ValidationUtil;

import lombok.AllArgsConstructor;
import lombok.NonNull;


@Service
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class OrderValidator {
	
    @Autowired
    MessagePropertyService messageSource; 
    
    private @NonNull OrderRepository orderRepository;
    
    private @NonNull CustomerRepository customerRepository;
    
    private @NonNull StyleRepository styleRepository;

	List<String> errors = null;
    List<String> errorsObj = null;
    Optional<Subject> subject = null;

    /**
     * method for Order validation
     * 
     * @param httpHeader
     * @return ValidationResult
     * @throws Exception
     */
	 public ValidationResult validate(RequestType requestType,Order request) {
		 
		errors = new ArrayList<>();
		Order orderObj = null;

        if (requestType.equals(RequestType.POST)) {
            if (!ValidationUtil.isNull(request.getId())) {
                throw new ObjectInvalidException(messageSource.getMessage("invalid.request.payload"));
            }
        }else {
			if (ValidationUtil.isNull(request.getId())) {
	            throw new ObjectInvalidException(messageSource.getMessage("invalid.request.payload"));
	        } 
    		Optional<Order> orderOptional = orderRepository.findById(request.getId());
            if (!orderOptional.isPresent()) {  
                throw new ObjectInvalidException(messageSource.getMessage("order.not.found"));
            }
            orderObj = orderOptional.get();
        }
        
        if (ValidationUtil.isNullObject(request.getOrderDate())) {
            errors.add(messageSource.getMessage("order.date.required"));
        }  
        
        if (ValidationUtil.isNullObject(request.getDeliveryDate())) {
            errors.add(messageSource.getMessage("order.delivery.date.required"));
        }  
        
        Optional<Customer> customerOptional = null;
        if (ValidationUtil.isNull(request.getCustomerId())) {
            errors.add(messageSource.getMessage("order.customer.required"));
        }else {
        	customerOptional = customerRepository.findById(request.getCustomerId());
            if (!customerOptional.isPresent()) {  
                throw new ObjectInvalidException(messageSource.getMessage("order.customer.not.found"));
            }
        }
        
       // List<OrderStyle> styleList
        for (OrderStyle orderStyleObj : request.getStyleList()) {
			
        	 Optional<Style> styleOptional = null;
             if (ValidationUtil.isNull(orderStyleObj.getStyleId())) {
                 errors.add(messageSource.getMessage("order.style.required"));
             }else {
            	 styleOptional = styleRepository.findById(orderStyleObj.getStyleId());
                 if (!styleOptional.isPresent()) {  
                	 errors.add(messageSource.getMessage("order.style.not.found"));
                 }else {
                	 orderStyleObj.setStyleObj(styleOptional.get());
                 }
             }
             
             if (ValidationUtil.isNullObject(orderStyleObj.getOrderedQty())) {
                 errors.add(messageSource.getMessage("order.style.qty.required"));
             } 
             
             if (ValidationUtil.isNullObject(orderStyleObj.getProdQty())) {
                 errors.add(messageSource.getMessage("order.style.prod.qty.required"));
             }
             
             if (ValidationUtil.isNullObject(orderStyleObj.getSam())) {
                 errors.add(messageSource.getMessage("order.style.sam.required"));
             }
		}
         
        ValidationResult result = new ValidationResult();
        if (errors.size() > 0) {
            String errorMessage = errors.stream().map(a -> String.valueOf(a)).collect(Collectors.joining(", "));
            throw new ObjectInvalidException(errorMessage);
        }
        
        if(null == orderObj) {
        	orderObj = Order.builder().customerObj(customerOptional.get()).deliveryDate(request.getDeliveryDate())
        			.orderDate(request.getOrderDate()).build();
        }else {
        	orderObj.setCustomerObj(customerOptional.get());
        	orderObj.setDeliveryDate(request.getDeliveryDate());
        	orderObj.setOrderDate(request.getOrderDate());
        	orderObj.setStatus(Status.ACTIVE);
        }
        for (OrderStyle orderStyleObj : request.getStyleList()) {
        	orderStyleObj.setOrder(orderObj);
		}
        orderObj.setStyleList(request.getStyleList());
        result.setObject(orderObj);
        return result;
	 }
	
}