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
import com.app.entity.Location;
import com.app.enumeration.RequestType;
import com.app.repository.CustomerRepository;
import com.app.repository.LocationRepository;
import com.app.service.MessagePropertyService;
import com.app.util.ValidationUtil;

import lombok.AllArgsConstructor;
import lombok.NonNull;


@Service
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class CustomerValidator {
	
    @Autowired
    MessagePropertyService messageSource; 
    
    private @NonNull CustomerRepository customerRepository;
    
    private @NonNull LocationRepository locationRepository;

	List<String> errors = null;
    List<String> errorsObj = null;
    Optional<Subject> subject = null;

    /**
     * method for Customer validation
     * 
     * @param httpHeader
     * @return ValidationResult
     * @throws Exception
     */
	 public ValidationResult validate(RequestType requestType,Customer request) {
		 
		errors = new ArrayList<>();
		Customer customerObj = null;
		
		Optional<Location> locationOptional = null;
		if (ValidationUtil.isNull(request.getLocationId())) {
			errors.add(messageSource.getMessage("customer.location.required"));
	    } else {
	    	locationOptional = 	locationRepository.findById(request.getLocationId()); 
	    	if(!locationOptional.isPresent()) {
   			 throw new ObjectInvalidException(messageSource.getMessage("customer.location.invalid"));
	    	}
	    }

        if (requestType.equals(RequestType.POST)) {
            if (!ValidationUtil.isNull(request.getId())) {
                throw new ObjectInvalidException(messageSource.getMessage("invalid.request.payload"));
            }
            Optional<Customer> customerObjDuplicate = customerRepository.findByLocationObjAndName(locationOptional.get(),request.getName());
    		if(customerObjDuplicate.isPresent()) {
    			 String[] params = new String[]{request.getName()};
    			 errors.add( messageSource.getMessage("customer.duplicate",params));
    		}
        }else {
			if (ValidationUtil.isNull(request.getId())) {
	            throw new ObjectInvalidException(messageSource.getMessage("invalid.request.payload"));
	        } 
    		Optional<Customer> customerObjOptional = customerRepository.findById(request.getId());
            if (!customerObjOptional.isPresent()) {  
                throw new ObjectInvalidException(messageSource.getMessage("customer.not.found"));
            }
            customerObj = customerObjOptional.get();
        }
        
        if (ValidationUtil.isNullOrEmpty(request.getName())) {
            errors.add(messageSource.getMessage("customer.name.required"));
        } else {
        	request.setName(ValidationUtil.getFormattedString(request.getName()));
        }
        
        if (ValidationUtil.isNullOrEmpty(request.getAddressLine())) {
            errors.add(messageSource.getMessage("customer.address.required"));
        } else {
        	request.setAddressLine(ValidationUtil.getFormattedString(request.getAddressLine()));
        }
        
        if (ValidationUtil.isNullOrEmpty(request.getPostalCode())) {
            errors.add(messageSource.getMessage("customer.postalcode.required"));
        } else {
        	request.setPostalCode(ValidationUtil.getFormattedString(request.getPostalCode()));
        }
       
        ValidationResult result = new ValidationResult();
        if (errors.size() > 0) {
            String errorMessage = errors.stream().map(a -> String.valueOf(a)).collect(Collectors.joining(", "));
            throw new ObjectInvalidException(errorMessage);
        }
        
        if(null == customerObj) {
        	customerObj = Customer.builder().locationObj(locationOptional.get()).name(request.getName())
        			.addressLine(request.getAddressLine()).postalCode(request.getPostalCode()).build();
        }else {
        	customerObj.setName(request.getName());
        	customerObj.setLocationObj(locationOptional.get());
        	customerObj.setAddressLine(request.getAddressLine());
        	customerObj.setPostalCode(request.getPostalCode());
        }
        result.setObject(customerObj);
        return result;
	 }
	
}