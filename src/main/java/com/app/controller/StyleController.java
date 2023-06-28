package com.app.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

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
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.Style;
import com.app.enumeration.RequestType;
import com.app.repository.StyleRepository;
import com.app.response.Response;
import com.app.response.ResponseGenerator;
import com.app.response.TransactionContext;
import com.app.service.MessagePropertyService;
import com.app.util.message.ResponseMessage;
import com.app.validator.StyleValidator;
import com.app.validator.ValidationResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor(onConstructor_ = { @Autowired })
@RequestMapping("/api/style")
@Api(value = "Style Rest API's", produces = "application/json", consumes = "application/json")
public class StyleController {
	
	private static final Logger logger = Logger.getLogger(StyleController.class);
	
	private @NonNull ResponseGenerator responseGenerator;
	
	private @NonNull StyleRepository styleRepository;
	
	private @NonNull StyleValidator validatorService;
	 
	@Autowired
	MessagePropertyService messageSource;
	
	
	@ApiOperation(value = "Allows to create new style.", response = Response.class)
	@PostMapping(value = "/create", produces = "application/json")
	public ResponseEntity<?> create(@ApiParam(value = "The style request payload") @RequestBody Style request,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		
		ValidationResult validationResult = validatorService.validate(RequestType.POST, request);
		
		Style style = (Style) validationResult.getObject();
		
		styleRepository.saveAndFlush(style);
		
		try {
			return responseGenerator.successResponse(context, messageSource.getMessage("style.create"), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@ApiOperation(value = "Allows to create new style.", response = Response.class)
	@PutMapping(value = "/update", produces = "application/json")
	public ResponseEntity<?> update(@ApiParam(value = "The order request payload") @RequestBody Style request,
			@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		
		ValidationResult validationResult = validatorService.validate(RequestType.PUT, request);
		
		Style style = (Style) validationResult.getObject();
		
		styleRepository.saveAndFlush(style);
		
		try {
			return responseGenerator.successResponse(context, messageSource.getMessage("style.create"), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	 
	@ApiOperation(value = "Allows to fetch all style list.", response = Response.class)
	@GetMapping(value = "/get", produces = "application/json")
	public ResponseEntity<?> getAll(@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			return responseGenerator.successGetResponse(context, messageSource.getMessage("style.get.list"), styleRepository.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Allows to fetch style by style id.", response = Response.class)
	@GetMapping(value = "/get/{styleId}", produces = "application/json")
	public ResponseEntity<?> get(@PathVariable("styleId") Integer styleId,@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			Optional<Style> styleObj = styleRepository.findById(styleId);
			if(!styleObj.isPresent()) {
				return responseGenerator.errorResponse(context, ResponseMessage.INVALID_REQUEST_FORMAT, HttpStatus.BAD_REQUEST);
			}
			return responseGenerator.successGetResponse(context, messageSource.getMessage("style.get.one"),styleObj.get(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@ApiOperation(value = "Allows to generate random colours.", response = Response.class)
	@GetMapping(value = "/generate/random/colour", produces = "application/json")
	public ResponseEntity<?> generateRandomColor(@RequestHeader HttpHeaders httpHeader) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			
			Map<String, Boolean> colorMap = new HashMap<String, Boolean>();
			
			for(Style style :styleRepository.findAll()) {
				String colorCode = randomColor();
				
				Boolean isthere = colorMap.get(colorCode);
				
				if(null == isthere || !isthere) {
					style.setColour(colorCode);
					styleRepository.saveAndFlush(style);
				}
				colorMap.put(colorCode, true);
			}
			return responseGenerator.successResponse(context, colorMap, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	 
	
	public String randomColor() {
		// create random object - reuse this as often as possible
        Random random = new Random();

        // create a big random number - maximum is ffffff (hex) = 16777215 (dez)
        int nextInt = random.nextInt(0xffffff + 1);

        // format it as hexadecimal string (with hashtag and leading zeros)
        String colorCode = String.format("#%06x", nextInt);

        // print it
        System.out.println(colorCode);
        return colorCode;
	}
	 
}
