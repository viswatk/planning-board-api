package com.app.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.security.auth.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.controlleradvice.ObjectInvalidException;
import com.app.entity.Style;
import com.app.enumeration.RequestType;
import com.app.repository.StyleRepository;
import com.app.service.MessagePropertyService;
import com.app.util.ValidationUtil;

import lombok.AllArgsConstructor;
import lombok.NonNull;


@Service
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class StyleValidator {
	
    @Autowired
    MessagePropertyService messageSource; 
    
    private @NonNull StyleRepository styleRepository;

	List<String> errors = null;
    List<String> errorsObj = null;
    Optional<Subject> subject = null;

    /**
     * method for Style validation
     * 
     * @param httpHeader
     * @return ValidationResult
     * @throws Exception
     */
	 public ValidationResult validate(RequestType requestType,Style request) {
		 
		errors = new ArrayList<>();
		Style styleObj = null;

        if (requestType.equals(RequestType.POST)) {
            if (!ValidationUtil.isNull(request.getId())) {
                throw new ObjectInvalidException(messageSource.getMessage("invalid.request.payload"));
            }
            Optional<Style> styleObjDuplicate = styleRepository.findByName(request.getName());
    		if(styleObjDuplicate.isPresent()) {
    			 String[] params = new String[]{request.getName()};
    			 errors.add( messageSource.getMessage("style.duplicate",params));
    		}
        }else {
			if (ValidationUtil.isNull(request.getId())) {
	            throw new ObjectInvalidException(messageSource.getMessage("invalid.request.payload"));
	        } 
    		Optional<Style> styleObjOptional = styleRepository.findById(request.getId());
            if (!styleObjOptional.isPresent()) {  
                throw new ObjectInvalidException(messageSource.getMessage("style.not.found"));
            }
            styleObj = styleObjOptional.get();
        }
        
        if (ValidationUtil.isNullOrEmpty(request.getName())) {
            errors.add(messageSource.getMessage("style.name.required"));
        } else {
        	request.setName(ValidationUtil.getFormattedString(request.getName()));
        }
        
        if (ValidationUtil.isNullOrEmpty(request.getDescription())) {
            errors.add(messageSource.getMessage("style.description.required"));
        } else {
        	request.setDescription(ValidationUtil.getFormattedString(request.getDescription()));
        }
        
        if (ValidationUtil.isNull(request.getSam())) {
            errors.add(messageSource.getMessage("style.sam.required"));
        } 
         
        ValidationResult result = new ValidationResult();
        if (errors.size() > 0) {
            String errorMessage = errors.stream().map(a -> String.valueOf(a)).collect(Collectors.joining(", "));
            throw new ObjectInvalidException(errorMessage);
        }
        
        if(null == styleObj) {
        	styleObj = Style.builder().name(request.getName()).sam(request.getSam()).description(request.getDescription()).build();
        }else {
        	styleObj.setSam(request.getSam());
        	styleObj.setName(request.getName());
        	styleObj.setDescription(request.getDescription());
        }
        result.setObject(styleObj);
        return result;
	 }
	
}