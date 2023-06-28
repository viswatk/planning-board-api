 package com.app.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.ErrorDto;
import com.app.dto.LoginRequest;
import com.app.dto.LoginResponse;
import com.app.dto.RoleDTO;
import com.app.entity.User;
import com.app.repository.UserRepository;
import com.app.response.ResponseGenerator;
import com.app.response.TransactionContext;
import com.app.security.JwtTokenUtil;
import com.app.service.MessagePropertyService;
import com.app.util.PasswordUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@Api(value = "Authorization Rest API", description = "Defines endpoints that can be hit only when the user is not logged in. It's not secured by default.")
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class AuthenticationController {
	
	private static final Logger logger = Logger.getLogger(AuthenticationController.class);
	
	private @NonNull ResponseGenerator responseGenerator;
	
	private @NonNull UserRepository userRepository;
	
	
	private @NonNull  JwtTokenUtil jwtTokenUtil;
	
	private @NonNull  MessageSource messageSource;
	
	private @NonNull MessagePropertyService messagePropertySource;
	
	private @NonNull  ApplicationEventPublisher applicationEventPublisher;
 
	
	@ApiOperation(value = "Logs the user in to the system and return the auth tokens")
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> login(@ApiParam(value = "The LoginRequest payload") @RequestBody LoginRequest request, @RequestHeader HttpHeaders httpHeader) throws Exception {
		ErrorDto errorDto = null;
		Map<String, Object> response = new HashMap<String, Object>();
		if (null == request) {
			errorDto = new ErrorDto();
			errorDto.setCode("400");
			errorDto.setMessage("Invalid Request Payload.!");
			response.put("status", 0);
			response.put("error", errorDto);
			return ResponseEntity.badRequest().body(response);
		}
		Optional<User> userOptional = userRepository.findByUserName(request.getUserName());
		if(!userOptional.isPresent()) {
			errorDto = new ErrorDto();
			errorDto.setCode("400");
			errorDto.setMessage("Invalid Username.!");
			response.put("status", 0);
			response.put("error", errorDto);
			return ResponseEntity.badRequest().body(response);
		}
		User user = userOptional.get();
		
		String encryptedPassword = PasswordUtil.getEncryptedPassword(request.getPassword());
		if(!user.getPassword().equals(encryptedPassword)) {
			errorDto = new ErrorDto();
			errorDto.setCode("400");
			errorDto.setMessage("Password is wrong.!");
			response.put("status", 0);
			response.put("error", errorDto);
			return ResponseEntity.badRequest().body(response);
		}
		
		final String token = jwtTokenUtil.generateToken(user);
		response.put("status", 1);		
		response.put("message","Logged in Successfully.!");
		response.put("jwt", token);
		response.put("isOtpVerified", true);
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		try {
			return responseGenerator.successResponse(context, response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		/*
		logger.info(response.toString());
		return ResponseEntity.accepted().body(response);*/
	}
	
	
	@ApiOperation(value = "Get logged in user information based from JWT")
	@RequestMapping(value = "/user-details", method = RequestMethod.GET,produces = "application/json")
	public ResponseEntity<?> getUserDetails(Principal principal, @RequestHeader HttpHeaders httpHeader, Principal principle) throws Exception {
		TransactionContext context = responseGenerator.generateTransationContext(httpHeader);
		ErrorDto errorDto = null;
		Map<String, Object> response = new HashMap<String, Object>();
	    Optional<User> userOptional = userRepository.findByUserName(principle.getName());
		User user = userOptional.get();
		List<RoleDTO> roles = new ArrayList<RoleDTO>();
		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setFullName(user.getFullName());
		loginResponse.setPhone(user.getPhoneNo());
		loginResponse.setUserId(user.getUserId());
		loginResponse.setUserName(user.getUserName());
		loginResponse.setForcePasswordChange(user.getForcePasswordChange());
		
		RoleDTO roleDto = new RoleDTO();
		roleDto.setRoleId(user.getRoleObj().getId());
		roleDto.setRoleName(user.getRoleObj().getRoleName());
		loginResponse.setRoleObj(roleDto);
		response.put("isCustomer", true);
		response.put("loginObj", loginResponse);
		try {
			return responseGenerator.successResponse(context, response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return responseGenerator.errorResponse(context, e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		/*
		logger.info(response.toString());
		return ResponseEntity.accepted().body(response);*/
	}
	 
}
