package com.app.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.repository.CountryRepository;
import com.app.repository.LocationRepository;
import com.app.response.Response;
import com.app.response.ResponseGenerator;
import com.app.response.TransactionContext;
import com.app.service.MessagePropertyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/country")
@Api(value = "Country, location Rest API", produces = "application/json", consumes = "application/json")
public class CountryLocationController {
	
	private static final Logger logger = Logger.getLogger(CountryLocationController.class);
	
	private ResponseGenerator responseGenerator;

	@Autowired
	public CountryLocationController(ResponseGenerator responseGenerator) {
		this.responseGenerator = responseGenerator;
	}
	
	@Autowired(required=true)
	CountryRepository countryRepository;
	
	@Autowired(required=true)
	LocationRepository locationRepository;
	
	@Autowired
	MessagePropertyService messageSource;
	
	 
	@ApiOperation(value = "Allows to fetch all country details as a list.", response = Response.class)
	@RequestMapping(value = "/get", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAll(@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			return responseGenerator.successResponse(context, countryRepository.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Allows to fetch all locations by country id.", response = Response.class)
	@RequestMapping(value = "/location/get/{countryId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAllLocationByCountryId(@ApiParam(value = "Country Integer") @PathVariable("countryId") Integer countryId, 
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			return responseGenerator.successResponse(context, locationRepository.findAllByCountryObjId(countryId), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	 
	 
}
