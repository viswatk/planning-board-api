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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.Customer;
import com.app.enumeration.RequestType;
import com.app.repository.CustomerRepository;
import com.app.response.Response;
import com.app.response.ResponseGenerator;
import com.app.response.TransactionContext;
import com.app.service.MessagePropertyService;
import com.app.util.message.ResponseMessage;
import com.app.validator.CustomerValidator;
import com.app.validator.ValidationResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor(onConstructor_ = { @Autowired })
@RequestMapping("/api/buyer")
@Api(value = "Buyer Rest API's", produces = "application/json", consumes = "application/json")
public class CustomerController {
	
	private static final Logger logger = Logger.getLogger(CustomerController.class);
	
	private @NonNull ResponseGenerator responseGenerator;
	
	private @NonNull CustomerRepository customerRepository;
	
	private @NonNull CustomerValidator validatorService;
	 
	@Autowired
	MessagePropertyService messageSource;
	
	@ApiOperation(value = "Allows to create new buyer.", response = Response.class)
	@PostMapping(value = "/create", produces = "application/json")
	public ResponseEntity<?> create(@ApiParam(value = "The customer request payload") @RequestBody Customer request,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		
		ValidationResult validationResult = validatorService.validate(RequestType.POST, request);
		
		Customer customer = (Customer) validationResult.getObject();
		
		customerRepository.saveAndFlush(customer);
		
		try {
			return responseGenerator.successResponse(context, messageSource.getMessage("customer.create"), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@ApiOperation(value = "Allows to create new buyer.", response = Response.class)
	@PostMapping(value = "/update", produces = "application/json")
	public ResponseEntity<?> update(@ApiParam(value = "The customer request payload") @RequestBody Customer request,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		
		ValidationResult validationResult = validatorService.validate(RequestType.PUT, request);
		
		Customer customer = (Customer) validationResult.getObject();
		
		customerRepository.saveAndFlush(customer);
		
		try {
			return responseGenerator.successResponse(context, messageSource.getMessage("customer.update"), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	 
	@ApiOperation(value = "Allows to fetch all customer list.", response = Response.class)
	@GetMapping(value = "/get", produces = "application/json")
	public ResponseEntity<?> getAll(@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			return responseGenerator.successGetResponse(context, messageSource.getMessage("customer.get.list"), customerRepository.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Allows to fetch customer by customer id.", response = Response.class)
	@GetMapping(value = "/get/{customerId}", produces = "application/json")
	public ResponseEntity<?> get(@PathVariable("customerId") Integer customerId,@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			Optional<Customer> customerObj = customerRepository.findById(customerId);
			if(!customerObj.isPresent()) {
				return responseGenerator.errorResponse(context, ResponseMessage.INVALID_REQUEST_FORMAT, HttpStatus.BAD_REQUEST);
			}
			return responseGenerator.successGetResponse(context, messageSource.getMessage("customer.get.one"),customerObj.get(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	 
	 
}
