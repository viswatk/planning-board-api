package com.app.controller;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.Order;
import com.app.enumeration.RequestType;
import com.app.repository.OrderRepository;
import com.app.response.Response;
import com.app.response.ResponseGenerator;
import com.app.response.TransactionContext;
import com.app.service.MessagePropertyService;
import com.app.service.OrderService;
import com.app.util.message.ResponseMessage;
import com.app.validator.OrderValidator;
import com.app.validator.ValidationResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor(onConstructor_ = { @Autowired })
@RequestMapping("/api/order")
@Api(value = "Order Rest API's", produces = "application/json", consumes = "application/json")
public class OrderController {
	
	private static final Logger logger = Logger.getLogger(OrderController.class);
	
	private @NonNull ResponseGenerator responseGenerator;
	
	private @NonNull OrderRepository orderRepository;
	
	private @NonNull OrderValidator validatorService;
	
	private @NonNull OrderService orderService;
	 
	@Autowired
	MessagePropertyService messageSource;
	
	
	@ApiOperation(value = "Allows to create new order.", response = Response.class)
	@PostMapping(value = "/create", produces = "application/json")
	public ResponseEntity<?> create(@ApiParam(value = "The order request payload") @RequestBody Order request,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		
		ValidationResult validationResult = validatorService.validate(RequestType.POST, request);
		
		Order order = (Order) validationResult.getObject();
		
		orderService.save(order);
		
		try {
			return responseGenerator.successGetResponse(context, messageSource.getMessage("order.create"),order.getId(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@ApiOperation(value = "Allows to update existing order.", response = Response.class)
	@PutMapping(value = "/update", produces = "application/json")
	public ResponseEntity<?> update(@ApiParam(value = "The order request payload") @RequestBody Order request,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		
		ValidationResult validationResult = validatorService.validate(RequestType.PUT, request);
		
		Order order = (Order) validationResult.getObject();
		
		orderService.update(order);
		
		try {
			return responseGenerator.successResponse(context, messageSource.getMessage("order.update"), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	 
	@ApiOperation(value = "Allows to fetch all order list.", response = Response.class)
	@GetMapping(value = "/get", produces = "application/json")
	public ResponseEntity<?> getAll(@RequestHeader HttpHeaders httpHeader, @RequestParam("key") String key) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			return responseGenerator.successGetResponse(context, messageSource.getMessage("order.get.list"), orderService.findAllActiveOrders(key), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	 
	
	@ApiOperation(value = "Allows to fetch order by order id.", response = Response.class)
	@GetMapping(value = "/get/{orderId}", produces = "application/json")
	public ResponseEntity<?> get(@PathVariable("orderId") Integer orderId,@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			Optional<Order> orderObj = orderRepository.findById(orderId);
			if(!orderObj.isPresent()) {
				return responseGenerator.errorResponse(context, ResponseMessage.INVALID_REQUEST_FORMAT, HttpStatus.BAD_REQUEST);
			}
			return responseGenerator.successGetResponse(context, messageSource.getMessage("order.get.one"),orderObj.get(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	 
	 
}
