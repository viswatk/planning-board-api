package com.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.OrderAllocationArrayDTO;
import com.app.dto.OrderAllocationDTO;
import com.app.dto.OrderDeAllocationDTO;
import com.app.entity.OrderAllocation;
import com.app.enumeration.RequestType;
import com.app.repository.OrderAllocationRepository;
import com.app.repository.OrderRepository;
import com.app.response.Response;
import com.app.response.ResponseGenerator;
import com.app.response.TransactionContext;
import com.app.service.MessagePropertyService;
import com.app.service.OrderAllocationService;
import com.app.service.OrderService;
import com.app.validator.OrderAllocationValidator;
import com.app.validator.ValidationResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor(onConstructor_ = { @Autowired })
@RequestMapping("/api/order/production")
@Api(value = "Order Rest API's", produces = "application/json", consumes = "application/json")
public class OrderAllocationController {
	
	private static final Logger logger = Logger.getLogger(OrderAllocationController.class);
	
	private @NonNull ResponseGenerator responseGenerator;
	
	private @NonNull OrderRepository orderRepository;
	
	private @NonNull OrderAllocationService orderAllocationService;
	
	private @NonNull OrderAllocationRepository  orderAllocationRepository;
	
	private @NonNull OrderAllocationValidator validatorService;
	
	private @NonNull OrderService orderService;
	 
	@Autowired
	MessagePropertyService messageSource;
	
	
	@ApiOperation(value = "Allows to create new allocation for any order.", response = Response.class)
	@PostMapping(value = "/allocate", produces = "application/json")
	public ResponseEntity<?> allocation(@ApiParam(value = "The allocation request payload") @RequestBody OrderAllocationDTO request,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		
		ValidationResult validationResult = validatorService.validateAllocation(RequestType.POST, request);
		
		OrderAllocation orderAllocation = (OrderAllocation) validationResult.getObject();
		
		orderAllocationService.save(orderAllocation);
		
		try {
			return responseGenerator.successGetResponse(context, messageSource.getMessage("order.allocation.create"),orderAllocation.getId(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Allows to create new allocation for any order.", response = Response.class)
	@PostMapping(value = "/allocates", produces = "application/json")
	public ResponseEntity<?> allocation(@ApiParam(value = "The allocation request payload") @RequestBody OrderAllocationArrayDTO request,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		
		ValidationResult validationResult = validatorService.validateAllocationArray(RequestType.POST, request);
		
		List<OrderAllocation> orderAllocationList = (List<OrderAllocation>) validationResult.getObject();
		
		orderAllocationService.saveAll(orderAllocationList);
		
		try {
			return responseGenerator.successResponse(context, messageSource.getMessage("order.allocation.create"), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@ApiOperation(value = "Allows to de-allocate the allotted sam of any order", response = Response.class)
	@PostMapping(value = "/deallocate", produces = "application/json")
	public ResponseEntity<?> deAllocation(@ApiParam(value = "The de-allocation request payload") @RequestBody OrderDeAllocationDTO request,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			ValidationResult validationResult = validatorService.validateDeAllocation(RequestType.POST, request);
			
			List<OrderAllocation> allocationList = (List<OrderAllocation>) validationResult.getObject();
			
			Map<String, Object> response = new HashMap<String, Object>();
					
			boolean showStyleDialog = false;
			if(allocationList.size() > 1) {
				showStyleDialog = true;
				response.put("showStyleDialog", showStyleDialog);
				return responseGenerator.successResponse(context,response, HttpStatus.OK);
			}else {
				OrderAllocation orderAllocation = allocationList.get(0);
				orderAllocationRepository.delete(orderAllocation);
			}
			return responseGenerator.successResponse(context,response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
 
	 
}
